from flask import Flask, jsonify, request, Blueprint
import pymysql.cursors
from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
from recommendation.config_reader import read_config, get_database_config, get_mongodb_config, get_jwt_secret_key
import jwt
from http import HTTPStatus
from flask_cors import CORS
recommendation_bp = Blueprint('recommendation', __name__, url_prefix='/data/recommendation')

app = Flask(__name__)
CORS(app)

# config.ini 파일에서 설정 읽어오기
config = read_config()
database_config = get_database_config(config)
mongodb_config = get_mongodb_config(config)

# JWT 시크릿 키 설정
SECRET_KEY = get_jwt_secret_key(config)

# mysql연결
def get_mysql_connection():
    try:
        connection = pymysql.connect(
            host=database_config['HOST'],
            port=database_config['PORT'],
            user=database_config['USERNAME'],
            password=database_config['PASSWORD'],
            database=database_config['DATABASE_NAME'],
            cursorclass=pymysql.cursors.DictCursor
        )
        print("MySQL 연결 성공 !")
        return connection
    
    except pymysql.err.OperationalError as e:
        print("MySQL 연결 중 운영 오류:", e)
    except pymysql.err.ProgrammingError as e:
        print("MySQL 연결 중 프로그래밍 오류:", e)
    except pymysql.err.DatabaseError as e:
        print("MySQL 연결 중 데이터베이스 오류:", e)
    except Exception as e:
        print("MySQL 연결 오류:", e)

# 토큰 유효성 검사
def validate_token(token):
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
        user_id = payload.get("user_id")
        if user_id:
            return {"isValid": True, "user_id": user_id}
        else:
            return {"isValid": False, "message": "토큰에 사용자 정보가 없습니다."}
    except jwt.ExpiredSignatureError:
        return {"isValid": False, "message": "토큰이 만료되었습니다."}
    except jwt.InvalidTokenError:
        return {"isValid": False, "message": "잘못된 토큰입니다."}

# 토큰에서 user_id 추출
def extract_user_id_from_token():
    auth_header = request.headers.get('Authorization')
    if auth_header and auth_header.startswith("Bearer "):
        access_token = auth_header.split(' ')[1]
        token_info = validate_token(access_token)
        
        if token_info["isValid"]:
            return token_info["user_id"]
    
    return None

# mysql에서 학습기록 video_id 가져오기
def fetch_user_history_news_from_mysql(user_id):
    video_ids = []
    try:
        connection = get_mysql_connection()
        if connection:
            with connection.cursor() as cursor:
                sql = "SELECT video_id FROM user_history WHERE user_id = %s"
                cursor.execute(sql, (user_id,))
                result = cursor.fetchall()
                for row in result:
                    video_ids.append(row['video_id'])
    except Exception as e:
        print(f"MySQL 데이터베이스에서 데이터를 가져오는 중 오류 발생: {e}")
    return video_ids

# mongoDB와 연결
def connect_to_mongodb():
    try:
        mongodb_uri = f"mongodb://{mongodb_config['USERNAME']}:{mongodb_config['PASSWORD']}@{mongodb_config['HOST']}:{mongodb_config['PORT']}/?authSource=admin"
        print(mongodb_uri)
        client = MongoClient(mongodb_uri)
        db = client[mongodb_config['DATABASE_NAME']]
        print("MongoDB 연결 성공!")
        return db
    except Exception as e:
        print("MongoDB 연결 중 오류 발생:", e)
        return None

# mongoDB에서 뉴스 가져오기
def fetch_news_from_mongodb(exclude_video_ids=None):
    try:
        db = connect_to_mongodb()
        news_collection = db.news

        news_documents = [] 
        if exclude_video_ids:
            query = {'video_id': {'$nin': exclude_video_ids}}
            projection = {'_id': 1, 'video_id': 1, 'sentence_list': 1, 'category_name': 1, 'video_date': 1, 'video_name': 1, 'video_thumbnail': 1, 'full_script': 1}
            all_news = news_collection.find(query, projection)
            news_documents = list(all_news)  
        else:
            projection = {'_id': 1, 'video_id': 1, 'sentence_list': 1, 'category_name': 1, 'video_date': 1, 'video_name': 1, 'video_thumbnail': 1, 'full_script': 1}
            all_news = news_collection.find({}, projection)
            news_documents = list(all_news) 

        print("MondoDB에서 가져온 뉴스 수", len(news_documents))
        return news_documents  
    except Exception as e:
        print("MongoDB에서 뉴스를 가져오는 중 오류 발생:", e)
        return []
    
# MySQL에 추천된 뉴스 저장
def save_recommended_news_to_mysql(recommended_news):
    try:
        connection = get_mysql_connection()
        if connection:
            with connection.cursor() as cursor:
                for news in recommended_news:
                    recommended_id = str(uuid.uuid4())
                    sql = "INSERT INTO recommended_news (recommended_id, user_id, video_id, video_name, video_level, video_thumbnail) VALUES (%s, %s, %s, %s, %s, %s)"
                    cursor.execute(sql, (recommended_id, news["user_id"], news["video_id"], news["video_name"], news["video_level"], news["video_thumbnail"]))

                cursor.execute("ALTER TABLE recommended_news ADD INDEX(recommended_id)")
                connection.commit()
                print("추천된 뉴스를 MySQL에 저장했습니다.")
    except Exception as e:
        print(f"추천된 뉴스를 MySQL에 저장하는 중 오류 발생: {e}")

# MySQL에 추천 뉴스 저장 전 삭제
def delete_recommended_news(user_id):
    try:
        connection = get_mysql_connection()
        if connection:
            with connection.cursor() as cursor:
                delete_sql = "DELETE FROM recommended_news WHERE user_id = %s"
                cursor.execute(delete_sql, (user_id,))
            connection.commit()
            print("기존 추천 뉴스를 삭제했습니다.")
    except Exception as e:
        print(f"기존 추천 뉴스 삭제 중 오류 발생: {e}")
    finally:
        if connection:
            connection.close()

class NewsRecommender:
    def __init__(self):
        self.vectorizer = TfidfVectorizer(stop_words=None)
        self.news_vectors = None

    def fit(self, news_scripts):
        if news_scripts:
            self.news_vectors = self.vectorizer.fit_transform(news_scripts)
            print("뉴스 대본 벡터화 성공")
        else:
            print("뉴스 대본이 없습니다.")

    def update_recommendations(self, user_history_scripts, top_n=10):
        if not user_history_scripts:
            print("학습기록이 없습니다.")
            return []

        try:
            user_history_vector = self.vectorizer.transform(user_history_scripts)
            similarities = cosine_similarity(self.news_vectors, user_history_vector)

            aggregate_scores = similarities.sum(axis=1)

            top_similar_indices = np.argsort(-aggregate_scores, axis=0)[:top_n].flatten()

            return top_similar_indices
        except Exception as e:
            print(f"추천 업데이트 중 오류: {e}")
            return []
news_recommender = NewsRecommender()

@recommendation_bp.route('/script', methods=['GET'])
def save_recommendations():
    user_id = extract_user_id_from_token()
    
    if user_id is None:
        return jsonify({"status": HTTPStatus.UNAUTHORIZED, "message": "접근이 불가능합니다."}), HTTPStatus.UNAUTHORIZED

    user_history_scripts = fetch_user_history_news_from_mysql(user_id)
    news_articles = fetch_news_from_mongodb(exclude_video_ids=user_history_scripts)

    if not news_articles:
        return jsonify({"message": "필요한 데이터가 없어 프로세스를 진행할 수 없습니다."}), HTTPStatus.BAD_REQUEST

    all_news_fullscripts = [article["full_script"] for article in news_articles]

    news_recommender.fit(all_news_fullscripts)

    recommended_indices = news_recommender.update_recommendations(user_history_scripts, top_n=10)
    
    recommended_news = [
    {
        "recommended_id": str(uuid.uuid4()),
        "user_id": user_id,
        "video_id": news_articles[index]["video_id"],
        "video_name": news_articles[index]["video_name"],
        "video_level": news_articles[index]["video_level"],
        "video_thumbnail": news_articles[index]["video_thumbnail"]
    }
    for index in recommended_indices
]
    
    delete_recommended_news(user_id)

    save_recommended_news_to_mysql(recommended_news)
    

    return jsonify({"message": "추천된 뉴스를 MySQL에 저장했습니다."}), HTTPStatus.OK


if __name__ == "__main__":
    app.run(debug=True)

