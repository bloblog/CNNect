<script setup>
defineProps({
    isFinishedFetching: Boolean,
    wordMeanings: Object
})
</script>

<template>
    <div>
        <ul class="card_list">
            <li class="card" v-for="(meanings, word) in wordMeanings" :key="word">
                <div class="word"> {{ word }} </div>
                <ul class="mean_list" v-if="meanings">
                    <li class="mean_item" v-for="(meaning, idx) in meanings" :key="idx">
                        <span class="num">{{ meaning.num }}. </span>
                        <p class="mean">{{ meaning.mean }}</p>
                    </li>
                </ul>
            </li>
        </ul>
        <div class="loader-container" v-if="!isFinishedFetching">
            <div class="loader"></div>
        </div>
    </div>
</template>

<style scoped>
li.card {
    padding: 5px 15px;
    border-bottom: 1px solid #e2e2e2;
    opacity: 0;
    transform: translateY();
    opacity: 0;
    transform: translateY(20px);
    animation: cardAppear 0.5s forwards ease-out;
}
@keyframes cardAppear {
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
.word {
    color: #CC0000;
    font-size: 17px;
    font-weight: 600;
}
.mean_list {
    font-size: 15px;
    display: flex;
    flex-wrap: wrap;
    color: #281212;
}
.mean_list .mean_item .mean {
    display: inline;
    margin-right: 15px;
}
.loader-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 30px 0;
}
.loader {
    border: 5px solid #f3f3f3;
    border-top: 5px solid #cc0000;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    animation: spin 2s linear infinite;
}
@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
</style>