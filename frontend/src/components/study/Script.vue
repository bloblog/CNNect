<script setup>
import { storeToRefs } from 'pinia'
import { useStudyStore } from '@/stores/studyStore.js'

const studyStore = useStudyStore()
const { videoData } = storeToRefs(studyStore)

const handleClick = (order) => {
    studyStore.curSentence.order = order;
    studyStore.curSentence.startTime = videoData.value.sentenceList[order-1].startTime
    studyStore.curSentence.content = videoData.value.sentenceList[order-1].content
    studyStore.curSentence.mean = videoData.value.sentenceList[order-1].mean
    studyStore.curSentence.score = videoData.value.sentenceList[order-1].score
};
</script>

<template>
    <ul>
        <li v-for="sentence in videoData.sentenceList" :key="sentence.order" 
            :class="{ 'active': studyStore.curSentence.order === sentence.order }"
            @click="handleClick(sentence.order)">
                <div class="content"> {{ sentence.content }} </div>
                <div class="score" :class="{ 'noScore': sentence.score === null}"> {{ sentence.score != null ?  sentence.score : "도전"}}  </div>
        </li>
    </ul>
</template>

<style scoped>

ul li {
    border-bottom: #E3E3E3 solid 1px;
    padding: 15px;
    display: flex;
    gap: 10px;
    cursor: pointer;
}
ul li.active,
ul li:hover {
    background-color: #F2F2F2;
}
ul li .content {
    flex-grow: 1;
    line-height: 1.5;
}
ul li .score {
    border: #c8c8c8 1px solid;
    border-radius: 20px;
    padding: 0 15px;
    margin: auto;
    color: #CC0000;
    line-height: 30px;
    font-size: 12px;
    min-width: 60px;
    text-align: center;
}
ul li .noScore{
    color: #b3b3b3;
}
</style>