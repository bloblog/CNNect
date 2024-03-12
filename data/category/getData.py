# CNN 홈페이지 기사 가져옴

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
import re

def init():
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--disable-gpu')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-dev-shm-usage')

    # 크롬 드라이버 최신 버전 설정
    service = ChromeService(executable_path=ChromeDriverManager().install())
    
    driver = webdriver.Chrome(service=service, options=options)

    return driver

def getUrl():
    # 페이지 파싱
    html = driver.page_source
    soup = BeautifulSoup(html, 'lxml')

    time.sleep(1)

    # url 뽑아서 리스트에 저장
    urls = set()
    for a_tag in soup.find_all('a', class_=re.compile('container__link container__link--type-article')):
        href = a_tag['href']
        urls.add(href)

    return list(urls)

def format(text):

    text_without_newline = text.replace('\n', ' ')
    processed_text = ' '.join(text_without_newline.split())

    return processed_text

def getText(category, urls):
    print("len : ", len(urls))

    for url in urls:
        # if url.startswith('https://'):
        #     driver.get(url)
        # else:
        #     driver.get(f'https://edition.cnn.com{url}')
        print("url : ", url)
        try: 
            driver.get(f'https://edition.cnn.com{url}')

            time.sleep(1)

            # 페이지 파싱
            html = driver.page_source
            soup = BeautifulSoup(html, 'lxml')

            try:
                title = format(soup.find('h1').text)
            except:
                title = ''

            text = ''
            for p in soup.find_all('p', class_='paragraph inline-placeholder'):
                text += format(p.text)
        except:
            title, text = '', ''
            
        result.append({'title': title, 'category': category, 'text': text})

def save(result):
    current_date = datetime.now().strftime("%m%d")

    # 파일 이름 생성
    file_name = f"article({current_date}).json"
    with open(file_name, 'w', encoding='utf-8') as json_file:
      json.dump(result, json_file, ensure_ascii=False, indent=4)

def saveUrls(category, result):
    current_date = datetime.now().strftime("%m%d")

    # 파일 이름 생성
    file_name = f"url_{category}({current_date}).json"
    with open(file_name, 'w', encoding='utf-8') as json_file:
      json.dump(result, json_file, ensure_ascii=False, indent=4)


# 드라이버 초기화
driver = init()
# 결과 리스트
result = []
def getArticle():
    # 페이지 접속하기
    # 'opinions' 일단 제외
    categories = ['us', 'world', 'politics', 'business', 'health', 'entertainment', 'style', 'travel', 'sports']
    for cat in categories:
        url = f'https://edition.cnn.com/{cat}'
        driver.get(url)
        urls = getUrl()
        saveUrls(cat, urls)
        getText(cat, urls)
    save(result)
