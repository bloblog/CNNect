"""
!pip install pymongo
!pip install youtube-transcript-api
!pip install py_youtube
!pip install nltk
"""

from pymongo import MongoClient
import configparser
import nltk
import re
from nltk.tokenize import sent_tokenize
import concurrent.futures
from youtube_transcript_api import YouTubeTranscriptApi
from py_youtube import Data

# 스크립트 관련 메서드
def load_script(video_id):
    try:
        script = YouTubeTranscriptApi.get_transcript(video_id, languages=['en'])
    except:
        # 안 불러와짐 = 스크립트 없음
        script = ''

    return script

def format(sentence):
    # '>>'를 제외한 특수문자는 유지하고, 2개 이상의 공백을 1개의 공백으로 치환
    cleaned_sentence = re.sub(r'\s{2,}', ' ', re.sub(r'(?<!>)>> ', '', sentence))
    capitalized_sentence = cleaned_sentence.capitalize()

    return capitalized_sentence

def get_fullscript(script):
    full_script = ' '.join(obj['text'] for obj in script)
    
    return full_script

def split(full_script):
    sentences = sent_tokenize(full_script)
    formatted_sentences = [format(s) for s in sentences]

    return formatted_sentences

def time_match(script, sentences):
    sentence_times = []  # 문장과 해당 문장의 시작 시간을 저장

    for sentence in sentences:
        for obj in script:
          # 현재 어절의 텍스트
          current_word = re.sub(r'\s{2,}', ' ', re.sub(r'(?<!>)>> ', '', obj['text'].lower()))

          # 어절이 현재 문장에 속하는 경우
          if current_word in sentence.lower():
              # 해당 문장의 시작 시간을 어절의 시작 시간으로 업데이트
              sentence_times.append({'start' :obj['start'], 'text': sentence})
              script.remove(obj)
              break

    return sentence_times

def set_script(video):
    script = load_script(video['video_id'])
    full_script = get_fullscript(script)
    script_sentence = split(full_script)

    # 스크립트 없으면 그냥 db 에서 빼버린다
    if script == '' :
        # print(video['video_id'], "script 확인 필요")
        db['data'].delete_one({"video_id": video['video_id'],})
    else :
        db['data'].update_one(
            {
                "video_id": video['video_id'],
            },
            {
                "$set": {
                    "senteceList": time_match(script, script_sentence),
                    "full_script": full_script
                }
            }
        )

# info 관련 메서드들
def set_info(video):
    data = Data(f"https://youtu.be/{video['video_id']}").data()

    title = data['title']
    src = data['thumbnails']
    date = data['publishdate']
    category = data['category'].split(' ')[-1]

    db['data'].update_one(
        {
            "video_id": video['video_id'],
        },
        {
            "$set": {
                "video_name" : title,
                "video_thumbnail" : src,
                "category_name" : category,
                "video_date": date,
            }
        }
    )

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

nltk.download('punkt')

# 현재 db 가져오기
# script 속성 없는 로우만
video_list = db['data'].find({ "video_name": { "$exists": False } })


# 멀티프로세싱을 위한 실행 함수 정의
cnt_1 = 0
cnt_2 = 0
def process_video_script(video):
    global cnt_1
    print(f"====={cnt_1}번째 script 처리 시작======")
    set_script(video)
    cnt_1 = cnt_1 + 1

def process_video_info(video):
    global cnt_2
    print(f"====={cnt_2}번째 info 처리 시작======")
    set_info(video)
    cnt_2 = cnt_2 + 1


# 멀티프로세싱 실행
with concurrent.futures.ThreadPoolExecutor() as executor:
    # executor.map(process_video_script, video_list)
    executor.map(process_video_info, video_list)
