FROM python:3.12.2

WORKDIR /app

RUN apt-get update && \
    apt-get install -y wget unzip

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable

RUN wget -O /tmp/chromedriver.zip http://chromedriver.storage.googleapis.com/$(curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE)/chromedriver_linux64.zip && \
    mkdir -p /usr/src/chrome && \
    unzip /tmp/chromedriver.zip chromedriver -d /usr/src/chrome

RUN apt-get update && \
    pip install --upgrade pip && \
    pip install flask

COPY requirements.txt /app/
RUN pip install -r requirements.txt

COPY . /app

EXPOSE 5000

CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0"]
