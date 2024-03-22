# cmd에서 설치하기

"""
pip install tensorflow
pip install sklearn
pip install keybert
pip install pandas
"""

from keybert import KeyBERT
from transformers import BertModel
from datetime import datetime
import pandas as pd
import json
import re
import os

def init():
    file_name = './article(0308).json'

    # 현재 작업 디렉토리를 스크립트 파일의 디렉토리로 변경
    script_dir = os.path.dirname(os.path.abspath(__file__))
    os.chdir(script_dir)

    with open(file_name, 'r', encoding='utf-8') as file:
      data = json.load(file)
    return data

def load_article(article_list):
    df = pd.DataFrame(columns=['title', 'category', 'text'])
    for i, article in enumerate(article_list):
        title = article['title']
        category = article['category']
        text = article['text']
        
        df.loc[i] = [title, category, text]

    return df

# def categorize(article):
#   model = BertModel.from_pretrained('bert-base-uncased')
#   kw_model = KeyBERT(model)
#   keywords = kw_model.extract_keywords(script, keyphrase_ngram_range=(1, 1), stop_words=None, top_n=10)
#   return keywords

def getCategory():
    article_list = init()
    df = load_article(article_list)
    return df