from pymongo import MongoClient
from sklearn.feature_extraction.text import TfidfVectorizer

def fetch_news_from_mongodb():
    client = MongoClient('mongodb://localhost:27017/')
    db = client.CNNect
    news_collection = db.news

    all_news = news_collection.find()

    news_texts = []
    for news in all_news:
        if 'senteceList' in news:
            full_text = ' '.join([sentence['text'] for sentence in news['senteceList']])
            if full_text:  
                news_texts.append(full_text)

    return news_texts

def main():
    # MongoDB에서 뉴스 데이터 가져오기
    news_texts = fetch_news_from_mongodb()
    
    
    # TfidfVectorizer를 사용하여 벡터화
    vectorizer = TfidfVectorizer(stop_words=None)
    news_vectors = vectorizer.fit_transform(news_texts)


    for idx, news_text in enumerate(news_texts):
        print(f"Vector: {news_vectors[idx]}")
        print()



if __name__ == "__main__":
    main()
