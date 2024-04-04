# :clapper: CNNect
![cnnect](/uploads/8c94053c1dd0ce99859d754c9e50ac65/cnnect.PNG)

## 📚 목차
- [💬 서비스 소개](#intro)
- [🎯 기획 배경](#plans)
- [🙇 팀원 소개](#members)
- [🔑 기술 소개](#technology)
- [💡 주요 기능](#features)
- [🔨 개발 환경](#skills)
- [🚀 포팅 메뉴얼](#installation)
- [📂 폴더 구조](#folder)

<a name="intro"></a>
## 💬 서비스 소개

**사용자의 관심사와 테스트를 통한 영어 실력을 기반으로 CNN 뉴스를 추천해주고 학습을 지원합니다.**

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

|**최동헌 (팀장/FS)**|**김하은 (팀원/FS)**|**이민주 (팀원/FS)**|
|:---:|:---:|:---:|
|인프라, 학습 기록 구현|인프라, 레벨 시스템|검색 기능, 회원 관리|

|**이은지 (팀원/FS)**|**유혜승 (팀원/FS)**|**차다운 (팀원/FS)**|
|:---:|:---:|:---:|
|학습 진행 관련 기능|데이터 수집 및 관리|뉴스 추천 알고리즘|

---

<a name="technology"></a>
## 🔑 기술 소개

### 📈 데이터 수집
- **scrapetube**를 사용하여 **16만개**의 CNN 유튜브 영상 크롤링
- **selenium**, **apscheduler**를 통해 새로 올라온 CNN 유튜브 영상을 하루 단위로 **자동 크롤링**

---

### 📉 데이터 전처리
- 스크립트 없는 데이터 제거
- **nltk.sent_tokenize**를 통해 발화 단위의 스크립트 타임라인을 **문장 단위**로 변경

---

### 📊 카테고리 모델링

_CNN 홈페지의 카테고리 기반 카테고라이징 모델_

➡ CNN 홈페이지에서 제공하는 기사 양이 한정적 + 카테고리 별 편차

➡ 데이터 증강을 통해 기사 양 늘리고 편차 극복

➡ 임베딩을 통해 단어를 밀집 벡터로 매핑

➡ 평균 풀링을 통해 고정 크기의 벡터 생성, 완전 연결 레이어를 통해 분류 수행

---

### 🥇 난이도 선정

_아래의 두가지 지표를 혼합하여 난이도 선정_

| 단어 난이도 | 영상 난이도 |
|:-------:|:-------:|
| **CEFR** 유럽연합 공통언어 표준등급 | **Flesh-Reading Ease Score** |
| A1 - A2 <br/>- B1 - B2 <br/>- C1 - C2 | **범위** 0~100 <br/> **계산식** 206.835 - 1.015* (문장당 평균 단어 길이) - 84.6 * (평균 단어 수) |   

<a name="features"></a>

## 💡 주요 기능

### 🖥️ 랜딩 페이지
|  초기화면  |
|:---------:|
| ![초기화면](/uploads/97564a9008894b4c42e84692007c0564/초기화면.gif) |

### 🙋‍♀️ 로그인 및 회원가입

|  정보입력  |
|:---------:|
| ![정보입력](/uploads/a308ddcfeb2c0961ae620d7265d5d2d1/정보입력.gif) |

|  관심영상  |
|:---------:|
| ![관심영상](/uploads/f1725ae685a2ccfa8df84464b0ed097c/관심영상.gif) |

|  레벨테스트  |
|:---------:|
| ![레벨테스트](/uploads/8c67aa24c2512950804c5e615702ce41/레벨테스트.gif) |

### 🎞 메인 페이지
|  최근 학습 뉴스와 추천 뉴스 |
|:---------:|
| ![메인페이지](/uploads/b4128a368d4122f41006b1fdadbba4ee/메인페이지.gif) |


### 📑 영상 목록 페이지
|  카테고리별 조회  |
|:---------:|
| ![둘러보기](/uploads/437dad0317e1aaabc558c2aaf7ed2cbd/둘러보기.gif) |

|  제목과 문장에서 키워드로 검색 |
|:---------:|
| ![검색기능](/uploads/bd5134cd9e8e8f22721cf35987c4de53/검색기능.gif) |



### ✏ 학습 진행 페이지 
|  스크립트  |
|:----------|
| ![스크립트](/uploads/e8f7e7755b013025e69fa57704b790ca/스크립트.gif) |

|   발음평가  |
|:----------:|
| ![발음평가](/uploads/86af72ffe6fd19c3c0e9c4df3dedaa9c/발음평가.gif) |

|  영어사전 팝업창  |
|:----------|
| ![영어사전팝업창](/uploads/1ae7baf98575ba6026a43cf114285639/영어사전팝업창.gif) |

|  단어장  |
|:----------|
| ![단어장](/uploads/6873dd3d895ede78bdf0ca6804c880af/단어장.gif) |

|  학습통계  |
|:----------|
| ![학습통계](/uploads/12e758804ef3ca7d8b7c18a469a16fd4/학습통계.gif) |

### 💯 학습 테스트 페이지
| 시험 문제 |
|:---------:|
| ![학습테스트](/uploads/c458690fafb26b9b95f4a93c723e2c85/학습테스트.gif) |

| 채점 결과 |
|:---------:|
| ![채점결과](/uploads/8819f8497c34af8183113a6f18980114/채점.gif) |

### 📑 학습 기록 페이지
|  학습 상태별 영상 |
|:---------:|
| ![학습기록영상](/uploads/ea15283568edac11fdacfd91828539c9/학습기록영상.gif) |

|  커스텀 단어장  |
|:---------:|
| ![커스텀단어장](/uploads/878c855a71ae41f293f32b746f5d4df8/커스텀단어장.gif) |

<a name="skills"></a>
## 🔨 개발 환경

#### Frontend
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img src="https://img.shields.io/badge/vue 3.4.15-4FC98D?style=for-the-badge&logo=vue&logoColor=white">
<img src="https://img.shields.io/badge/node.js -339933?style=for-the-badge&logo=node.js&logoColor=white">
<img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white">
<img src="https://img.shields.io/badge/tailwindcss-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white">
<img src="https://img.shields.io/badge/cheerio-E88C1F?style=for-the-badge&logo=cheerio&logoColor=white">

#### Backend
<img src="https://img.shields.io/badge/java 17-E84135?style=for-the-badge&logo=y&logoColor=white">
<img src="https://img.shields.io/badge/spring boot 3-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">
<img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=t&logoColor=white">
<img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=spring security&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white">
<img src="https://img.shields.io/badge/swagger-5EA2D?style=for-the-badge&logo=spring security&logoColor=white">
<img src="https://img.shields.io/badge/jwt-000000?style=for-the-badge&logo=y&logoColor=white">

#### Infra
<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/aws ec2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white">

#### IDE
<img src="https://img.shields.io/badge/intellij idea-000000?style=for-the-badge&logo=intellij idea&logoColor=white">
<img src="https://img.shields.io/badge/visual studio code-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white">

#### Version control
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white">

#### Management Tools
<img src="https://img.shields.io/badge/jira software-0052CC?style=for-the-badge&logo=jira software&logoColor=white">
<img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white">
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">


<a name="installation"></a>
## 🚀 포팅 메뉴얼
[포팅 메뉴얼](https://lab.ssafy.com/s10-bigdata-recom-sub2/S10P22A507/-/blob/master/exec/CNNect_A507_%ED%8F%AC%ED%8C%85%EB%A9%94%EB%89%B4%EC%96%BC.pdf?ref_type=heads)

<a name="folder"></a>
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
