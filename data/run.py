"""
pip install pymongo
pip install flask
"""

from flask import Flask, render_template
from pymongo import MongoClient
import configparser

from crawling.script import *

# from keywords import *
from category.category import *
from category.getData import *

app = Flask(__name__)

# DB 인증 정보 가져오기
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
    return render_template('index.html')

dataset = []

@app.route("/getData")
def getData():
    global dataset
    video_list = getVideoId()
    # dataset = getScript(video_list)
    dataset = getScriptDate(video_list)
    # dataset = getDate(dataset)

    # # 카테고리 설정
    # dataset = await setCategory(dataset)

    # # 레벨 평가
    # dataset = setLevel(dataset)

    return render_template('done.html')

@app.route("/category")
async def setCategory():
    # global dataset
    df = getCategory()
    return df.to_json(orient='records', force_ascii=False)


@app.route("/addData")
def addData():
    db['data'].insert_many(dataset)
    return 'DB에 데이터 추가 완료'

if __name__ == '__main__':
    app.run('0.0.0.0', port=8000, debug=True)
    
