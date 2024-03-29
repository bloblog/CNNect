from pymongo import MongoClient
import configparser
from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.common.by import By
from webdriver_manager.chrome import ChromeDriverManager
from bs4 import BeautifulSoup
import nltk
import time
import re
from datetime import datetime
from nltk.tokenize import sent_tokenize
from youtube_transcript_api import YouTubeTranscriptApi
from config_reader import read_config, get_database_config, get_mongodb_config

import concurrent.futures
from py_youtube import Data
from script_level import *
from script_category import *

config = read_config()
mongodb_config = get_mongodb_config(config)

# DB 인증 정보 가져오기
def setDB():
    mongodb_uri = f"mongodb://{mongodb_config['USERNAME']}:{mongodb_config['PASSWORD']}@{mongodb_config['HOST']}:{mongodb_config['PORT']}/?authSource=admin"
    client = MongoClient(mongodb_uri)
    db = client[mongodb_config['DATABASE_NAME']]
    return db


# 크롬드라이버 세팅
def init():
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-dev-shm-usage')
    options.add_experimental_option("detach", True)
    
    driver = webdriver.Chrome(options=options)

    # 페이지 접속하기
    url = 'https://www.youtube.com/@CNN/videos'
    driver.get(url)

    return driver


def get_url():
    global new_data

    # 24일 기준 새로운 영상 많아서 스크롤 2번 더 수행
    # 실제 적용시는 필요 없음
    # 한 번 스크롤 = 30개 영상
    driver.execute_script("window.scrollTo(0, document.documentElement.scrollHeight);")
    time.sleep(1)
    driver.execute_script("window.scrollTo(0, document.documentElement.scrollHeight);")
    time.sleep(1)

    # 페이지 파싱
    html = driver.page_source
    soup = BeautifulSoup(html, 'lxml')

    # url 뽑아서 리스트에 저장
    element = soup.findAll('div', class_='style-scope ytd-rich-item-renderer')

    for video in element:
        info = video.find('a', {'id': 'video-title-link'})
        if info:
            video_id = info['href'].split('?v=')[1]
            if not is_video_exist(video_id):
                new_data.append({'video_id': video_id})
            else:
                print("이미 있는 비디오입니다.")
                break
    print("새로운 데이터 : ", new_data)

    # return new_data


def is_video_exist(video_id):
    result = db['data'].find_one({'video_id': video_id})
    return result is not None


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
    # 문장과 해당 문장의 시작 시간을 저장
    sentence_times = []  

    for sentence in sentences:
        for obj in script:
            # 현재 어절의 텍스트
            current_word = re.sub(r'\s{2,}', ' ', re.sub(r'(?<!>)>> ', '', obj['text'].lower()))

            # 어절이 현재 문장에 속하는 경우
            if current_word in sentence.lower():
                # 해당 문장의 시작 시간을 어절의 시작 시간으로 업데이트
                sentence_times.append({'start': obj['start'], 'text': sentence})
                script.remove(obj)
                break

    return sentence_times


def set_script(video):
    global scripts
    global new_data
    script = load_script(video['video_id'])

    if script == '':
        # 스크립트가 없으면 video_id 삭제
        new_data = [data for data in new_data if data['video_id'] != video['video_id']]

    else:
        full_script = get_fullscript(script)
        scripts.append(full_script) # 카테고라이징 데이터 모으기
        script_sentence = split(full_script)
        level = calculate_difficulty(full_script, script_sentence)
        word_list = find_word_list(full_script, level)

        for data in new_data:
            if data['video_id'] == video['video_id']:
                data['sentenceList'] = time_match(script, script_sentence)
                data['full_script'] = full_script
                data['video_level'] = level
                data['word_list'] = word_list
                break


def set_info(video):

    global new_data
    data = Data(f"https://youtu.be/{video['video_id']}").data()

    title = data['title']
    src = data['thumbnails']
    date = data['publishdate']

    for data in new_data:

        if data['video_id'] == video['video_id']:
            data['video_name'] = title
            data['video_thumbnail'] = src
            data['video_date'] = date
            break


###########################

db = setDB()
driver = init()

nltk.download('punkt')

# 데이터 새로 수집
new_data = []
scripts = []
get_url()

for video in new_data:
    set_script(video)
    set_info(video)

categories = get_category(scripts)

for idx, cat in enumerate(categories):
    new_data[idx]['category_id'] = int(cat)

if new_data:
    db['data'].insert_many(new_data)

# 크롬 드라이버 종료
driver.quit()
