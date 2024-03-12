"""
pip install pymongo
pip install flask
"""

from flask import Flask, render_template, request
from pymongo import MongoClient
import configparser

from crawling.videoId import *
from crawling.script import *
# from keywords import *
from crawling.date import *
from category.category import *
from category.getData import *

app = Flask(__name__)

# 인증 정보 가져오기
config = configparser.ConfigParser()
config.read('config.ini')

host = config['MongoDB']['DB_HOST']
port = config['MongoDB']['DB_PORT']
dbname = config['MongoDB']['DB_NAME']
user = config['MongoDB']['DB_USER']
password = config['MongoDB']['DB_PASSWORD']

mongo_url = f"mongodb://{user}:{password}@{host}:{port}/{dbname}"
client = MongoClient(mongo_url)
db = client.CNNect

@app.route('/')
def home():
    # json_data = categorize().to_json()
    # getArticle()
    return 'home'

dataset = []
@app.route("/getData")
def getData():
    # video id 먼저 구해서 객체 배열로 만듦
    # id 돌면서 script 구한 다음 
    # DB 에 저장할 형태인 dataset 에 저장
    global dataset
    dataset = setDate(getScript(getVideoId()))
    
    return render_template('index.html')

@app.route("/addData")
def addData():
    db['data'].insert_many(dataset)
    return 'DB에 데이터 추가 완료'

if __name__ == '__main__':
    app.run('0.0.0.0', port=5000, debug=True)