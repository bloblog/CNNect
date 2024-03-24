#### 자동 크롤링 코드 ####
from flask import Flask
import subprocess
from apscheduler.schedulers.background import BackgroundScheduler

def job_function():
    print("크롤링 실행")
    subprocess.run(["python", "crawling.py"])

# APScheduler 설정
scheduler = BackgroundScheduler(daemon=True)

# 1일마다 실행
scheduler.add_job(job_function, 'interval', days=1)

# # (테스트용)10분마다 실행
# scheduler.add_job(job_function, 'interval', minutes=1) 
scheduler.start()

app = Flask(__name__)

if __name__ == '__main__':
    app.run()