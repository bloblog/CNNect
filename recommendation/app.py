import pymysql.cursors
from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
def get_mysql_connection():
    try:
        connection = pymysql.connect(
            host='localhost',
            user='root',
            password='ssafy',
            database='CNNectDB',
            cursorclass=pymysql.cursors.DictCursor
        )
        print("MySQL connection successful!")
        return connection
    # except pymysql.err.OperationalError as e:
    #     print("Operational error connecting to MySQL:", e)
    #     # 사용자 이름, 암호, 호스트 주소, 데이터베이스 이름 등을 확인하고 문제를 해결합니다.
    # except pymysql.err.ProgrammingError as e:
    #     print("Programming error connecting to MySQL:", e)
    #     # SQL 문장이나 데이터베이스 관련 설정 등이 잘못된 경우를 처리합니다.
    # except pymysql.err.DatabaseError as e:
    #     print("Database error connecting to MySQL:", e)
    #     # 데이터베이스에 액세스할 수 없는 경우를 처리합니다.
    except Exception as e:
        print("Error connecting to MySQL:", e)
        # 기타 예외 상황을 처리합니다.

# mySQL에서 학습기록에 있는 비디오아이디반환
def fetch_user_history_news_from_mysql():
    connection = get_mysql_connection()
    if connection.is_connected():
        cursor = connection.cursor()

        query = "SELECT video_id FROM user_history"
        cursor.execute(query)

        user_history_video_ids = [row['video_id'] for row in cursor.fetchall()]

        cursor.close()
        connection.close()
    return user_history_video_ids


# 몽고DB에 있는 모든 뉴스의 sentenceList를 하나의 스크립트로
def fetch_news_from_mongodb(video_ids=None):
    try:
        client = MongoClient('mongodb://localhost:27017/')
        db = client.CNNect
        news_collection = db.news

        news_sentences = []
        if video_ids:
            for video_id in video_ids:
                news_document = news_collection.find_one({'video_id': video_id})
                if news_document and 'senteceList' in news_document:
                    full_text = ' '.join([sentence['text'] for sentence in news_document['senteceList']])
                    if full_text:  
                        news_sentences.append(full_text)
        else:
            all_news = news_collection.find()
            for news_document in all_news:
                if 'senteceList' in news_document:
                    full_text = ' '.join([sentence['text'] for sentence in news_document['senteceList']])
                    if full_text:  
                        news_sentences.append(full_text)

        print("Number of news sentences fetched from MongoDB:", len(news_sentences))
        return news_sentences
    except Exception as e:
        print("Error fetching news from MongoDB:", e)
        return []



class NewsRecommender:

    def __init__(self):
        self.vectorizer = TfidfVectorizer(stop_words=None)
        self.news_vectors = None
        self.similarities = None

    # 뉴스 스크립트 기반 벡터화(전체 뉴스)
    def fit(self, news_sentences):
        self.news_vectors = self.vectorizer.fit_transform(news_sentences)

    # 학습기록 벡터화 및 유사도 측정
    def update_recommendations(self, user_history_sentences, top_n=10):
        user_history_vector = self.vectorizer.transform(user_history_sentences)
        self.similarities = cosine_similarity(self.news_vectors, user_history_vector)
        top_similar_indices = np.argsort(-self.similarities, axis=0)[:top_n]
        return top_similar_indices


def main():
    news_recommender = NewsRecommender()

    user_history_video_ids = fetch_user_history_news_from_mysql()
    user_news_sentences = fetch_news_from_mongodb(user_history_video_ids)
    news_sentences = fetch_news_from_mongodb()
    
    news_recommender.fit(news_sentences)

    recommended_indices = news_recommender.update_recommendations(user_news_sentences, top_n=10)
    print("Recommended News Indices:", recommended_indices)
