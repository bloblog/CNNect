from flask import Flask, jsonify
import csv
from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


def fetch_user_history_news_from_file():
    print("Fetching user history news from file...")
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
    print("Fetching news from MongoDB...")
    try:
        client = MongoClient('mongodb://localhost:27017/')
        db = client.CNNect
        news_collection = db.news

        news_documents = []
        if exclude_video_ids:
            query = {'video_id': {'$nin': exclude_video_ids}}
            all_news = news_collection.find(query)
            news_documents = list(all_news)
        else:
            all_news = news_collection.find()
            news_documents = list(all_news)

        print("Number of news documents fetched from MongoDB:", len(news_documents))
        return news_documents
    except Exception as e:
        print("Error fetching news from MongoDB:", e)
        return []


class NewsRecommender:
    def __init__(self):
        self.vectorizer_title = TfidfVectorizer(stop_words=None)
        self.vectorizer_script = TfidfVectorizer(stop_words=None)
        self.news_vectors_title = None
        self.news_vectors_script = None

    def fit(self, news_titles, news_scripts):
        print("Fitting news titles and scripts...")
        if news_titles and news_scripts:
            self.news_vectors_title = self.vectorizer_title.fit_transform(news_titles)
            self.news_vectors_script = self.vectorizer_script.fit_transform(news_scripts)
            print("News titles and scripts have been successfully vectorized.")
        else:
            print("No news titles or scripts provided for vectorization.")

    def update_recommendations(self, user_history_titles, user_history_scripts, top_n=10):
        print("Updating recommendations...")
        if not user_history_titles or not user_history_scripts:
            print("User history titles or scripts are empty.")
            return []

        try:
            user_history_vector_title = self.vectorizer_title.transform(user_history_titles)
            user_history_vector_script = self.vectorizer_script.transform(user_history_scripts)

            # 각 벡터를 열로 변환하여 2차원 배열로 구성합니다.
            user_history_vector = np.hstack([user_history_vector_title.A, user_history_vector_script.A])

            # news_vectors를 수직으로 병합한 후 각 벡터를 열로 변환하여 2차원 배열로 구성합니다.
            news_vectors = np.vstack([self.news_vectors_title.A, self.news_vectors_script.A])

            similarities = cosine_similarity(news_vectors, user_history_vector)

            aggregate_scores = similarities.sum(axis=1)

            top_similar_indices = np.argsort(-aggregate_scores, axis=0)[:top_n].flatten()

            return top_similar_indices
        except Exception as e:
            print(f"Error during recommendation update: {e}")
            return []


@app.route('/complexity', methods=['GET'])
def get_recommendations():
    print("Getting recommendations...")
    news_recommender = NewsRecommender()

    user_history_video_ids = fetch_user_history_news_from_file()
    # 사용자가 이미 본 뉴스를 제외한 뉴스 목록을 가져옵니다.
    news_documents = fetch_news_from_mongodb(exclude_video_ids=user_history_video_ids)

    if not news_documents:
        return jsonify({"message": "필요한 데이터가 없어 프로세스를 진행할 수 없습니다."}), 400

    all_news_titles = [news_document.get('video_name', 'No Title') for news_document in news_documents]
    all_news_scripts = [' '.join([sentence['text'] for sentence in news_document['senteceList']]) for news_document in
                        news_documents]

    news_recommender.fit(all_news_titles, all_news_scripts)

    recommended_indices = news_recommender.update_recommendations(all_news_titles, all_news_scripts, top_n=10)

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
