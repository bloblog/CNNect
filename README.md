# :clapper: CNNect

<img src="https://lab.ssafy.com/s10-bigdata-recom-sub2/S10P22A507/-/raw/master/assets/gifs/NonLoginHome.gif?ref_type=heads" width='600px'>

## 📚 목차
- [💬 서비스 소개](#intro)
- [🎯 기획 배경](#plans)
- [🙇 팀원 소개](#members)
- [🔨 기술 스택](#skills)
- [🚀 포팅 메뉴얼](#installation)
- [💡 주요 기능](#features)
- [📂 폴더 구조](#folder)

<a name="intro"></a>
## 💬 서비스 소개

사용자의 관심사와 테스트를 통한 영어 실력을 기반으로 CNN 뉴스를 추천해주고 학습을 지원합니다.
1. 뉴스 속 문장의 발음을 듣고 쉐도잉하며 스피킹 실력 향상
2. 발음 점수 측정을 통해 영어 발음 개선
3. 뉴스 스크립트의 해석본를 제공하여 독해 역량 향상
4. 레벨에 맞는 영단어의 한글의미를 제공하여 단어 실력을 향상
5. 학습한 영상에 대한 단어 테스트를 통해 이해도 평가
6. 사용자 관심사와 실력에 맞는 학습 영상 추천
7. 학습 통계를 시각적으로 확인하며 성과 확인 및 동기부여
8. 나의 단어장 및 영상 학습 기록을 제공하여 진행 상태 파악
9. 최신 뉴스의 학습자료(영상과 스크립트)가 매일 업데이트되도록 자동화
10. CEFR 레벨시스템에 따른 어휘 빈도수와 평균 문장 길이에 따른 영상 난이도 판별

<a name="plans"></a>
## 🎯 기획 배경
- 영어 실력의 중요성은 현대사회에서 점점 더 부각
- 듣기, 말하기, 쓰기, 읽기를 한번에 해결
- 정확한 문법, 또렷한 발음, 일정한 속도
- 다양한 주제, 시사적 안목 확장

<a name="members"></a>
## 🙇 팀원 소개

| <img alt="최동헌" src="/assets/images/dongheon.png"/> | <img alt="김하은" src="/assets/images/haeun.png"/> | <img alt="이민주" src="/assets/images/manju.png"/> |
|:---:|:---:|:---:|
|**최동헌(팀장/FS)**|**김하은(팀원/FS)**|**이민주(팀원/FS)**|
|인프라, 학습 기록 구현|인프라, 레벨 시스템|검색 기능, 회원 관리|

| <img alt="이은지" src="/assets/images/eunji.png"/> | <img alt="유혜승" src="/assets/images/hyeseung.jpg"/> | <img alt="차다운" src="/assets/images/dawun.png"/> |
|:---:|:---:|:---:|
|**이은지(팀원/FS)**|**유혜승(팀원/FS)**|**차다운(팀원/FS)**|
|학습진행 관련기능 구현|데이터 수집 및 관리|뉴스 추천 알고리즘|

---

<a name="skills"></a>
## 🔨 기술 스택
### IDE
<img src="https://img.shields.io/badge/intellij idea-000000?style=for-the-badge&logo=intellij idea&logoColor=white">
<img src="https://img.shields.io/badge/visual studio code-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white">

### Frontend
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img src="https://img.shields.io/badge/vue 3.4.15-4FC98D?style=for-the-badge&logo=vue&logoColor=white">
<img src="https://img.shields.io/badge/node.js 20.11.0-339933?style=for-the-badge&logo=node.js&logoColor=white">
<img src="https://img.shields.io/badge/axios 1.6.5-5A29E4?style=for-the-badge&logo=axios&logoColor=white">
<img src="https://img.shields.io/badge/tailwindcss-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white">
<img src="https://img.shields.io/badge/cheerio-E88C1F?style=for-the-badge&logo=cheerio&logoColor=white">


### Backend
<img src="https://img.shields.io/badge/java 17-E84135?style=for-the-badge&logo=y&logoColor=white">
<img src="https://img.shields.io/badge/spring boot 3.2.1-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">
<img src="https://img.shields.io/badge/spring data jpa 3.2.1-6DB33F?style=for-the-badge&logo=t&logoColor=white">
<img src="https://img.shields.io/badge/spring security 6.2.1-6DB33F?style=for-the-badge&logo=spring security&logoColor=white">
<img src="https://img.shields.io/badge/swagger 2.0.2-5EA2D?style=for-the-badge&logo=spring security&logoColor=white">
<img src="https://img.shields.io/badge/jwt 0.8.0-000000?style=for-the-badge&logo=y&logoColor=white">
<img src="https://img.shields.io/badge/mysql 8.0.35-4479A1?style=for-the-badge&logo=mysql&logoColor=white">


### Infra
<img src="https://img.shields.io/badge/jenkins 2.426.3-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
<img src="https://img.shields.io/badge/docker 25.0.0-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/nginx 1.25.3-009639?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/aws ec2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white">

### Version control
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white">


### Management Tools
<img src="https://img.shields.io/badge/jira software-0052CC?style=for-the-badge&logo=jira software&logoColor=white">
<img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white">
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">

---

<a name="skills"></a>

## 🔨 기술 소개

### 데이터 수집
- scrapetube를 사용하여 16만개의 CNN 유튜브 영상 크롤링
- selenium, apscheduler를 통해 새로 올라온 CNN 유튜브 영상을 하루 단위로 자동 크롤링

### 데이터 전처리
- 스크립트 없는 데이터 제거
- nltk.sent_tokenize를 통해 발화 단위의 스크립트 타임라인을 문장 단위로 변경

### 카테고리 모델링
CNN 홈페지의 카테고리 기반 카테고라이징 모델
CNN 홈페이지에서 제공하는 기사 양이 한정적 + 카테고리 별 편차

---

<a name="installation"></a>
## 🚀 포팅 메뉴얼
[포팅 메뉴얼](https://lab.ssafy.com/s10-bigdata-recom-sub2/S10P22A507/-/blob/master/exec/CNNect_A507_%ED%8F%AC%ED%8C%85%EB%A9%94%EB%89%B4%EC%96%BC.pdf?ref_type=heads)   


<a name="features"></a>

## 💡 주요 기능

### 🖥️ 랜딩 페이지
|                       첫 화면                        |
|:------------------------------------------------:|
| <img src="assets/gifs/NonLoginHome.gif" width='250px'> |


## 📂 폴더 구조
<details>
<summary>FE 폴더 구조</summary>
<div markdown="1">

```
📦frontend
 ┣ 📂.vscode
 ┣ 📂node_modules
 ┣ 📂public
 ┃ ┣ 📂badge
 ┃ ┣ 📂level
 ┣ 📂src
 ┃ ┣ 📂api
 ┃ ┃ ┣ 📜badge.js
 ┃ ┃ ┣ 📜eventBus.js
 ┃ ┃ ┣ 📜history.js
 ┃ ┃ ┣ 📜http.js
 ┃ ┃ ┣ 📜recommendations.js
 ┃ ┃ ┣ 📜scraping.js
 ┃ ┃ ┣ 📜search.js
 ┃ ┃ ┣ 📜sentence.js
 ┃ ┃ ┣ 📜study.js
 ┃ ┃ ┣ 📜test.js
 ┃ ┃ ┣ 📜user.js
 ┃ ┃ ┣ 📜video.js
 ┃ ┃ ┣ 📜voca.js
 ┃ ┃ ┗ 📜youtubeSetup.js
 ┃ ┣ 📂assets
 ┃ ┃ ┣ 📂gifs
 ┃ ┣ 📂components
 ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┣ 📜CategoryVideoList.vue
 ┃ ┃ ┃ ┣ 📜DefaultHome.vue
 ┃ ┃ ┃ ┣ 📜Header.vue
 ┃ ┃ ┃ ┣ 📜NonLoginHome.vue
 ┃ ┃ ┃ ┗ 📜RecommVideoList.vue
 ┃ ┃ ┣ 📂history
 ┃ ┃ ┃ ┣ 📜CompletedVideo.vue
 ┃ ┃ ┃ ┣ 📜LearningVideo.vue
 ┃ ┃ ┃ ┗ 📜Voca.vue
 ┃ ┃ ┣ 📂study
 ┃ ┃ ┃ ┣ 📜PopupDictionary.vue
 ┃ ┃ ┃ ┣ 📜Quiz.vue
 ┃ ┃ ┃ ┣ 📜QuizResult.vue
 ┃ ┃ ┃ ┣ 📜Script.vue
 ┃ ┃ ┃ ┣ 📜Shadowing.vue
 ┃ ┃ ┃ ┣ 📜Title.vue
 ┃ ┃ ┃ ┣ 📜VideoPlayer.vue
 ┃ ┃ ┃ ┗ 📜Voca.vue
 ┃ ┃ ┗ 📂user
 ┃ ┃ ┃ ┣ 📜RegisterInfoComponent.vue
 ┃ ┃ ┃ ┣ 📜RegisterLevelComponent.vue
 ┃ ┃ ┃ ┣ 📜RegisterLevelDetailComponent.vue
 ┃ ┃ ┃ ┣ 📜RegisterLevelResultComponent.vue
 ┃ ┃ ┃ ┗ 📜RegisterVideoComponent.vue
 ┃ ┣ 📂router
 ┃ ┃ ┗ 📜index.js
 ┃ ┣ 📂stores
 ┃ ┃ ┗ 📜userStore.js
 ┃ ┣ 📂views
 ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┣ 📜HomeView.vue
 ┃ ┃ ┃ ┣ 📜SearchView.vue
 ┃ ┃ ┃ ┗ 📜VideoView.vue
 ┃ ┃ ┣ 📂history
 ┃ ┃ ┃ ┗ 📜HistoryView.vue
 ┃ ┃ ┣ 📂level
 ┃ ┃ ┃ ┗ 📜LevelView.vue
 ┃ ┃ ┣ 📂study
 ┃ ┃ ┃ ┣ 📜QuizView.vue
 ┃ ┃ ┃ ┗ 📜StudyView.vue
 ┃ ┃ ┗ 📂user
 ┃ ┃ ┃ ┣ 📜MyPageView.vue
 ┃ ┃ ┃ ┗ 📜RegisterView.vue
 ┃ ┣ 📜App.vue
 ┃ ┣ 📜config.ini
 ┃ ┣ 📜index.css
 ┃ ┗ 📜main.js
 ```
</div>
</details>

<details>
<summary>BE 폴더 구조</summary>
<div markdown="1">

```
📦backend
 ┣ 📂gradle
 ┃ ┗ 📂wrapper
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂ssafy
 ┃ ┃ ┃ ┃ ┃ ┗ 📂cnnect
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂badge
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂code
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GlobalException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UnAuthorizedException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂oauth
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂jwt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂filter
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂token
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂recommendation
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂result
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂search
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂userHistory
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂userSentence
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂video
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂voca
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CnnectApplication.java
 ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┗ 📜application.properties
```
</div>
</details>

<details>
<summary>DATA 폴더 구조</summary>
<div markdown="1">

```
📦data
 ┣ 📂conf
 ┣ 📂data
 ┃ ┣ 📂csv
 ┃ ┃ ┗ 📜result.csv
 ┃ ┣ 📂ipynb
 ┃ ┃ ┣ 📜categorize.ipynb
 ┃ ┃ ┣ 📜level_video.ipynb
 ┃ ┃ ┗ 📜word_video.ipynb
 ┃ ┣ 📂json
 ┃ ┃ ┣ 📜addCategory.json
 ┃ ┃ ┣ 📜addDifficulty.json
 ┃ ┃ ┣ 📜cnnect_baseData.json
 ┃ ┃ ┗ 📜cnnect_final.json
 ┃ ┗ 📂py
 ┃ ┃ ┣ 📜category_model.h5
 ┃ ┃ ┣ 📜config_reader.py
 ┃ ┃ ┣ 📜crawling.py
 ┃ ┃ ┣ 📜sample.json
 ┃ ┃ ┣ 📜script_category.py
 ┃ ┃ ┣ 📜script_info.py
 ┃ ┃ ┣ 📜script_level.py
 ┃ ┃ ┗ 📜videoId.py
 ┣ 📂recommendation
 ┃ ┣ 📜config_reader.py
 ┃ ┣ 📜recommendedNewsByScript.py
 ┣ 📂test
 ┃ ┣ 📜leveltest.py
 ┃ ┣ 📜videotest.py
 ┣ 📜app.py
 ┣ 📜Dockerfile
 ┗ 📜requirements.txt 
```
</div>
</details>
