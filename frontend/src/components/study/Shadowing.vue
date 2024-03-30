<script setup>
import PopupDictionary from "@/components/study/PopupDictionary.vue"
import { getDict } from '@/api/scraping';
import { ref, onMounted, defineProps, watch, computed } from 'vue'
import axios from 'axios';

const { VITE_GT_ACCESS_KEY, VITE_ETRI_ACCESS_KEY } = import.meta.env;

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
const pronunciationScore = ref(null); // 현재 문장의 발음 점수로 초기화
const isRecording = ref(false);

const openApiURL = 'http://aiopen.etri.re.kr:8000/WiseASR/Pronunciation'; // 영어
const languageCode = 'english';
const script = props.curSentence.content;

const startRecording = async () => {
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        console.log('녹음 시작');
        isRecording.value = true;
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
        const audioData = await fileToBase64(audioBlob);
        sendPronunciationRequest(audioData);
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

// Base64로 변환
const fileToBase64 = (blob) => {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
        reader.readAsDataURL(blob);
    });
};

const sendPronunciationRequest = (audioData) => {
    const requestJson = {
        argument: {
            language_code: languageCode,
            script: script,
            audio: audioData.split(',')[1], 
        },
    };

    axios.post(openApiURL, requestJson, {
        headers: {
        'Content-Type': 'application/json',
        'Authorization': VITE_ETRI_ACCESS_KEY
        },
    })
    .then((response) => {
        console.log('responseCode = ', response.status);
        console.log('responseBody = ', response.data);
        pronunciationScore.value = response.data.return_object.score;
        console.log('발음 점수 : ', pronunciationScore.value);
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
                <div class="listen">
                    <span class="material-symbols-outlined">volume_up</span>
                </div>
                <div class="speack" @click="toggleRecording" :class="{'recording': isRecording}">
                    <span class="material-symbols-outlined">
                        {{ isRecording ? 'stop' : 'mic' }}
                    </span>
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
                <span class="space-separated-word" v-for="(word, index) in words" :key="index" @click="showPopup(word, index)" @mouseover="hover = index" @mouseleave="hover = null" :class="{'highlight-hover': hover === index, 'highlight-selected': selectedWordIndex === index}">
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
    border: #0F1B4F 2px solid;
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
}
.listen:hover {
    color: #10193e;
}
.listening {
    color: #10193e;
}
.speack {
    padding: 10px 20px;
    border-right: #E3E3E3 1px solid;
}
.speack:hover {
    color: #cc0000;
}
.recording {
    color: #cc0000;
}
.listen span,
.speack span {
    font-size: 25px;
}
.top-right-box .score {
    border: #c8c8c8 1px solid;
    border-radius: 20px;
    padding: 7px 20px;
    margin-right: 15px;
    color: #CC0000;
    font-weight: 600;
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
</style>