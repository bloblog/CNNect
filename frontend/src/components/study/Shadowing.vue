<script setup>
import PopupDictionary from "@/components/study/PopupDictionary.vue"
import { getDict } from '@/api/scraping';
import { ref, onMounted, defineProps, watch } from 'vue'
import axios from 'axios';

const { VITE_GT_ACCESS_KEY, VITE_ETRI_ACCESS_KEY } = import.meta.env;

const props = defineProps({
    curSentence: Object
});

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
    }
}, { immediate: true });

const selectedText = ref('');
const isShowPopup = ref(false);
const translatedContent = ref('');
const selectedWordMeanings = ref([]);


const handleMouseUp = () => {
    selectedText.value = window.getSelection().toString().trim();
    if (selectedText.value.length > 0 ) {
        getDict(selectedText.value).then(meanList => {
            selectedWordMeanings.value = meanList;
            console.log(meanList);
            isShowPopup.value = true;
        }).catch(error => {
            console.error(error);
        })
    }
};

const hidePopup = () => {
    selectedText.value = '';
    isShowPopup.value = false;
};


//Google Translate API Key
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


//발음 API
const audioFile = ref(null);
const mediaRecorder = ref(null);
const audioChunks = ref([]);
const pronunciationScore = ref(null);
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
    console.log('발음 점수 : ', pronunciationScore.value)
  })
  .catch((error) => {
    console.error('Error:', error);
  });
};

</script>



<template>
    <div class="shadowing">
        <div class="above-box">
            <div class="korean">
            {{ translatedContent }}
            </div>
            <div class="english" @dblclick="handleMouseUp">
                {{ curSentence.content }}
                <PopupDictionary v-if="isShowPopup" :selectedText="selectedText" :selectedWordMeanings="selectedWordMeanings" @close-popup="hidePopup"></PopupDictionary>
            </div>
        </div>
        <div class="below-box">
            <div class="below-left-box">
                <div class="listen">
                    <span class="material-symbols-outlined">
                        volume_up
                    </span>
                </div>
                <div class="speack" @dblclick="toggleRecording">
                    <span class="material-symbols-outlined">
                        {{ isRecording ? 'stop' : 'mic' }}
                    </span>
                </div>
            </div>
            <div class="below-right-box">
                <div class="score" :class="{'noScore': pronunciationScore === null}">
                    {{ pronunciationScore != null ?  pronunciationScore : "도전"}}
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
::selection {
    background: rgba(204, 0, 0, 0.15);
}
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
    justify-content: space-between;
}
.shadowing .above-box {
    margin: 10px;
}
.shadowing .above-box .korean {
    color: #8A8A8A;
    padding: 15px;

}
.shadowing .above-box .english {
    color: #000000;
    font-weight: 600;
    font-size: 18px;
    padding: 15px;
}
.shadowing .below-box {
    border-top: #E3E3E3 solid 1px;
    height: 50px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.shadowing .below-left-box {
    display: flex;
}

.shadowing .below-left-box .listen span {
    box-sizing: border-box;
    padding: 10px 20px;
    height: 50px;
    border-right: #E3E3E3 1px solid;
}
.shadowing .below-left-box .speack {
    padding: 10px 20px;
    border-right: #E3E3E3 1px solid;

}
.shadowing .below-right-box .score {
    border: #c8c8c8 1px solid;
    border-radius: 20px;
    padding: 7px 20px;
    margin-right: 15px;
    color: #CC0000;
    font-weight: 600;
}
.shadowing .below-right-box .noScore {
    color: #b3b3b3;
}
</style>