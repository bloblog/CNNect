# 초기 데이터 세팅

"""
!pip install pymongo
!pip install youtube-transcript-api
!pip install py_youtube
!pip install nltk
"""

import concurrent.futures
from crawling import *

def set_script(video):
    script = load_script(video['video_id'])
    full_script = get_fullscript(script)
    script_sentence = split(full_script)

    # 스크립트 없으면 그냥 db 에서 빼버린다
    if script == '' :
        # print(video['video_id'], "script 확인 필요")
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

db = setDB()
nltk.download('punkt')

# 현재 db 가져오기
# script 속성 없는 로우만
video_list = db['data'].find({ "video_name": { "$exists": False } })

# 멀티프로세싱을 위한 실행 함수 정의
cnt_1 = 0
cnt_2 = 0
def process_video_script(video):
    global cnt_1
    print(f"====={cnt_1}번째 script 처리 시작======")
    set_script(video)
    cnt_1 = cnt_1 + 1

def process_video_info(video):
    global cnt_2
    print(f"====={cnt_2}번째 info 처리 시작======")
    set_info(video)
    cnt_2 = cnt_2 + 1


# 멀티프로세싱 실행
with concurrent.futures.ThreadPoolExecutor() as executor:
    # executor.map(process_video_script, video_list)
    executor.map(process_video_info, video_list)
