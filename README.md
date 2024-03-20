# 코드 설명

## 1. **`get_mysql_connection()`** 함수
    ◦ MySQL 데이터베이스에 연결하기 위한 함수
    ◦ `pymysql.connect()`를 사용하여 MySQL 서버에 연결

## 2. **`fetch_user_history_news_from_mysql()`** 함수
    ◦ MySQL 데이터베이스에서 사용자의 뉴스 학습 기록을 가져오는 함
    ◦ `get_mysql_connection()` 함수를 사용하여 MySQL에 연결합니다.
    ◦ 쿼리의 결과로부터 사용자의 뉴스 비디오 ID를 추출하고 이를 리스트에 저장하여 반환

## 3. **`fetch_news_from_mongodb()`** 함수
    ◦ MongoDB 데이터베이스에서 모든 뉴스의 문장 리스트를 가져오는 함수
    ◦ `MongoClient`를 사용하여 MongoDB에 연결
    ◦ news 컬렉션에서 모든 뉴스 문서를 가져와서 문장 리스트를 추출
    ◦ 해당 비디오 ID를 가진 뉴스 문서의 문장 리스트 추출
    ◦ 가져온 문장 리스트를 전체 문장 리스트에 추가하여 반환

## 4. **`NewsRecommender`** 클래스
    ◦ TF-IDF 기반 벡터화를 사용하여 뉴스 추천을 위한 클래스
    ◦ `TfidfVectorizer`를 사용하여 뉴스 문장을 벡터화하고, 이를 저장
    ◦ `fit()` 메서드는 뉴스 문장을 기반으로 TF-IDF 벡터를 학습
    ◦ `update_recommendations()` 메서드는 사용자의 뉴스 학습 기록과 뉴스 벡터 간의 코사인 유사도를 계산하여 유사한 뉴스를 추천

## 5. **`main()`** 함수
    ◦ 위에서 정의한 클래스와 함수를 사용하여 뉴스 추천 시스템을 실행하는 함수
    ◦ MySQL에서 사용자의 뉴스 학습 기록을 가져오고, MongoDB에서 모든 뉴스의 문장 리스트를 추출
    ◦ `NewsRecommender` 객체를 생성하고, 가져온 뉴스 문장을 기반으로 추천을 업데이트
    ◦ 추천된 뉴스의 인덱스를 출력
