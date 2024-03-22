# cmd에서 설치하기

"""
pip install selenium
pip install webdriver-manager
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


# 드라이버 초기화
driver = date_init()
# def getDate(video_list):
#     result = []
#     for i, video in enumerate(video_list):

#         # 페이지 접속
#         url = f'https://www.youtube.com/watch?v={video["video_id"]}'
#         driver.get(url)

#         time.sleep(1)

#         # 페이지 파싱
#         html = driver.page_source
#         soup = BeautifulSoup(html, 'lxml')

#         video['date'] = parseDate(soup)
#         result.append(video)

#         print(f'{i}/{len(video_list)} loading date...')
    
#     return result

def getDate(video_id):

    # 페이지 접속
    url = f'https://www.youtube.com/watch?v={video_id}'
    driver.get(url)

    time.sleep(1)

    # 페이지 파싱
    html = driver.page_source
    soup = BeautifulSoup(html, 'lxml')

    return parseDate(soup)