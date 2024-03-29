FROM python:3.12.2

WORKDIR /app

RUN apt install wget
RUN apt install unzip  
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt -y install ./google-chrome-stable_current_amd64.deb
RUN wget -O /tmp/chromedriver.zip http://chromedriver.storage.googleapis.com/` curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip
RUN mkdir chrome
RUN unzip /tmp/chromedriver.zip chromedriver -d /usr/src/chrome

RUN apt-get update && \
    pip install --upgrade pip && \
    pip install -r requirements.txt flask

COPY . /app

EXPOSE 5000

CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0"]
