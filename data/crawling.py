"""
!pip install pymongo
!pip install selenium
!pip install webdriver-manager
!pip install pytube
!pip install beautifulsoup4
!pip install lxml
!pip install youtube-transcript-api
!pip install py_youtube
!pip install nltk
"""

from pymongo import MongoClient
import configparser
from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.chrome.options import Options as ChromeOptions
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.by import By
from bs4 import BeautifulSoup
import nltk
import time
import re
from datetime import datetime
from nltk.tokenize import sent_tokenize
from youtube_transcript_api import YouTubeTranscriptApi

import concurrent.futures
from py_youtube import Data

def init():
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-dev-shm-usage')
    options.add_experimental_option("detach", True)

    # 크롬 드라이버 최신 버전 설정
    service = ChromeService(executable_path=ChromeDriverManager().install())
    
    driver = webdriver.Chrome(service=service, options=options)

    # 페이지 접속하기
    url = 'https://www.youtube.com/@CNN/videos'
    driver.get(url)

    return driver

def scroll(start_height, end_height):
    # 스크롤 아래로
    SCROLL_PAUSE_TIME = 0.5
    last_height = driver.execute_script("return document.documentElement.scrollHeight")

    # 시작 위치로 스크롤 이동
    driver.execute_script(f"window.scrollTo(0, {start_height});")
    time.sleep(SCROLL_PAUSE_TIME)

    while True:
        driver.execute_script("window.scrollTo(0, document.documentElement.scrollHeight);")
        time.sleep(SCROLL_PAUSE_TIME)

        new_height = driver.execute_script("return document.documentElement.scrollHeight;")
        print('now: ', new_height)

        # 스크롤이 끝까지 도달하거나 끝 위치에 도달한 경우 종료
        # if new_height >= end_height or new_height == last_height:
        if new_height >= end_height:
            break
        last_height = new_height

def parse_time_to_seconds(time_str):
    total_seconds = 0
    time_parts = time_str.split(' ')

    for part in time_parts:
        if '시간' in part:
            total_seconds += int(part.replace('시간', '')) * 3600
        elif '분' in part:
            total_seconds += int(part.replace('분', '')) * 60
        elif '초' in part:
            total_seconds += int(part.replace('초', ''))

    return total_seconds

def get_url():
    # 페이지 파싱
    html = driver.page_source
    soup = BeautifulSoup(html, 'lxml')

    # url 뽑아서 리스트에 저장
    videoList = soup.findAll('div', class_='style-scope ytd-rich-item-renderer')
    print(len(videoList))


    video_info = []
    for video in videoList:
        info = video.find('a', {'id': 'video-title-link'})
        if info:
            video_id = info['href'].split('?v=')[1]
            time = parse_time_to_seconds(info['aria-label'].split(' 전 ')[1])

        # 30분 넘는 영상과 중복 로우는 넣지 않음
        existing_video = db['data'].find_one({'video_id': video_id})
        if existing_video is None and time < 1800:
            video_info.append({'video_id' : video_id, 'video_level' : 0, 'time': time})

    return video_info

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

# def get_date(video_id):

    # 페이지 접속
    url = f'https://www.youtube.com/watch?v={video_id}'
    driver.get(url)

    time.sleep(2)

    # 페이지 파싱
    html = driver.page_source
    soup = BeautifulSoup(html, 'lxml')

    info = soup.findAll('yt-formatted-string', class_='style-scope ytd-video-primary-info-renderer')

    formatted_date = ''
    
    for i in info:
        try:
            original_date = datetime.strptime(i.text, "%Y. %m. %d.")
            formatted_date = original_date.strftime("%Y-%m-%d")
            break
        except:
            continue

    return formatted_date

def set_script(video):
    print(f'=========={video['video_id']} script 처리 시작==========')
    script = load_script(video['video_id'])
    full_script = get_fullscript(script)
    script_sentence = split(full_script)

    # 스크립트 없으면 그냥 db 에서 빼버린다
    if script == '' :
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

def set_info(video):
    print(f'=========={video['video_id']} data 처리 시작==========')
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

# 초기화
start_height = 0
end_height = 100000

# 루프 실행
while True:
    # 드라이버 초기화
    driver = init()

    # 스크롤 및 비디오 리스트 가져오기
    scroll(start_height, end_height)
    video_list = get_url()

    # 비디오 리스트 DB에 저장
    db['data'].insert_many(video_list)

    # 멀티프로세싱을 위한 실행 함수 정의
    def process_video_script(video):
        set_script(video)

    def process_video_info(video):
        set_info(video)

    # 멀티프로세싱 실행
    with concurrent.futures.ThreadPoolExecutor() as executor:
        executor.map(process_video_script, video_list)
        executor.map(process_video_info, video_list)

    # 크롬 드라이버 종료
    driver.quit()

    # 다음 스크롤 범위 설정
    start_height = end_height + 1
    end_height += 100000

    print("next start = ", start_height)

    # 마지막 스크롤 범위에 도달하면 종료
    if end_height > 10000000:
        break