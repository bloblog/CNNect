"""
!pip install pymongo
!pip install selenium
!pip install webdriver-manager
!pip install pytube
!pip install beautifulsoup4
!pip install lxml
!pip install youtube-transcript-api
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

from datetime import datetime
import time
import re
import nltk
from nltk.tokenize import sent_tokenize
from youtube_transcript_api import YouTubeTranscriptApi

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

# 크롤링 시작

def init():
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-dev-shm-usage')

    # 크롬 드라이버 최신 버전 설정
    service = ChromeService(executable_path=ChromeDriverManager().install())
    
    driver = webdriver.Chrome(service=service, options=options)

    # 페이지 접속하기
    url = 'https://www.youtube.com/@CNN/videos'
    driver.get(url)

    return driver

def scroll():
    # 스크롤 아래로
    SCROLL_PAUSE_TIME = 0.5
    last_height = driver.execute_script("return document.documentElement.scrollHeight")

    while True:
        driver.execute_script("window.scrollTo(0, document.documentElement.scrollHeight);")

        time.sleep(SCROLL_PAUSE_TIME)

        new_height = driver.execute_script("return document.documentElement.scrollHeight;")
        print('now: ', new_height)
        # 페이지 길이로 콘텐츠 개수 조정
        # 끝까지 가져오려면 or 뒷부분만
        if new_height > 100000 or new_height == last_height:
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


def getUrl():
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
            title = info['aria-label'].split(' 게시자:')[0]
            time = parse_time_to_seconds(info['aria-label'].split(' 전 ')[1])

        image = video.find('img', class_= 'yt-core-image yt-core-image--fill-parent-height yt-core-image--fill-parent-width yt-core-image--content-mode-scale-aspect-fill yt-core-image--loaded')
        
        if image:
            src = image['src']

        video_info.append({'title' : title, 'video_id' : video_id, 'level' : 0, 'category': 'politics', 'time': time, 'img' : src})

    return video_info





def load_script(video_id):
    nltk.download('punkt')

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


def date_init():
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-gpu')
    options.add_argument('disable-dev-shm-usage')

    # 크롬 드라이버 최신 버전 설정
    service = ChromeService(executable_path=ChromeDriverManager().install())
    driver = webdriver.Chrome(service=service, options=options)

    return driver

def parseDate(soup):
    info = soup.findAll('yt-formatted-string', class_='style-scope ytd-video-primary-info-renderer')

    formatted_date = ''
    
    for i in info:
        try:
            print(i.text)
            original_date = datetime.strptime(i.text, "%Y. %m. %d.")
            formatted_date = original_date.strftime("%Y-%m-%d")
            break
        except:
            continue

    return formatted_date



def getDate(video_id):

    # 페이지 접속
    url = f'https://www.youtube.com/watch?v={video_id}'
    driver.get(url)

    time.sleep(1)

    # 페이지 파싱
    html = driver.page_source
    soup = BeautifulSoup(html, 'lxml')

    return parseDate(soup)

def getScriptDate(video_list):
    result = []
    for i, video in enumerate(video_list[:50]):
        print(f'=========={i}번째==========')
        # print('load date...')
        date = getDate(video['video_id'])
        # print('load script...')
        script = load_script(video['video_id'])
        full_script = get_fullscript(script)
        script_sentence = split(full_script)

        video['date'] = date
        video["sentence"] = time_match(script, script_sentence)
        video["full_script"] = full_script
        result.append(video)
        
    return result


# 드라이버 초기화
driver = init()
# 영상 리스트 스크롤
scroll()
# video id 크롤링
video_list = getUrl()
# 스크립트 및 업로드 날짜 크롤링
dataset = getScriptDate(video_list)
# db에 저장
db['data'].insert_many(dataset)

