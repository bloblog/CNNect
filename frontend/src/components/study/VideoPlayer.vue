<script setup>
import { EventBus } from '@/api/eventBus.js';
import { ref, onMounted, onUnmounted } from 'vue';

const props = defineProps({
    videoData: Object
})

const emit = defineEmits(['changeCurSentence'])

let player;

const handleSeek = (startTime) => {
    player.seekTo(startTime, true);
}

onMounted(() => {
    let tag = document.createElement('script');
    tag.src = "https://www.youtube.com/iframe_api";
    let firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

    window.onYouTubeIframeAPIReady = function() {
        player = new YT.Player('player', {
        height: '360',
        width: '640',
        videoId: props.videoData.videoId,
        events: {
            'onStateChange': onPlayerStateChange
        }
        });
    };

    EventBus.on('seek-to', handleSeek);
});

onUnmounted (() => {
    EventBus.off('seek-to', handleSeek);
})

function onPlayerStateChange(event) {
    if (event.data == YT.PlayerState.PLAYING) {
        const checkTime = () => {
        const currentTime = player.getCurrentTime();

        const nextSentence = props.videoData.sentenceList.find(sentence => {
            return currentTime < sentence.startTime;
        });

        if (nextSentence) {
            const currentSentenceOrder = (nextSentence.order - 1) === 0 ? 1 : nextSentence.order - 1;
            emit('changeCurSentence', currentSentenceOrder);
        } else {
            const lastSentenceOrder = props.videoData.sentenceList[props.videoData.sentenceList.length - 1].order;
            emit('changeCurSentence', lastSentenceOrder)
        }
    };
        
    const interval = setInterval(checkTime, 250);

    event.target.addEventListener('onStateChange', function(e) {
        if (e.data != YT.PlayerState.PLAYING) {
            clearInterval(interval);
        }
    });
    }
}

</script>

<template>
    <div>
        <div id="player"></div>
    </div>
</template>

<style scope>

</style>