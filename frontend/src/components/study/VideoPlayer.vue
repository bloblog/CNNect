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
    <div id=player-container>
        <div id="player"></div>
    </div>
</template>

<style scope>
#player-container {
    position: relative;
    width: 100%;
    padding-top: 56.25%;
    margin-bottom: 15px;
    overflow: hidden;
}
#player {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    border-radius: 10px;
}
</style>