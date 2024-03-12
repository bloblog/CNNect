# cmd에서 설치하기

"""
pip install selenium
pip install webdriver-manager
pip install pytube
pip install beautifulsoup4
pip install lxml
"""

from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.chrome.options import Options as ChromeOptions
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.by import By
from bs4 import BeautifulSoup
from datetime import datetime
import time
import json

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

# def save(result):
#     # 현재 날짜 가져오기
#     current_date = datetime.now().strftime("%m%d")

#     # 파일 이름 생성
#     file_name = f"videolist({current_date}).json"
#     with open(file_name, 'w', encoding='utf-8') as json_file:
#       json.dump(result, json_file, ensure_ascii=False, indent=4)

# 드라이버 초기화
driver = init()
def getVideoId():
    scroll()
    result = getUrl()
    return result
    # save(result)