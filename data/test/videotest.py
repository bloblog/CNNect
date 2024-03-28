from flask import Flask, Blueprint
from pymongo import MongoClient
from conf.config_reader import read_config, get_database_config, get_mongodb_config


app = Flask(__name__)
# recommendation_bp = Blueprint('recommendation', __name__, url_prefix='/recommendation')
CORS(app)

# config.ini 파일에서 설정 읽어오기
config = read_config()
database_config = get_database_config(config)
mongodb_config = get_mongodb_config(config)


def connect_to_mongodb():
    mongodb_uri = f"mongodb://{mongodb_config['USERNAME']}:{mongodb_config['PASSWORD']}@{mongodb_config['HOST']}:{mongodb_config['PORT']}/?authSource=admin"
    client = MongoClient(mongodb_uri)
    db = client[mongodb_config['DATABASE_NAME']]
    return db

# @app.route('/video/<videoId>')  # 3
# def showUserId(videoId):  # 4
#
#     df = pd.read_csv('C:/Users/SSAFY/PycharmProjects/flaskProject2/data/csv/result.csv')
#     translation_lists = generate_translation_lists(df, level)
#
#     # 결과를 저장할 리스트 초기화
#     result_list = []
#
#     # 생성된 단어 목록을 바탕으로 JSON 형태의 데이터 생성
#     for translation_list in translation_lists:
#         word_content = translation_list[0]
#         word_answer = translation_list[1]
#         # 나머지 단어들 선택 (처음 2개 제외)
#         remaining_words = translation_list[2:]
#         # 나머지 단어들을 랜덤하게 섞음
#         all_words = [word_answer] + remaining_words
#         random.shuffle(all_words)
#         # 결과 JSON 객체 생성
#         result = {
#             "word_id": result_list.__len__() + 1,  # 예시로 ID를 리스트 길이+1로 설정
#             "word_content": word_content,
#             "word_answer": word_answer,
#             "word_mean": all_words[:4],  # word_answer 포함하여 총 4개 선택
#         }
#         result_list.append(result)
#
#     # 결과 리스트를 JSON 형태로 변환하여 반환
#     return jsonify(result_list)