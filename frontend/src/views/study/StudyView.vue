<script setup>
import Title from '@/components/study/Title.vue';
import Script from '@/components/study/Script.vue';
import Shadowing from '@/components/study/Shadowing.vue';
import Voca from '@/components/study/Voca.vue';
import VideoPlayer from '@/components/study/VideoPlayer.vue';

import { ref, onMounted, onUnmounted } from 'vue'
import { getStudy } from '@/api/study';
import { updateScore } from '@/api/sentence';
import { updateLastSentence } from '@/api/history';
import { getDict } from '@/api/scraping.js'
import { useRoute } from 'vue-router';

const videoData = ref({
    historyId: null,
    videoId: "",
    videoName: "",
    level: null,
    sentenceList: [],
    wordList: []
})

const curSentence = ref({
    order: "",
    startTime: "",
    content: "",
    score: null
})

const setCurSentence = (curOrder) => {
    const { order, startTime, content, mean, score } = videoData.value.sentenceList[curOrder-1];
    curSentence.value = { order, startTime, content, mean, score };
    ensureActiveSentenceVisible();
};

const updatePronunciationScore = (sentenceOrder, pronunciationScore) => {
    videoData.value.sentenceList[sentenceOrder - 1].score = parseFloat(pronunciationScore);
    curSentence.value.score = parseFloat(pronunciationScore);
    updateScore(
        { 
            sentenceOrder : sentenceOrder, 
            sentenceContent: videoData.value.sentenceList[sentenceOrder - 1].content,
            sentenceScore : parseFloat(pronunciationScore),
            historyId: videoData.value.historyId 
        },
        ({ data }) => {
        },
        (error) => {
            console.log(error);
        }
    );
    updateLastSentence(
        {
            historySentence : videoData.value.sentenceList[sentenceOrder - 1].content,
            historyTime : videoData.value.sentenceList[sentenceOrder - 1].startTime,
            videoId : videoData.value.videoId
        }, 
        ({ data }) => {
        },
        (error) => {
            console.log(error);
        }
    );

}

const wordMeanings = ref({})
const isFinishedFetching = ref(false)
const controller = new AbortController();

const fetchWordMeanings = async () => {
    for (const word of videoData.value.wordList) {
        const result = await getDict(word, 0, controller.signal);
        if (result !== null) {
            wordMeanings.value[word] = result;
        }
    }
    isFinishedFetching.value = true;
};

const refScript = ref(null);

const ensureActiveSentenceVisible = () => {
    const activeSentence = refScript.value.querySelector('.active');
    if (activeSentence) {
        activeSentence.scrollIntoView({ behavior: "smooth", block: "center" });
    }
}

const route = useRoute();

onMounted(() => {
    const videoId = route.params.videoId;

    getStudy(
        videoId,
        ({ data }) => {
            videoData.value = data.data;
            fetchWordMeanings();
        },
        (error) => {
            console.log(error);
        }
    );
})

onUnmounted(() => {
    controller.abort();
});
</script>

<template>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

    <body>
        <Title :videoData="videoData"></Title>
        <main class="section-box">
            <div class="section1">
                <VideoPlayer v-if="videoData.videoId" :videoData="videoData" @change-cur-sentence="setCurSentence"/>
                <Shadowing :videoData="videoData" :curSentence="curSentence" @update-pronunciation-score="updatePronunciationScore"></Shadowing>
            </div>

            <div class="section2">
                <div class="tabmenu out-tabmenu">
                    <ul>
                        <li id="tab1" class="btnCon">
                            <input type="radio" checked name="tabmenu" id="tabmenu1">
                            <label for="tabmenu1">스크립트</label>
                            <div class="tabCon tabCon1" ref="refScript">
                                <Script v-if="videoData.videoId" :videoData="videoData" :curSentence="curSentence" @change-cur-order="setCurSentence"></Script>
                            </div>
                        </li>
                        <li id="tab2" class="btnCon">
                            <input type="radio" name="tabmenu" id="tabmenu2">
                            <label for="tabmenu2">단어장</label>
                            <div class="tabCon tabCon2">
                                <Voca :wordMeanings="wordMeanings" :isFinishedFetching="isFinishedFetching"/>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </main>
    </body>
</template>

<style>
body {
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
}

/* section-box */
.section-box {
    flex: 1;
    display: flex;
    flex-flow: wrap;
}

/* section-box / section1 */
.section1 {
    padding: 20px;
    flex-grow: 1;
    flex-basis: 480px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
/* section-box / section2 */
.section2 {
    margin-top: 5px;
    flex-grow: 1;
    flex-basis: 300px;
    padding: 20px;
}
.tabmenu{ 
    margin: 0 auto;
    height: 300px;
}
.tabmenu ul{
    position: relative;
}
.tabmenu > ul > li{
    display: inline-block;
    width:33.33%;
    max-width: 130px;
    text-align:center;
    background :#f9f9f9;
    line-height:40px;
    border-radius: 10px 10px 0 0;
    box-shadow: 3px 0 10px rgba(0, 0, 0, 0.25);
    margin-right: 10px;
    
}
.tabmenu label{
    display:block;
    width:100%; 
    height: 40px;
    line-height:40px;
    cursor: pointer;
}
.tabmenu input{
    display:none;
}
.tabCon{
    background-color: #ffffff;
    display: none; 
    width: 100%;
    text-align: left; 
    position: absolute; 
    left: 0;
    top: 40px; 
    border : 1px solid #e6e6e6;
    filter: drop-shadow(2px 2px 3px rgba(0, 0, 0, 0.25));
    border-radius: 0 10px 10px 10px;
    height: calc(100vh - 4rem - 85px);
    box-sizing: border-box;
    overflow-y: scroll;
}
.tabmenu input:checked ~ label{
    font-weight: 800;
    padding-top: 5px;
    font-size: 17px;
    background:#f9f9f9;
    border-radius: 10px 10px 0 0;
    box-shadow: 3px 0px 10px rgba(0, 0, 0, 0.25);
    transform: translateY(-5px);
    height: 50px;
}
.tabmenu input:checked ~ label:hover{
    background-color: #f0f0f0;
}
.btnCon:hover{
    background-color: #f0f0f0;
}
.tabmenu input:checked ~ .tabCon{
    display:block;
}
</style>