<script setup>
import { storeToRefs } from 'pinia'
import { useStudyStore } from '@/stores/studyStore.js'
const studyStore = useStudyStore()
const { videoData, curSentence } = storeToRefs(studyStore)

const getVideoUrl = (videoId, startTime) => {
    return `https://www.youtube.com/embed/${videoId}?start=${startTime}&autoplay=1&rel=0`
}

</script>

<template>
    <div class="video">
        <iframe width="100%" height="auto"
            :src="getVideoUrl(videoData.videoId, curSentence.startTime)"
            title="YouTube video player"
            frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share;"
            allowfullscreen></iframe>
    </div>
</template>

<style scoped>
.video {
    position: relative;
    width: 100%;
    padding-bottom: 56.25%;
    margin-bottom: 15px;
}
.video iframe {
    position: absolute;
    width: 100%;
    height: 100%;
    border-radius: 5px;
}
</style>