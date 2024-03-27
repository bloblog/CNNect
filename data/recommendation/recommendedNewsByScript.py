from flask import Flask, jsonify, Blueprint
import pymysql.cursors
from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
from module.config_reader import read_config, get_database_config, get_mongodb_config
from flask_cors import CORS

app = Flask(__name__)
recommendation_bp = Blueprint('recommendation', __name__, url_prefix='/recommendation')
CORS(app)

# config.ini 파일에서 설정 읽어오기
config = read_config()
database_config = get_database_config(config)
mongodb_config = get_mongodb_config(config)


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


# mysql에서 학습기록 video_id 가져오기
def fetch_user_history_news_from_mysql():
    video_ids = []
    try:
        connection = get_mysql_connection()
        if connection:
            with connection.cursor() as cursor:
                sql = "SELECT video_id FROM user_history"
                cursor.execute(sql)
                result = cursor.fetchall()
                for row in result:
                    video_ids.append(row['video_id'])
    except Exception as e:
        print(f"MySQL 데이터베이스에서 데이터를 가져오는 중 오류 발생: {e}")
    return video_ids


def save_recommended_news_to_mysql(recommended_news):
    try:
        connection = get_mysql_connection()
        if connection:
            with connection.cursor() as cursor:
                for news in recommended_news:
                    sql = "INSERT INTO recommended_news (video_id, category_name, video_date, video_name, video_thumbnail) VALUES (%s, %s, %s, %s, %s)"
                    cursor.execute(sql, (
                    news["video_id"], news["category_name"], news["video_date"], news["video_name"],
                    news["video_thumbnail"]))
                connection.commit()
                print("추천된 뉴스를 MySQL에 저장했습니다.")
    except Exception as e:
        print(f"추천된 뉴스를 MySQL에 저장하는 중 오류 발생: {e}")


# mongoDB와 연결
def connect_to_mongodb():
    mongodb_uri = f"mongodb://{mongodb_config['USERNAME']}:{mongodb_config['PASSWORD']}@{mongodb_config['HOST']}:{mongodb_config['PORT']}/?authSource=admin"
    client = MongoClient(mongodb_uri)
    db = client[mongodb_config['DATABASE_NAME']]
    return db


# mongoDB에서 뉴스 가져오기
def fetch_news_from_mongodb(exclude_video_ids=None):
    try:
        db = connect_to_mongodb()
        news_collection = db['data']

        news_documents = []
        if exclude_video_ids:
            query = {'video_id': {'$nin': exclude_video_ids}}
            projection = {'_id': 1, 'video_id': 1, 'senteceList': 1, 'category_name': 1, 'video_date': 1,
                          'video_name': 1, 'video_thumbnail': 1, 'full_script': 1}
            all_news = news_collection.find(query, projection)
            news_documents = list(all_news)
        else:
            projection = {'_id': 1, 'video_id': 1, 'senteceList': 1, 'category_name': 1, 'video_date': 1,
                          'video_name': 1, 'video_thumbnail': 1, 'full_script': 1}
            all_news = news_collection.find({}, projection)
            news_documents = list(all_news)

        print("MondoDB에서 가져온 뉴스 수", len(news_documents))
        return news_documents
    except Exception as e:
        print("MongoDB에서 뉴스를 가져오는 중 오류 발생:", e)
        return []


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
def get_recommendations():
    user_history_scripts = fetch_user_history_news_from_mysql()

    news_articles = fetch_news_from_mongodb(exclude_video_ids=user_history_scripts)

    if not news_articles:
        return jsonify({"message": "필요한 데이터가 없어 프로세스를 진행할 수 없습니다."}), 400

    all_news_fullscripts = [article["full_script"] for article in news_articles]

    news_recommender.fit(all_news_fullscripts)

    recommended_indices = news_recommender.update_recommendations(user_history_scripts, top_n=10)

    recommended_news = [{"_id": str(news_articles[index]["_id"]),
                         "video_id": news_articles[index]["video_id"],
                         "category_name": news_articles[index]["category_name"],
                         "video_date": news_articles[index]["video_date"],
                         "video_name": news_articles[index]["video_name"],
                         "video_thumbnail": news_articles[index]["video_thumbnail"]} for index in recommended_indices]

    save_recommended_news_to_mysql(recommended_news)

    return jsonify({"추천 뉴스": recommended_news}), 200


if __name__ == "__main__":
    app.run(debug=True)