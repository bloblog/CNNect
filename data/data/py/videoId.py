# 대량 데이터 가져오기 위해 이미 있는 모듈 활용
# 맨 처음 한 번만 사용

import scrapetube
import configparser
from pymongo import MongoClient
from crawling import *

db = setDB()

videos = scrapetube.get_channel(channel_id="UCupvZG-5ko_eiXAupbDfxWw", sleep=1.5)

for i, video in enumerate(videos):
    print(f"{i}번째 비디오")
    db['data'].insert_one({'video_id' : video['videoId']})

print('done')
