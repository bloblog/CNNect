from flask import Flask, jsonify
import pymysql.cursors
import csv
from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

app = Flask(__name__)

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
    print('videoId',video_ids)
    return video_ids

def fetch_news_from_mongodb(video_ids=None):
    try:
        client = MongoClient('mongodb://localhost:27017/')
        db = client.CNNect
        news_collection = db.news

        news_sentences = []
        news_titles = {}  
        if video_ids:
            for video_id in video_ids:
                news_document = news_collection.find_one({'video_id': video_id})
                if news_document and 'senteceList' in news_document:
                    full_text = ' '.join([sentence['text'] for sentence in news_document['senteceList']])
                    if full_text:
                        index = len(news_sentences)
                        news_sentences.append(full_text)
                        news_titles[index] = news_document.get('video_name', 'No Title') 
        else:
            all_news = news_collection.find()
            for news_document in all_news:
                if 'senteceList' in news_document:
                    full_text = ' '.join([sentence['text'] for sentence in news_document['senteceList']])
                    if full_text:
                        index = len(news_sentences)
                        news_sentences.append(full_text)
                        news_titles[index] = news_document.get('video_name', 'No Title')

        print("Number of news sentences fetched from MongoDB:", len(news_sentences))
        return news_sentences, news_titles 
    except Exception as e:
        print("Error fetching news from MongoDB:", e)
        return [], {}

class NewsRecommender:
    def __init__(self):
        self.vectorizer = TfidfVectorizer(stop_words=None)
        self.news_vectors = None

    def fit(self, news_sentences):
        if news_sentences:
            self.news_vectors = self.vectorizer.fit_transform(news_sentences)
            print("News sentences have been successfully vectorized.")
        else:
            print("No news sentences provided for vectorization.")

    def update_recommendations(self, user_history_sentences, top_n=10):
        if not user_history_sentences:
            print("User history sentences are empty.")
            return []

        try:
            user_history_vector = self.vectorizer.transform(user_history_sentences)
            similarities = cosine_similarity(self.news_vectors, user_history_vector)

            aggregate_scores = similarities.sum(axis=1)

            top_similar_indices = np.argsort(-aggregate_scores, axis=0)[:top_n].flatten()

            return top_similar_indices
        except Exception as e:
            print(f"Error during recommendation update: {e}")
            return []

@app.route('/recommendations', methods=['GET'])
def get_recommendations():
    news_recommender = NewsRecommender()

    user_history_video_ids = fetch_user_history_news_from_file()
    all_news_sentences, news_titles = fetch_news_from_mongodb()  

    latest_user_news_sentences, _ = fetch_news_from_mongodb(user_history_video_ids[-10:])

    if not all_news_sentences or not latest_user_news_sentences:
        return jsonify({"message": "필요한 데이터가 없어 프로세스를 진행할 수 없습니다."}), 400

    news_recommender.fit(all_news_sentences)

    recommended_indices = news_recommender.update_recommendations(latest_user_news_sentences, top_n=10)
    if recommended_indices.size > 0:
        recommended_news = [news_titles[index] for index in recommended_indices]
        return jsonify({"recommended_news": recommended_news}), 200
    else:
        return jsonify({"message": "추천할 뉴스가 없습니다."}), 404

if __name__ == "__main__":
    app.run(debug=True)
