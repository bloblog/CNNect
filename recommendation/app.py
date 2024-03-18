import pymysql.cursors
from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

# MySQL 데이터베이스 연결 설정
def get_mysql_connection():
    connection = pymysql.connect(
        host='localhost',
        user='your_username',
        password='your_password',
        database='your_database',
        cursorclass=pymysql.cursors.DictCursor
    )
    return connection

# 몽고DB에서 뉴스 데이터 가져와서 벡터화하여 새로운 컬렉션에 저장
def save_news_to_mongodb(news_scripts):
    client = MongoClient('mongodb://localhost:27017/')
    db = client.CNNect
    news_vector_collection = db.news_vector

    vectorizer = TfidfVectorizer(stop_words='english')
    news_vectors = vectorizer.fit_transform(news_scripts)

    for i, script in enumerate(news_scripts):
        news_vector_collection.insert_one({'content': script, 'vector': news_vectors[i].tolist()})

# MySQL에서 학습 기록 데이터 가져오기
def fetch_training_history_from_mysql():
    connection = get_mysql_connection()
    try:
        with connection.cursor() as cursor:
            sql = "SELECT vec_value FROM training_history"
            cursor.execute(sql)
            training_history_vec_values = [row['vec_value'] for row in cursor.fetchall()]
    finally:
        connection.close()
    return training_history_vec_values

class NewsRecommender:
    def __init__(self):
        self.vectorizer = TfidfVectorizer(stop_words='english')
        self.news_vectors = None
        self.training_history_vectors = None
        self.similarities = None

    def fit(self, news_scripts, training_history_vec_values):
        self.news_vectors = self.vectorizer.fit_transform(news_scripts)
        self.training_history_vectors = np.array(training_history_vec_values)
        self.similarities = cosine_similarity(self.news_vectors, self.training_history_vectors)

    def update_recommendations(self, top_n=10):
        if self.similarities is None:
            raise ValueError("Fit 메서드를 먼저 호출하여 유사도를 계산해야 합니다.")

        top_similar_indices = np.argsort(-self.similarities, axis=0)[:top_n]

        return top_similar_indices

# 몽고DB에서 뉴스 데이터 가져오기
def fetch_news_from_mongodb():
    client = MongoClient('mongodb://localhost:27017/')
    db = client.CNNect
    news_collection = db.news
    news_documents = news_collection.find({}, {'content': 1})

    news_scripts = [doc['content'] for doc in news_documents]
    return news_scripts

# MySQL에서 학습 기록 가져오기
training_history_vec_values = fetch_training_history_from_mysql()

news_recommender = NewsRecommender()

news_scripts = fetch_news_from_mongodb()

# 뉴스 데이터를 몽고DB에 저장 (벡터화한 값도 함께 저장)
save_news_to_mongodb(news_scripts)

# 뉴스 벡터화 값과 학습 기록 벡터 값 사용하여 모델 학습
news_recommender.fit(news_scripts, training_history_vec_values)

# 추천 뉴스 인덱스 가져오기
recommended_indices = news_recommender.update_recommendations(top_n=10)
print("Recommended News Indices:", recommended_indices)
