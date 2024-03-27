from datetime import datetime

from flask import Flask
import recommendation.recommendedNewsByScript
import test.leveltest
import subprocess
from apscheduler.schedulers.background import BackgroundScheduler

def job_function():
    print("크롤링 실행")
    subprocess.run(["python", "./data/py/crawling.py"])


# APScheduler 설정
scheduler = BackgroundScheduler(daemon=True)

# 1일마다 실행
scheduler.add_job(job_function, 'interval', days=1)
scheduler.start()

app = Flask(__name__)
app.register_blueprint(recommendation.recommendedNewsByScript.recommendation_bp)
app.register_blueprint(test.leveltest.level_bp)

@app.route('/')
def hello_world():  # put application's code here
    return 'Hello World!'

if __name__ == '__main__':
    app.run()
