<script setup>
defineProps({
    videoData: Object
})
</script>

<template>
    <ul>
        <li v-for="word in videoData.wordList" :key="word">
            <div class="word"> {{ word }} </div>
            <div class="mean"> 이곳에 번역 api를 통해 영단어의 한글의미가 나타납니다. </div>
        </li>
    </ul>
</template>

<style scoped>
ul li {
    border: 1px solid #e2e2e2;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    margin: 15px;
    padding: 0 10px;
    display: flex;
    flex-direction: column;
    
}
.word {
    color: #CC0000;
    font-weight: 600;
}
.mean {
    
}
</style>