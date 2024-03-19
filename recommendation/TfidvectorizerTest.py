from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

def fetch_news_from_mongodb():
    client = MongoClient('mongodb://localhost:27017/')
    db = client.CNNect
    news_collection = db.news

    all_news = news_collection.find()

    news_texts = []
    for news in all_news:
        if 'sentenceList' in news:
            full_text = ' '.join([sentence['content'] for sentence in news['sentenceList']])
            news_texts.append(full_text)

    return news_texts

class NewsRecommender:
    def __init__(self):
        self.vectorizer = TfidfVectorizer
        self.news_vectors = None
        self.similarities = None

    def fit(self, news_texts):
        self.news_vectors = self.vectorizer.fit_transform(news_texts)

    def update_recommendations(self, user_history_text, top_n=10):
        user_history_vector = self.vectorizer.transform([user_history_text])
        self.similarities = cosine_similarity(self.news_vectors, user_history_vector)
        top_similar_indices = np.argsort(-self.similarities, axis=0)[:top_n]

        return top_similar_indices

def main():
    news_recommender = NewsRecommender()

    news_texts = fetch_news_from_mongodb()
    news_recommender.fit(news_texts)

    user_history_text = ""

    recommended_indices = news_recommender.update_recommendations(user_history_text, top_n=10)
    print("Recommended News Indices:", recommended_indices)

if __name__ == "__main__":
    main()
