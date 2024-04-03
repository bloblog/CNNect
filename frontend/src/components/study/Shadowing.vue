<script setup>
import PopupDictionary from "@/components/study/PopupDictionary.vue"
import { getDict } from '@/api/scraping';
import { ref, onMounted, defineProps, watch, computed } from 'vue'
import { EventBus } from '@/api/eventBus.js';
import axios from 'axios';
import Swal from "sweetalert2";

const msg = Swal.mixin({
  position: "center",
  showConfirmButton: true,
  confirmButtonText: "확인",
  backdrop: true,
}); // alert창 기본틀

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


// NAVER CLOVA Speech API
const audioFile = ref(null);
const mediaRecorder = ref(null);
const audioChunks = ref([]);
const pronunciationScore = ref(null);
const isRecording = ref(false);

const script = ref(props.curSentence.content);

const openApiURL = '/naverapi/recog/v1/stt';

const startRecording = async () => {
    isSectionPlaying.value = false;
    if (navigator.mediaDevices?.getUserMedia) {
        // console.log('녹음 시작');
        isRecording.value = true;
        EventBus.emit('pause-video');
        try {
            const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
            mediaRecorder.value = new MediaRecorder(stream);
            audioChunks.value = [];
            
            mediaRecorder.value.ondataavailable = (event) => {
                audioChunks.value.push(event.data);
            };
            mediaRecorder.value.start();
        } catch (error) {
            console.error('마이크 권한이 거부되었습니다.', error);
            Swal.fire({
                icon: "error",
                title: "마이크 권한 거부",
                text: "브라우저 설정에서 마이크 권한을 허용해주세요.",
            });
            isRecording.value = false;
        }
    } else {
        Swal.fire({
            icon: "error",
            title: "마이크 권한 거부",
            text: "브라우저가 오디오 녹음을 지원하지 않습니다.",
        });
    }
};

const stopRecording = () => {
    if (!mediaRecorder.value) return;

    mediaRecorder.value.onstop = async () => {
        const audioBlob = new Blob(audioChunks.value, { type: 'audio/wav' });
        sendPronunciationRequest(audioBlob);
    };

    mediaRecorder.value.stop();
    // console.log('녹음 중지');
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

const startPlaying = async () => {
    const startTime = props.curSentence.startTime;
    const stopTime = props.videoData.sentenceList[props.curSentence.order].startTime;
    isSectionPlaying.value = true;
    EventBus.emit('section-play', { startTime, stopTime, callback: () => isSectionPlaying.value = false });
};

const stopPlaying = () => {
    EventBus.emit('pause-video');
    isSectionPlaying.value = false;
};

const togglePlaying = () => {
    if (isSectionPlaying.value) {
        stopPlaying();
    } else {
        startPlaying();
    }
};

const stopSectionPlay = () => {
    isSectionPlaying.value = false;
}

const sendPronunciationRequest = (audioBlob) => {
    let timerInterval;
    Swal.fire({
        title: "발음 점수 채점 중",
        html: "측정이 완료되면 자동으로 닫힙니다.",
        timer: 2000,
        timerProgressBar: true,
        didOpen: () => {
            Swal.showLoading();
            const timer = Swal.getPopup().querySelector("b");
            timerInterval = setInterval(() => {
            timer.textContent = `${Swal.getTimerLeft()}`;
            }, 100);
        },
        willClose: () => {
            clearInterval(timerInterval);
        }
        }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
            // console.log("I was closed by the timer");
        }
    });

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
        // console.log('발음평가결과:', response.data);
        pronunciationScore.value = response.data.assessment_score;
        updatePronunciationScore(pronunciationScore.value);
        clearInterval(timerInterval);
        Swal.fire({
            title: "채점 완료",
            text: `당신의 발음 점수는 ${response.data.assessment_score}점 입니다.`,
            icon: "success"
        });
    })
    .catch((error) => {
        console.error('Error:', error);
        clearInterval(timerInterval);
        Swal.fire({
            icon: "error",
            title: "채점 실패",
            text: "다시 시도해보십시오.",
        });
    });
};

</script>

<template>
    <div class="shadowing">
        <div class="top-box">
            <div class="top-left-box">
                <div class="listen" @click="togglePlaying" :class="{ 'is-section-playing': isSectionPlaying }">
                    {{ isSectionPlaying ? '일시정지' : '구간재생' }}
                </div>
                <div class="speak" @click="toggleRecording" :class="{'recording': isRecording}">
                    {{ isRecording ? '제출하기' : '발음평가' }}
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
    width: 90%;
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
/* listen */
.listen {
    margin: 10px;
    padding: 10px 20px;
    border-radius: 5px;
    border: 2px solid transparent;
    background-color: #f2f2f2;
    color: #8d8d8d;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s ease;
}
.listen:hover {
    animation: listenBorderFadeIn 0.5s forwards;
}
.listen.is-section-playing {
    color: #ffffff;
    background-color: #0000CC;
    animation: listenPulseAnimation 1s infinite;
}
@keyframes listenBorderFadeIn {
    from {
        border-color: transparent;
    }
    to {
        color: #0000CC;
        background-color: #ecedff;
        border-color: #0000CC;
    }
}
@keyframes listenPulseAnimation {
    0% {
        box-shadow: 0 0 0 0px rgba(0, 0, 204, 0.7);
    }
    70% {
        box-shadow: 0 0 0 10px rgba(0, 0, 204, 0);
    }
    100% {
        box-shadow: 0 0 0 0px rgba(0, 0, 204, 0);
    }
}
/* speak */
.speak{
    margin: 10px 10px 10px 0;
    padding: 10px 20px;
    border-radius: 5px;
    border: 2px solid transparent;
    background-color: #f2f2f2;
    color: #8d8d8d;
    font-size: 16px;
    cursor: pointer;
    transition: all 0.3s ease;
}
.speak:hover {
    animation: speakBorderFadeIn 0.5s forwards;
}
.speak.recording {
    color: #ffffff;
    background-color: #cc0000;
    animation: speakPulseAnimation 1s infinite;
}
@keyframes speakBorderFadeIn {
    from {
        border-color: transparent;
    }
    to {
        color: #CC0000;
        background-color: #fff2f2;
        border-color: #CC0000;
    }
}
@keyframes speakPulseAnimation {
    0% {
        box-shadow: 0 0 0 0px rgba(204, 0, 0, 0.7);
    }
    70% {
        box-shadow: 0 0 0 10px rgba(204, 0, 0, 0);
    }
    100% {
        box-shadow: 0 0 0 0px rgba(204, 0, 0, 0);
    }
}
/* score */
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