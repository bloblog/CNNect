<script setup>
import PopupDictionary from "@/components/study/PopupDictionary.vue"
import { getDict } from '@/api/scraping';
import { ref, onMounted, defineProps, watch, computed } from 'vue'
import { EventBus } from '@/api/eventBus.js';
import axios from 'axios';

const { VITE_GT_ACCESS_KEY, VITE_CLOVASPEECH_API_KEY } = import.meta.env;

const props = defineProps({
    videoData: Object,
    curSentence: Object
});

const emit = defineEmits(['updatePronunciationScore'])

const updatePronunciationScore = (score) => {
    emit('updatePronunciationScore', props.curSentence.order, score);
};

onMounted(() => {
    try {
        if (props.curSentence && props.curSentence.content) {
            translateText(props.curSentence.content);
        }
    } catch (error) {
        console.error("Mounted hook error:", error);
    }
    EventBus.on('stop-section-play', stopSectionPlay);
});

watch(() => props.curSentence.content, (newContent) => {
    if (newContent) {
        translateText(newContent);
        hidePopup();
    }
}, { immediate: true });


// 다음 영어사전 팝업창
const words = computed(() => {
  return props.curSentence.content.split(' ');
});
const isInWordList = computed(() => {
  return words.value.map((word) => props.videoData.wordList.includes(word));
});
const hover = ref(null)
const selectedText = ref('');
const selectedWordIndex = ref(null);
const isShowPopup = ref(false);
const selectedWordMeanings = ref([]);
let latestRequestTime = 0;
const isFinishedFetchingPopup = ref(false);

const showPopup = async (word, index) => {
    isFinishedFetchingPopup.value = false;
    selectedText.value = word;
    selectedWordIndex.value = index;
    
    if (selectedText.value.length > 0 ) {
        isShowPopup.value = true;
        const currentTime = Date.now();
        latestRequestTime = currentTime;
        const meanList = await getDict(selectedText.value);

        if (meanList !== null && currentTime === latestRequestTime) {
            selectedWordMeanings.value = meanList;
        }
        isFinishedFetchingPopup.value = true;
    }
};

const hidePopup = () => {
    selectedText.value = '';
    selectedWordIndex.value = null;
    isShowPopup.value = false;
    selectedWordMeanings.value = [];
};


// Google Translate API
const translatedContent = ref('');

function translateText(textToTranslate) {
    fetch(`https://translation.googleapis.com/language/translate/v2?key=${VITE_GT_ACCESS_KEY}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            q: textToTranslate,
            source: "en",
            target: "ko"
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        translatedContent.value = data.data.translations[0].translatedText;
    })
    .catch(error => console.error("번역 오류:", error));
}


// 발음 평가 API
const audioFile = ref(null);
const mediaRecorder = ref(null);
const audioChunks = ref([]);
const pronunciationScore = ref(null);
const isRecording = ref(false);

const script = ref(props.curSentence.content);

const openApiURL = '/naverapi/recog/v1/stt';

const startRecording = async () => {
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        console.log('녹음 시작');
        isRecording.value = true;
        EventBus.emit('pause-video');
        const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
        mediaRecorder.value = new MediaRecorder(stream);
        audioChunks.value = [];
        
        mediaRecorder.value.ondataavailable = (event) => {
        audioChunks.value.push(event.data);
        };
        
        mediaRecorder.value.start();

    } else {
        console.error('브라우저가 오디오 녹음을 지원하지 않습니다.');
    }
};

const stopRecording = () => {
    if (!mediaRecorder.value) return;

    mediaRecorder.value.onstop = async () => {
        const audioBlob = new Blob(audioChunks.value, { type: 'audio/wav' });
        sendPronunciationRequest(audioBlob);
    };

    mediaRecorder.value.stop();
    console.log('녹음 중지');
    isRecording.value = false;
    };

    const toggleRecording = () => {
    if (isRecording.value) {
        stopRecording();
    } else {
        startRecording();
    }
};
const isSectionPlaying = ref(false);

const sectionPlay = () => {
    const startTime = props.curSentence.startTime;
    const stopTime = props.videoData.sentenceList[props.curSentence.order].startTime;
    isSectionPlaying.value = true;
    EventBus.emit('section-play', { startTime, stopTime, callback: () => isSectionPlaying.value = false });
}

const stopSectionPlay = () => {
    isSectionPlaying.value = false;
}

const sendPronunciationRequest = (audioBlob) => {
    axios.post(openApiURL, audioBlob, {
        headers: {
        'Content-Type': 'application/octet-stream',
        'X-CLOVASPEECH-API-KEY': VITE_CLOVASPEECH_API_KEY
        },
        params: {
            utterance: props.curSentence.content,
            lang: "Eng",
            assessment: true,
            graph: true
        },
    })
    .then((response) => {
        console.log('response = ', response.data.assessment_score);
        console.log('response = ', response.data);
        pronunciationScore.value = response.data.assessment_score;
        updatePronunciationScore(pronunciationScore.value);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
};
</script>

<template>
    <div class="shadowing">
        <div class="top-box">
            <div class="top-left-box">
                <div class="listen" @click="sectionPlay" :class="{ 'is-section-playing': isSectionPlaying }">
                    <!-- 스피커 아이콘 -->
                    <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 -960 960 960" width="24">
                        <path d="M560-131v-82q90-26 145-100t55-168q0-94-55-168T560-749v-82q124 28 202 125.5T840-481q0 127-78 224.5T560-131ZM120-360v-240h160l200-200v640L280-360H120Zm440 40v-322q47 22 73.5 66t26.5 96q0 51-26.5 94.5T560-320ZM400-606l-86 86H200v80h114l86 86v-252ZM300-480Z"/>
                    </svg>
                </div>
                <div class="speack" @click="toggleRecording" :class="{'recording': isRecording}">
                    
                    <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 -960 960 960" width="24">
                        <template v-if="isRecording">
                            <!-- 정지 아이콘 -->
                            <path d="M320-640v320-320Zm-80 400v-480h480v480H240Zm80-80h320v-320H320v320Z"/>
                        </template>
                        <template v-else>
                            <!-- 마이크 아이콘 -->
                            <path d="M480-400q-50 0-85-35t-35-85v-240q0-50 35-85t85-35q50 0 85 35t35 85v240q0 50-35 85t-85 35Zm0-240Zm-40 520v-123q-104-14-172-93t-68-184h80q0 83 58.5 141.5T480-320q83 0 141.5-58.5T680-520h80q0 105-68 184t-172 93v123h-80Zm40-360q17 0 28.5-11.5T520-520v-240q0-17-11.5-28.5T480-800q-17 0-28.5 11.5T440-760v240q0 17 11.5 28.5T480-480Z"/>
                        </template>
                    </svg>
                </div>
            </div>
            <div class="top-right-box">
                <div class="score" :class="{'noScore': props.curSentence.score === null}">
                    {{ props.curSentence.score != null ?  props.curSentence.score : "도전"}}
                </div>
            </div>
        </div>
        <div class="bottom-box">
            <div class="korean">
            {{ translatedContent }}
            </div>
            <div class="english">
                <span class="space-separated-word" v-for="(word, index) in words" :key="index" @click="showPopup(word, index)" @mouseover="hover = index" @mouseleave="hover = null" :class="{'highlight-hover': hover === index, 'highlight-selected': selectedWordIndex === index, 'highlight-red': isInWordList[index]}">
                    {{ word }}
                    <span class="space" v-if="index < words.length - 1"> </span>
                </span>
                <PopupDictionary v-if="isShowPopup" :selectedText="selectedText" :selectedWordMeanings="selectedWordMeanings" :isFinishedFetchingPopup="isFinishedFetchingPopup" @close-popup="hidePopup"></PopupDictionary>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* shadowing */
.shadowing {
    width: 100%;
    height: 100%;
    background-color: #ffffff;
    border: 1px solid #CDCDCD;
    box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.25);
    border-radius: 10px;
    display: flex;
    flex-direction: column;
}

/* shadowing / top-box */
.top-box {
    border-bottom: #E3E3E3 solid 1px;
    height: 50px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.top-left-box {
    display: flex;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #797979;
}
.listen {
    padding: 10px 20px;
    border-right: #E3E3E3 1px solid;
    fill: #8d8d8d;
}
.listen.is-section-playing {
    fill: #cc0000
}
.speack {
    padding: 10px 20px;
    border-right: #E3E3E3 1px solid;
    fill: #8d8d8d;
}
.speack.recording {
    fill: #cc0000
}

.top-right-box .score {
    border: #c8c8c8 1px solid;
    border-radius: 20px;
    padding: 5px 20px;
    margin-right: 15px;
    color: #CC0000;
    font-weight: 600;
    width: 80px;
    text-align: center;
}
.top-right-box .noScore {
    color: #b3b3b3;
}

/* shadowing / bottom-box */
.bottom-box {
    margin: 10px;
    line-height: 1.5;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    overflow-y: auto; 
    max-height: calc(25vh);
}
.bottom-box .korean {
    color: #8A8A8A;
    padding: 15px;
}
.bottom-box .english {
    flex-grow: 1;
    background-color: #f2f2f2;
    color: #000000;
    font-weight: 600;
    font-size: 18px;
    padding: 15px;
}
.english .space-separated-word {
    cursor: pointer;
}
.highlight-hover {
  background: rgba(204, 0, 0, 0.15);
}
.highlight-selected {
  background: rgba(204, 0, 0, 0.15);
}
.highlight-red {
  color: #cc0000;
}
</style>