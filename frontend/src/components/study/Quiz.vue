<script setup>
import { ref, onMounted, defineProps } from 'vue'
import { useRoute } from 'vue-router';


const { VITE_GT_ACCESS_KEY, VITE_CLOVASPEECH_API_KEY } = import.meta.env;


const route = useRoute();

const currentQuiz = ref(0); // 생성할 퀴즈 번호
const q1 = ref(''); // 퀴즈 앞부분
const q2 = ref(''); // 뒷부분

const props = defineProps({
    quizData: Array,
    resultList: Array,
    answerList: Array,
    correctList: Array,
  });

  onMounted(() => {
    setQuestion();
    console.log(props.correctList);
})



const setQuestion = () => {
    // 빈칸 세팅
    q1.value = props.quizData[currentQuiz.value].question_first;
    q2.value = props.quizData[currentQuiz.value].question_second;
    blank.value = "_".repeat(props.correctList[currentQuiz.value].length * 2)
    // 해석 부분 세팅
    translateText(props.quizData[currentQuiz.value].original);

}

const activeIndex = ref(1);

const goPrev = () => {
    addToAnswerList();
    answer.value = "";
    if (activeIndex.value > 1) {
      activeIndex.value = activeIndex.value-1;
      goAnother();
    }

};

const goNext = () => {
    addToAnswerList();
    answer.value = "";
    if (activeIndex.value < 10) {
        activeIndex.value = activeIndex.value + 1;
        goAnother();
    }

}


const goNum = (num) => {
    addToAnswerList();
    answer.value = "";
    activeIndex.value = num;
    goAnother();
    
}

const goAnother = () => {
    currentQuiz.value = activeIndex.value-1;
    setQuestion();

    const existAnswer = props.answerList.find(answer => Object.keys(answer)[0] == activeIndex.value);
    if (existAnswer) {
        answer.value = existAnswer[activeIndex.value];
    }
}

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

const submit = () => {
    addToAnswerList();
    answer.value = "";

    const existAnswer = props.answerList.find(answer => Object.keys(answer)[0] == activeIndex.value);
    if (existAnswer) {
        answer.value = existAnswer[activeIndex.value];
    }

    compareAnswers();
}





const compareAnswers = () => {
    for (let i = 0; i < 10; i++) {
        const answerObj = props.answerList.find(answer => Object.keys(answer)[0] == i+1);
        if (answerObj) {
            const userAnswer = answerObj[i+1];

            const correctAnswer = props.correctList[i];
            const isCorrect = userAnswer === correctAnswer;
            props.resultList.push(isCorrect);
        } else {
            props.resultList.push(false);
        }
    }

};


const handleEnter = () => {
    if (activeIndex.value == 10) {
        submit();
    } else {
        goNext();
    }
}

const getClass = (index) => {
    return {
        'text-white bg-theme-red': index === activeIndex.value,
        'text-white bg-gray-800': index !== activeIndex.value
    };
}

const answer = ref('');
const isDuplicate = ref(false);

const addToAnswerList = () => {
    if (props.answerList.length > 0) {
        for (let i = 0; i < props.answerList.length; i++) {
            if (Object.keys(props.answerList[i])[0] == activeIndex.value && answer.value.trim() != "") {
                props.answerList[i][activeIndex.value] = answer.value;
                isDuplicate.value = true;
                break;
            }
        }
    }

    if (!isDuplicate.value && answer.value != "") {
        const newAnswer = {
            [activeIndex.value]: answer.value
        };
        props.answerList.push(newAnswer);
    }
};


const blank = ref("");

</script>

<template>
    <div>
        <div class="p-3 min-w-fit relative">
            <div id="quiz-container" class="border-gray-400 border-2">
                <div id='step-container' class="flex justify-center sm:space-x-6 p-3 bg-gray-200">
                    <div v-for="index in 10" :key="index" @click="goNum(index)" class="flex flex-col hover:cursor-pointer items-center space-x-2.5 rtl:space-x-reverse">
                        <span :class="getClass(index)" class="flex items-center justify-center w-10 h-10 rounded-full shrink-0">
                            {{ index }}
                        </span>
                    </div>
                </div>
                <div class="divider"></div>
                <div class="">
                    <div class="m-10">
                        <div class="flex items-start space-x-2">
                            <div class="text-lg font-bold">Q{{ activeIndex }}.</div>
                            <div class="text-lg font-bold">
                                {{ q1 }} <span class="text-theme-red font-extrabold">{{ blank }}</span> {{ q2 }}
                            </div>
                        </div>

                        <div class="flex ml-10 mt-3">
                            {{ translatedContent }}
                        </div>
                    </div>
                    <div class="divider"></div>
                    
                    <div id="answer" class="flex justify-end m-5">
                        <div class="flex w-1/3 items-center">
                            <label class="block mb-2 text-lg font-bold text-gray-900 ">A.</label>
                            <input v-model="answer" @keyup.enter="handleEnter()" type="text" class="m-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="type your answer">
                        </div>

                    </div>
                </div>
            </div>
            <div id="button-container" class="w-[100%] flex justify-end mt-2">
                <button @click="goPrev" class="btn back">이전</button>
                <button v-if="activeIndex != 10" @click="goNext" class="btn next">다음</button>
                <button v-else @click="submit()" class="btn finish">제출</button>
            </div>
            
        </div>
    </div>

</template>

<style scoped>

.divider {
    border: 1px rgba(160, 158, 158, 0.603) solid;
}
.btn {
    width: 100px;
    padding: 7px 0;
    color: #f4f4f4;
    border: none;
    font-weight: 600;
    border-radius: 5px;
    cursor: pointer;
    transition: transform 0.2s, opacity 0.2s, background-color 0.2s;
}
.btn:hover {
    transform: scale(1.05);
    opacity: 0.9;
}
.btn.back {
    margin-right: 10px;
    background-color: #505050;
}
.btn.back:hover {
    background-color: #7d7b7b;
}

.btn.next {
    margin-right: 10px;
    background-color: #0F1B4F;
}

.btn.next:hover {
    background-color: #43539e;
}

.btn.finish {
    margin-right: 10px;
    background-color: #CC0000;
}
.btn.finish:hover {
    background-color: #f03737;
}
</style>