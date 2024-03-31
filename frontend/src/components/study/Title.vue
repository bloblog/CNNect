<script setup>
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const props = defineProps({
    videoData: Object
})


const goToQuiz = () => {
    if (props.videoData.videoId != '') {
        router.push({ path: '/quiz', query: { videoId: props.videoData.videoId } });
    }
}



</script>

<template>
    <header>
        <div class="info-box">
            <div class="level">Lv.{{ props.videoData.level }}</div>
            <div class="videoName">{{ props.videoData.videoName }}</div>
        </div>
        <div class="btn-box">
            <button @click="router.push('/')" class="btn back">나가기</button>
            <button @click="goToQuiz" class="btn finish">학습완료</button>
        </div>
    </header>
</template>

<style scoped>
header {
    position: sticky;
    top: 0;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    height: 4rem;
    background-color: #2d2d2d;
    z-index: 99;
    display: flex;
    justify-content: space-between;
    padding: 0 15px;
    gap: 10px;
}

/* header / info-box */
.info-box {
    display: flex;
    margin: auto 0;
    justify-content: space-between;
    overflow: hidden;
    text-overflow: ellipsis;
}
.videoName {
    color: #e4e4e4;
    margin: auto 5px;
    white-space: nowrap;
    font-size: 20px;
    font-weight: 800;
    padding: 0 10px;
}
.level {
    margin: auto;
    border: #9f9f9f solid 1px;
    background-color: #484848;
    color: #d4d4d4;
    font-size: 16px;
    font-weight: 600;
    padding: 3px 15px;
    border-radius: 18px;
}

/* header / btn-box */
.btn-box {
    display: flex;
    margin: auto 0;
    justify-content: space-between;
}
.btn {
    width: 100px;
    padding: 5px 0;
    color: #f4f4f4;
    border: none;
    font-weight: 600;
    border-radius: 5px;
    cursor: pointer;
    transition: transform 0.2s, opacity 0.2s, background-color 0.2s;
}
.btn:hover {
    transform: scale(1.05);
    opacity: 0.9;
}
.btn.back {
    margin-right: 10px;
    background-color: #505050;
}
.btn.back:hover {
    background-color: #7d7b7b;
}
.btn.finish {
    background-color: #CC0000;
}
.btn.finish:hover {
    background-color: #f03737;
}
</style>