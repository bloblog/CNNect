# cmd에서 설치하기

"""
pip install tensorflow
pip install sklearn
pip install keybert
pip install pandas
"""

from keybert import KeyBERT
from transformers import BertModel
from youtube_transcript_api import YouTubeTranscriptApi
from datetime import datetime
import pandas as pd
import json
import re

def init():
    file_name = './script(0307).json'
    with open(file_name, 'r') as file:
      data = json.load(file)
    return data

def load_script():
    df = pd.DataFrame(columns=['videoId', 'script', 'keyword'])
    for i, script in enumerate(script_list):
        print('load....')
        video_id = script['video_id']
        full_script = script['full_script']

        # 키워드 추출
        keyword = get_keyword(full_script)
        df.loc[i] = [video_id, full_script, keyword]
    return df

def get_keyword(script):
  model = BertModel.from_pretrained('bert-base-uncased')
  kw_model = KeyBERT(model)
  keywords = kw_model.extract_keywords(script, keyphrase_ngram_range=(1, 1), stop_words=None, top_n=10)
  return keywords


def save(result):
    # 현재 날짜 가져오기
    current_date = datetime.now().strftime("%m%d")

    # 파일 이름 생성
    file_name = f"category({current_date}).json"
    with open(file_name, 'w', encoding='utf-8') as json_file:
      json.dump(result, json_file, ensure_ascii=False, indent=4)

# 스크립트 리스트 초기화
script_list = init()
def script_to_keyword():
    df = load_script()
    return df