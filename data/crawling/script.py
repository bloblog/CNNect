# cmd에서 설치하기

"""
pip install youtube-transcript-api
pip install nltk
"""

import nltk
import json
import re
from datetime import datetime
from nltk.tokenize import sent_tokenize
from youtube_transcript_api import YouTubeTranscriptApi

def init():
    nltk.download('punkt')
    
    # # JSON 파일 로드
    # file_name = './videolist(0308).json'
    # with open(file_name, 'r', encoding='utf-8') as file:
    #     data = json.load(file)

    # return data

def load_script(video_id):
    # 안 불러와짐 = 스크립트 없음
    try:
        script = YouTubeTranscriptApi.get_transcript(video_id, languages=['en'])
    except:
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

# def save(result):
#     # 현재 날짜 가져오기
#     current_date = datetime.now().strftime("%m%d")

#     # 파일 이름 생성
#     file_name = f"script({current_date}).json"
#     with open(file_name, 'w', encoding='utf-8') as json_file:
#       json.dump(result, json_file, ensure_ascii=False, indent=4)

def getScript(video_list):
    init()
    result = []
    for video in video_list[:10]:
        script = load_script(video['video_id'])
        full_script = get_fullscript(script)
        script_sentence = split(full_script)
        
        video["sentence"] = time_match(script, script_sentence)
        video["full_script"] = full_script
        result.append(video)

    # save(result)
    print(result)
    return result