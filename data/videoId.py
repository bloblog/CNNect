import scrapetube
import configparser
from pymongo import MongoClient


# DB 인증 정보 가져오기
config = configparser.ConfigParser()
config.read('config.ini')

host = config['MongoDB']['DB_HOST']
port = config['MongoDB']['DB_PORT']
dbname = config['MongoDB']['DB_NAME']
user = config['MongoDB']['DB_USER']
password = config['MongoDB']['DB_PASSWORD']

# DB 연결
mongo_url = f"mongodb://{user}:{password}@{host}:{port}/{dbname}"
client = MongoClient(mongo_url)
db = client.CNNect

videos = scrapetube.get_channel(channel_id="UCupvZG-5ko_eiXAupbDfxWw", sleep=1.5)

for i, video in enumerate(videos):
    print(f"{i}번째 비디오")
    db['data'].insert_one({'video_id' : video['videoId']})

print('done')
