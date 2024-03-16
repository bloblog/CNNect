<script setup>
import Title from '@/components/study/Title.vue';
import Script from '@/components/study/Script.vue';
import Video from '@/components/study/Video.vue';
import Shadowing from '@/components/study/Shadowing.vue';
import Voca from '@/components/study/Voca.vue';
import Buttons from '@/components/study/Buttons.vue';

import { ref, onMounted } from 'vue'
import { getStudy } from '@/api/study';

const videoData = ref({
    videoId: "",
    videoName: "",
    url : "",
    date: "",
    level: 3,
    category :  "",
    sentenceList: [],
    wordList: []
})

const curSentence = ref({
    order: "",
    startTime: "",
    content: "",
    mean: "",
    score: ""
})

function setCurSentence (order) {
    curSentence.value.order = videoData.value.sentenceList[order-1].order;
    curSentence.value.startTime = videoData.value.sentenceList[order-1].startTime;
    curSentence.value.content = videoData.value.sentenceList[order-1].content;
    curSentence.value.mean = videoData.value.sentenceList[order-1].mean;
    curSentence.value.score = videoData.value.sentenceList[order-1].score;
}

onMounted(() => {
    videoData.value = getStudy(); 
    setCurSentence(1);
})

</script>

<template>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.css" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

    <body>
        <Title :videoData="videoData"></Title>

        <main>
            <div class="section-box">
                <div class="section1">
                    <Video :videoData="videoData" :curSentence="curSentence"></Video>
                    <Shadowing :curSentence="curSentence"></Shadowing>
                </div>

                <div class="section2">
                    <div class="tabmenu out-tabmenu">
                        <ul>
                            <li id="tab1" class="btnCon">
                                <input type="radio" checked name="tabmenu" id="tabmenu1">
                                <label for="tabmenu1">스크립트</label>
                                <div class="tabCon">
                                    <Script :videoData="videoData" :curSentence="curSentence" @change-cur-order="setCurSentence"></Script>
                                </div>
                            </li>
                            <li id="tab2" class="btnCon">
                                <input type="radio" name="tabmenu" id="tabmenu2">
                                <label for="tabmenu2">단어장</label>
                                <div class="tabCon">
                                    <Voca :videoData="videoData"/>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </main>
        <Buttons/>
    </body>
</template>

<style>
/* section-box */
.section-box {
    display: flex;
    flex-flow: wrap;
}

/* section-box / section1 */
.section1 {
    padding: 20px;
    flex-grow: 1;
    flex-basis: 480px;
    height: 100%;
    display: flex;
    flex-direction: column;
}

/* section-box / section2 */
.section2 {
    flex-grow: 1;
    flex-basis: 300px;
    height: 600px;
    padding: 20px;
}
.tabmenu{ 
    margin: 0 auto;
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
    display:none; 
    width: 100%;
    text-align:left; 
    position:absolute; 
    left:0; top:40px; 
    box-sizing: border-box; 
    border : 1px solid #e6e6e6;
    filter: drop-shadow(2px 2px 3px rgba(0, 0, 0, 0.25));
    border-radius: 0 10px 10px 10px;
    height: 500px;
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