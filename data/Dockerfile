FROM python:3.8.18

WORKDIR /app
COPY . /app

RUN apt-get update && \
    pip install --upgrade pip && \
    pip install -r requirements.txt flask

EXPOSE 5000

CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0"]
