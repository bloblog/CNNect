import pymysql.cursors
import csv
from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from recommendedNewsByScript import recommendation_bp
import numpy as np
from flask import Flask, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


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
    except pymysql.err.OperationalError as e:
        print("Operational error connecting to MySQL:", e)
    except pymysql.err.ProgrammingError as e:
        print("Programming error connecting to MySQL:", e)
    except pymysql.err.DatabaseError as e:
        print("Database error connecting to MySQL:", e)
    except Exception as e:
        print("Error connecting to MySQL:", e)


def fetch_user_history_news_from_file():
    video_ids = []
    try:
        with open('C:/Users/SSAFY/Desktop/user_history.csv', mode='r', encoding='utf-8') as file:
            reader = csv.reader(file)
            next(reader)
            for row in reader:
                video_ids.append(row[0])
    except Exception as e:
        print(f"CSV 파일 읽기 오류: {e}")
    print('videoId', video_ids)
    return video_ids


def fetch_news_from_mongodb(exclude_video_ids=None):
    try:
        client = MongoClient('mongodb://localhost:27017/')
        db = client.CNNect
        news_collection = db.news

        news_documents = []  # 뉴스 문서를 담을 리스트 생성
        if exclude_video_ids:
            query = {'video_id': {'$nin': exclude_video_ids}}
            all_news = news_collection.find(query)
            news_documents = list(all_news)  # 조건에 맞는 뉴스 문서를 리스트로 변환
        else:
            all_news = news_collection.find()
            news_documents = list(all_news)  # 모든 뉴스 문서를 리스트로 변환

        print("Number of news documents fetched from MongoDB:", len(news_documents))
        return news_documents  # 뉴스 문서 리스트 반환
    except Exception as e:
        print("Error fetching news from MongoDB:", e)
        return []


class NewsRecommender:
    def __init__(self):
        self.vectorizer = TfidfVectorizer(stop_words=None)
        self.news_vectors = None

    def fit(self, news_names):
        if news_names:
            self.news_vectors = self.vectorizer.fit_transform(news_names)
            print("News names have been successfully vectorized.")
        else:
            print("No news names provided for vectorization.")

    def update_recommendations(self, user_history_names, top_n=10):
        if not user_history_names:
            print("User history names are empty.")
            return []

        try:
            user_history_vector = self.vectorizer.transform(user_history_names)
            similarities = cosine_similarity(self.news_vectors, user_history_vector)

            aggregate_scores = similarities.sum(axis=1)

            top_similar_indices = np.argsort(-aggregate_scores, axis=0)[:top_n].flatten()

            return top_similar_indices
        except Exception as e:
            print(f"Error during recommendation update: {e}")
            return []


@recommendation_bp.route('/title', methods=['GET'])
def get_recommendations():
    news_recommender = NewsRecommender()

    user_history_video_ids = fetch_user_history_news_from_file()
    # 사용자가 이미 본 뉴스를 제외한 뉴스 목록을 가져옵니다.
    news_documents = fetch_news_from_mongodb(exclude_video_ids=user_history_video_ids)

    if not news_documents:
        return jsonify({"message": "필요한 데이터가 없어 프로세스를 진행할 수 없습니다."}), 400

    all_news_names = [news_document.get('video_name', 'No Title') for news_document in news_documents]

    news_recommender.fit(all_news_names)

    recommended_indices = news_recommender.update_recommendations(all_news_names, top_n=10)

    recommended_news = [{"_id": str(news_documents[index]["_id"]),
                         "video_id": news_documents[index]["video_id"],
                         "senteceList": news_documents[index]["senteceList"],
                         "category_name": news_documents[index]["category_name"],
                         "video_date": news_documents[index]["video_date"],
                         "video_name": news_documents[index]["video_name"],
                         "video_thumbnail": news_documents[index]["video_thumbnail"]} for index in recommended_indices]
    return jsonify({"recommended_news": recommended_news}), 200


if __name__ == "__main__":
    app.run(debug=True)
