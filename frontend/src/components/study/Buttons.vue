<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
</script>

<template>
    <div class="btn-box">
        <button @click="router.go(-1)" class="btn back">나가기</button>
        <button @click="router.go(-1)" class="btn finish">학습완료</button>
    </div>
</template>

<style scoped>
.btn-box {
    display: flex;
    justify-content: flex-end;
    margin: 10px;
}
.btn {
    width: 100px;
    padding: 7px 0;
    color: #f4f4f4;
    border: none;
    font-weight: 600;
    border-radius: 5px;
}
.btn.back {
    margin-right: 10px;
    background-color: #0F1B4F;
}
.btn.finish {
    background-color: #CC0000;
}
</style>