import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getStudy }  from "@/api/study"

export const useStudyStore = defineStore('study', () => {
    const curOrder = ref(1);

    const videoData = ref({
        videoId: "",
        videoName: "",
        url : "",
        date: "",
        level: 3,
        category :  "",
        sentenceList: [],
        wordList: []
    })

    const curSentence = ref({
        order: "",
        startTime: "",
        content: "",
        mean: "",
        score: ""
    })

    const requestStudy = async function (videoId) {
        try {
            const response = await getStudy(videoId);
            videoData.value = response;
            curSentence.value.order = videoData.value.sentenceList[0].order;
            curSentence.value.startTime = videoData.value.sentenceList[0].startTime;
            curSentence.value.content = videoData.value.sentenceList[0].content;
            curSentence.value.mean = videoData.value.sentenceList[0].mean;
            curSentence.value.score = videoData.value.sentenceList[0].score;
            console.log('response study');
        } catch (error) {
            console.log('error study');
        }
    }


    return {
        curOrder,
        videoData,
        curSentence,
        requestStudy,
    }
})