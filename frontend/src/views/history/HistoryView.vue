<template>

    <body>
        <div class="section-box" >
            <div class="section">
                <div class="tabmenu out-tabmenu">
                    <ul>
                        <li id="tab1" class="btnCon">
                            <input type="radio" checked name="tabmenu" id="tabmenu1">
                            <label for="tabmenu1">동영상</label>
                            <div class="tabCon">
                                <h1 class="text-2xl font-bold p-3">학습 진행중인 뉴스</h1>
                                <div class="learning-video">
                                    <div class="Carousel-btn">
                                        <svg class="sysmbol-btn" xmlns="http://www.w3.org/2000/svg" @click="changeVideoOrder('backward')" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
                                            <path d="M400-80 0-480l400-400 71 71-329 329 329 329-71 71Z"/>
                                        </svg>
                                    </div>   
                                    <div >
                                        <learningVideo :curVideo="curVideo" />
                                    </div>
                                    <div class="Carousel-btn">
                                        <svg xmlns="http://www.w3.org/2000/svg" @click="changeVideoOrder('foreward')" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
                                            <path d="m321-80-71-71 329-329-329-329 71-71 400 400L321-80Z"/>
                                        </svg>
                                    </div>
                                </div>
                                <div >
                                   <completionVideo :completionVideoList = "videoHistory.completionVideoList" />
                                </div>
                            </div>
                        </li>
                        <li id="tab2" class="btnCon">
                            <input type="radio" name="tabmenu" id="tabmenu2">
                            <label for="tabmenu2">단어장</label>
                            <div class="tabCon" style="overflow-y: hidden;">
                                <Voca :wordHistory="wordHistory" />
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>    
    </body>
</template>

<script setup>
import Voca from '@/components/history/Voca.vue';
import completionVideo from '@/components/history/completionVideo.vue';
import learningVideo from '@/components/history/learningVideo.vue';

import { ref } from 'vue';
import { onMounted } from 'vue';
import { insertVideoHistory, insertWordHistory } from '@/api/history';

const videoHistory = ref({
    learningVideoList: [],
    completionVideoList: []
})

const wordHistory = ref({
    wordList: []
})

let totalVideos = 0;

let currentOrder = 0;

const curVideo = ref({
    order: "",
    videoTitle: "",
    videoUrl: "",
    lastSentence: "",
    videoLevel: "",
    completedSentenceNum: "",
    totalSentenceNum: ""
})

function setCurVideo (idx) {
    curVideo.value.order = videoHistory.value.learningVideoList[idx].order;
    curVideo.value.videoTitle = videoHistory.value.learningVideoList[idx].videoTitle;
    curVideo.value.videoUrl = videoHistory.value.learningVideoList[idx].videoUrl;
    curVideo.value.lastSentence = videoHistory.value.learningVideoList[idx].lastSentence;
    curVideo.value.videoLevel = videoHistory.value.learningVideoList[idx].videoLevel;
    curVideo.value.completedSentenceNum = videoHistory.value.learningVideoList[idx].completedSentenceNum;
    curVideo.value.totalSentenceNum = videoHistory.value.learningVideoList[idx].totalSentenceNum;
}

function changeVideoOrder(direction) {
    if (direction === 'backward') {
        if (currentOrder > 0) {
            currentOrder -= 1;
            setCurVideo(currentOrder);
        } else {
            currentOrder = totalVideos-1;
            setCurVideo(totalVideos-1);
        }
    } else if (direction === 'foreward') {
        if (currentOrder < totalVideos-1) {
            currentOrder += 1;
            setCurVideo(currentOrder);
        } else {
            currentOrder = 0;
            setCurVideo(0);
        }
    }
}


onMounted(async () => {
    videoHistory.value = await insertVideoHistory();
    wordHistory.value = insertWordHistory();
    totalVideos = videoHistory.value.learningVideoList.length;
    setCurVideo(0);
})

</script>

<style>
/* section-box */
.section-box {
    display: flex;
    flex-flow: wrap;
}

.section {
    flex-grow: 1;
    flex-basis: 300px;
    height: 500px;
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
    display: none; 
    width: 100%;
    text-align: left; 
    position: absolute; 
    left: 0;
    top: 40px; 
    box-sizing: border-box; 
    border : 1px solid #e6e6e6;
    filter: drop-shadow(2px 2px 3px rgba(0, 0, 0, 0.25));
    border-radius: 0 10px 10px 10px;
    height: 400px;
    overflow-y: scroll;
}
.learning-video{
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    flex-direction: row;
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

.Carousel-btn{
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    padding: 10px;
}
</style>