import pymysql.cursors
from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

def get_mysql_connection():
    connection = pymysql.connect(
        host='localhost',
        user='CNNect',
        password='password',
        database='database',
        cursorclass=pymysql.cursors.DictCursor
    )
    return connection

# sentenceList를 full_script로 바꾸기
def fetch_user_history_sentences_from_mysql():
    connection = get_mysql_connection()
    if connection.is_connected():
        cursor = connection.cursor()

        query = "SELECT sentenceList FROM user_history"
        cursor.execute(query)

        user_history_sentences = []
        for row in cursor.fetchall():
            user_history_sentences.extend(row[0])

        cursor.close()
        connection.close()
    return user_history_sentences


def fetch_news_from_mongodb(video_ids):
    client = MongoClient('mongodb://localhost:27017/')
    db = client.CNNect
    news_collection = db.news

    news_sentences = []
    for video_id in video_ids:
        news_document = news_collection.find_one({'video_id': video_id})
        if news_document and 'sentenceList' in news_document:
            news_sentences.extend(news_document['sentenceList'])

    return news_sentences


class NewsRecommender:
    def __init__(self):
        self.vectorizer = TfidfVectorizer
        # (stop_words='english')
        self.news_vectors = None
        self.similarities = None

    def fit(self, news_sentences):
        self.news_vectors = self.vectorizer.fit_transform(news_sentences)

    def update_recommendations(self, user_history_sentences, top_n=10):
        user_history_vector = self.vectorizer.transform(user_history_sentences)
        self.similarities = cosine_similarity(self.news_vectors, user_history_vector)
        top_similar_indices = np.argsort(-self.similarities, axis=0)[:top_n]
        return top_similar_indices


def main():
    news_recommender = NewsRecommender()

    user_history_sentences = fetch_user_history_sentences_from_mysql()

    user_video_ids = [item[4] for item in user_history_sentences]
    news_sentences = fetch_news_from_mongodb(user_video_ids)

    news_recommender.fit(news_sentences)

    recommended_indices = news_recommender.update_recommendations(user_history_sentences, top_n=10)
    print("Recommended News Indices:", recommended_indices)
