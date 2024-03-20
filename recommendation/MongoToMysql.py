from pymongo import MongoClient
import mysql.connector

# MongoDB 연결
mongo_client = MongoClient('mongodb://localhost:27017/')
mongo_db = mongo_client.CNNect
news_collection = mongo_db.news

# MySQL 연결
mysql_db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="ssafy",
    database="CNNectDB"
)
mysql_cursor = mysql_db.cursor()

# MongoDB에서 뉴스 데이터 가져오기
all_news = news_collection.find()

for news in all_news:
    video_id = news.get('video_id')  # MongoDB의 video_id 가져오기
    if video_id:
        # user_history 테이블에 video_id 삽입
        mysql_cursor.execute("INSERT INTO user_history (history_id, history_status, history_sentence, history_date, user_id, video_id) VALUES (%s, %s, %s, %s, %s, %s)", (None, False, "Sample sentence", '2024-03-19', 1, video_id))

# 변경사항 저장
mysql_db.commit()

# 연결 종료
mysql_cursor.close()
mysql_db.close()
