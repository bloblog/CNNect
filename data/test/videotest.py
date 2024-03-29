from flask import Flask, Blueprint
from pymongo import MongoClient
from conf.config_reader import read_config, get_database_config, get_mongodb_config
from flask_cors import CORS

video_level_bp = Blueprint('videoLevel', __name__, url_prefix='/data/level/video')

app = Flask(__name__)

# config.ini 파일에서 설정 읽어오기
config = read_config()
database_config = get_database_config(config)
mongodb_config = get_mongodb_config(config)


def connect_to_mongodb():
    mongodb_uri = f"mongodb://{mongodb_config['USERNAME']}:{mongodb_config['PASSWORD']}@{mongodb_config['HOST']}:{mongodb_config['PORT']}/?authSource=admin"
    client = MongoClient(mongodb_uri)
    db = client[mongodb_config['DATABASE_NAME']]
    return db


@video_level_bp.route('/<videoId>')  # 3
def showUserId(videoId):  # 4
    db = connect_to_mongodb()
    video_collection = db['data']
    video_data = video_collection.find_one({"video_id": videoId})
    word_list = video_data['word_list']
    sentence_list = video_data['sentence_list']

    selected_words = random.sample(word_list, 10)
    selected_sentences = []
    answerList = []

    for index, word in enumerate(selected_words):
        for sentence in sentence_list:
            if word in sentence['text']:
                sentence_text_modified = sentence['text'].replace(word, '_' * len(word))
                selected_sentences.append({
                    "original": sentence['text'],
                    "modified": sentence_text_modified,
                    "word": word,
                    "sentenceOrder": index + 1  # 순서는 1부터 시작
                })
                break  # 해당 단어를 포함하는 첫 문장을 찾으면 반복 중단

    # 각 선택된 문장에 대한 질문과 답변 준비
    for item in selected_sentences:
        answerList.append({
            "question": item["modified"],
            "answer": item["word"],
            "sentenceOrder": item["sentenceOrder"]
        })

    return {"answerList": answerList}  # JSON 형태로 반환
