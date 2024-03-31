<script setup>
import { ref } from 'vue'
import Quiz from "@/components/study/Quiz.vue"
import QuizResult from "@/components/study/QuizResult.vue"

const resultList = ref([]); // 채점결과
const answerList = ref([]); // 사용자가 입력한 답안

</script>

<template>
    <div class="m-10">
        <div v-if="resultList.length == 0">
            <Quiz :resultList="resultList" :answerList="answerList"/>
        </div>
        <div v-else>
            <QuizResult :resultList="resultList" :answerList="answerList"/>
        </div>
    </div>

</template>

<style scoped>

.divider {
    border: 1px rgba(160, 158, 158, 0.603) solid;
}
.btn {
    width: 100px;
    padding: 7px 0;
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

.btn.next {
    margin-right: 10px;
    background-color: #0F1B4F;
}

.btn.next:hover {
    background-color: #43539e;
}

.btn.finish {
    margin-right: 10px;
    background-color: #CC0000;
}
.btn.finish:hover {
    background-color: #f03737;
}
</style>