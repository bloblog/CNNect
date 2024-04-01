<script setup>
import { EventBus } from '@/api/eventBus.js';

const props = defineProps({
    videoData: Object,
    curSentence: Object
})

const emit = defineEmits(['changeCurOrder'])

const seekTo = (startTime) => {
    EventBus.emit('seek-to', startTime);
}

const handleClick = (sentence) => {
    seekTo(sentence.startTime);
    emit('changeCurOrder', sentence.order);
};

</script>

<template>
    <ul>
        <li v-for="sentence in props.videoData.sentenceList" :key="sentence.order"
            @click="handleClick(sentence)"
            :class="{ 'active': props.curSentence.order === sentence.order }">
                <div class="content"> 
                    {{ sentence.content }} 
                </div>
                <div class="score" :class="{'noScore': sentence.score === null}"> 
                    {{ sentence.score != null ?  sentence.score : "도전"}}  
                </div>
        </li>
    </ul>
</template>

<style scoped>
ul {
    opacity: 0;
    transform: translateY();
    opacity: 0;
    transform: translateY(20px);
    animation: scriptAppear 0.5s forwards ease-out;
}
@keyframes scriptAppear {
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
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
    line-height: 1.6;
    margin: auto 0 ;
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