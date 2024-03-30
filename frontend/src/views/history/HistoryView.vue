<template>
    <div class="section-box">
        <div class="section mx-20">
            <div class="tabmenu out-tabmenu">
                <ul>
                    <li id="tab1" class="btnCon ">
                        <input type="radio" checked name="tabmenu" id="tabmenu1">
                        <label for="tabmenu1" class="font-[GmarketSansMedium]">동영상</label>
                        <div class="tabCon">
                            <h1 class="text-xl font-[GmarketSansMedium] font-bold p-3 ml-10 mt-3">학습 진행중인 뉴스</h1>
                            <div class="learning-video">
                                <div class="Carousel-btn">
                                    <svg class="sysmbol-btn" xmlns="http://www.w3.org/2000/svg" @click="changeVideoOrder('fore')" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
                                        <path d="M400-80 0-480l400-400 71 71-329 329 329 329-71 71Z"/>
                                    </svg>
                                </div>   
                                <div >
                                    <LearningVideo :curVideo="curVideo" />
                                </div>
                                <div class="Carousel-btn">
                                    <svg xmlns="http://www.w3.org/2000/svg" @click="changeVideoOrder('back')" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
                                        <path d="m321-80-71-71 329-329-329-329 71-71 400 400L321-80Z"/>
                                    </svg>
                                </div>
                            </div>
                            <div >
                                <CompletedVideo :completedVideoList = "completedVideoHistory.completedVideoHistory" />
                            </div>
                        </div>
                    </li>
                    <li id="tab2" class="btnCon">
                        <input type="radio" name="tabmenu" id="tabmenu2">
                        <label for="tabmenu2" class="font-[GmarketSansMedium]">단어장</label>
                        <div class="tabCon" style="overflow-y: hidden;">
                            <Voca :wordHistory="wordHistory" />
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>    
</template>

<script setup>
import Voca from '@/components/history/Voca.vue';
import CompletedVideo from '@/components/history/CompletedVideo.vue';
import LearningVideo from '@/components/history/LearningVideo.vue';

import { ref } from 'vue';
import { watch, onMounted } from 'vue';
import { getLearningVideo, getCompletedVideo} from '@/api/history';
import { getWordHistory } from '@/api/voca';


const learningVideoHistory = ref([])

const completedVideoHistory = ref({
    completedVideoHistory: []
})

const wordHistory = ref({
    wordList: []
})

const totalVideos = ref(0);

const currentOrder = ref(0);

const curVideo = ref({
    historyId: "",
    videoId: "",
    videoName: "",
    videoLevel: "",
    lastSentence: "",
    completedSentenceNum: "",
    totalSentenceNum: ""
})

watch(() => currentOrder, (newValue) => {
    // setCurVideo(newValue);
    // console.log(curVideo.value)
});

function setCurVideo(idx) {
    curVideo.value = learningVideoHistory.value[idx];
}

function changeVideoOrder(direction) {
    if (direction === 'fore') {
        console.log('앞')
        if (currentOrder.value > 0) {
            currentOrder.value -= 1;
            setCurVideo(currentOrder.value);
        } else {
            currentOrder.value = totalVideos.value-1;
            setCurVideo(currentOrder);
        }
    } else if (direction === 'back') {
        console.log('뒤')

        if (currentOrder.value < totalVideos.value-1) {
            currentOrder.value += 1;
            setCurVideo(currentOrder.value);
        } else {
            currentOrder.value = 0;
            setCurVideo(currentOrder);
        }
    }
}


onMounted(() => {
    getLearningVideo(
    ({ data }) => {
        console.log(data)
        learningVideoHistory.value = data.data;
        currentOrder.value = 0;
        totalVideos.value = learningVideoHistory.value.length;
        console.log(learningVideoHistory.value)
        setCurVideo(0);
    },
    (error) => {
      console.log(error);
    }
    );

    completedVideoHistory.value = getCompletedVideo();
    wordHistory.value = getWordHistory();

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