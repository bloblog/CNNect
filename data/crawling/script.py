# cmd에서 설치하기

"""
pip install youtube-transcript-api
pip install nltk
"""

import nltk
import json
import re
import asyncio

from datetime import datetime
from nltk.tokenize import sent_tokenize
from youtube_transcript_api import YouTubeTranscriptApi

from crawling.videoId import *
from crawling.date import *



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

# def getScript(video_list):
#     result = []
#     for i, video in enumerate(video_list[:50]):
#         script = load_script(video['video_id'])
#         full_script = get_fullscript(script)
#         script_sentence = split(full_script)
        
#         video["sentence"] = time_match(script, script_sentence)
#         video["full_script"] = full_script
#         result.append(video)
#         print(f'{i}/{len(video_list)} loading script...')
#     return result

def getScriptDate(video_list):
    result = []
    for i, video in enumerate(video_list[:50]):
        print(f'=========={i}번째==========')
        print('load date...')
        date = getDate(video['video_id'])
        print('load script...')
        script = load_script(video['video_id'])
        full_script = get_fullscript(script)
        script_sentence = split(full_script)

        video['date'] = date
        video["sentence"] = time_match(script, script_sentence)
        video["full_script"] = full_script
        result.append(video)
        
    return result