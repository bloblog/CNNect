<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from "vue-router";

import { initFlowbite, Modal } from "flowbite";
import { updateStatus } from '@/api/history'


const { VITE_GT_ACCESS_KEY, VITE_CLOVASPEECH_API_KEY } = import.meta.env;


const route = useRoute();
const router = useRouter();

const activeIndex = ref(1);

onMounted(() => {
    setQuestion();

    const existAnswer = props.answerList.find(answer => Object.keys(answer)[0] == activeIndex.value);
    if (existAnswer) {
        answer.value = existAnswer[activeIndex.value];
    } else {
        answer.value = "미입력"
    }

    initFlowbite();
    console.log(modal.isVisible());

});

const $targetEl = document.getElementById("done-modal");

const options = {
    placement: "bottom-right",
    backdrop: "dynamic",
    backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
    closable: true,
    onHide: () => {
      console.log("modal is hidden");
    },
    onShow: () => {
      console.log("modal is shown");
    },
    onToggle: () => {
      console.log("modal has been toggled");
    },
  };

  // instance options object
  const instanceOptions = {
    id: "done-modal",
    override: false,
  };
  
  const modal = new Modal($targetEl, options, instanceOptions);


const answer = ref('');

const props = defineProps({
    quizData: Array,
    answerList: Array,
    resultList: Array, // 테스트결과
    correctList: Array, // 답지
  });

const goNum = (num) => {
    activeIndex.value = num;
    setQuestion();
    
    const existAnswer = props.answerList.find(answer => Object.keys(answer)[0] == activeIndex.value);
    if (existAnswer) {
        answer.value = existAnswer[activeIndex.value];
    } else {
        answer.value = "미입력"
    }
}




const quit = () => {
    // 뱃지 획득 여부 체크
    

    // 뱃지 모달


    // pass 인 경우
    if (answerCnt >= 6) {
        // 학습 상태 변경
        updateStatus(
            route.params.videoId,
            ({data}) => {
                console.log(data);
            },
            (error) => {
                console.log(error);
            }
        )
    } else {
        // fail 시 반영안됨 안내해주기
    }

    // router.push("/");
}

const q1 = ref(''); // 퀴즈 앞부분
const q2 = ref(''); // 뒷부분

const setQuestion = () => {
    // 빈칸 세팅
    q1.value = props.quizData[activeIndex.value-1].question_first;
    q2.value = props.quizData[activeIndex.value-1].question_second;
    // 해석 부분 세팅
    translateText(props.quizData[activeIndex.value-1].original);

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


const getClass = (index) => {
    const isAnswerExist = props.answerList.some(answer => Object.keys(answer)[0] == index);
    return {
        'text-white bg-theme-red': isAnswerExist,
        'text-white bg-gray-800': !isAnswerExist
    };
};

const answerCnt = props.resultList.filter(result => result === true).length;

</script>

<template>
    <div>
        <!-- login modal -->
      <div
        id="done-modal"
        tabindex="-1"
        aria-hidden="true"
        class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full"
      >
        <div class="relative p-4 w-full max-w-md max-h-full">
          <!-- Modal content -->
          <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
            <!-- Modal header -->
            <div
              class="flex items-center justify-between rounded-t dark:border-gray-600"
            >
              <button
                type="button"
                class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                data-modal-hide="done-modal"
              >
                <svg
                  class="w-3 h-3"
                  aria-hidden="true"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 14 14"
                >
                  <path
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                  />
                </svg>
                <span class="sr-only">Close modal</span>
              </button>
            </div>
            <!-- Modal body -->
            <div class="flex flex-col items-center">
                <div>
                  학습을 종료하시겠습니까?
                </div>
                <div class="flex justify-center">
                    <button
                        type="button"
                        class="m-2 mb-5 text-white bg-theme-red hover:bg-theme-redbrown font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                    >
                        종료
                    </button>
                    <button
                        type="button"
                        class=" text-white bg-gray-500 hover:bg-gray-600 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                    >
                        취소
                    </button>

                </div>
              
            </div>
          </div>
        </div>
      </div>
        <div class="p-3 relative">
            <div id="quiz-container" class="border-gray-400 border-2">
                <div id='step-container' class="flex justify-center sm:space-x-6 p-3 bg-gray-200">
                    <div v-for="index in 10" :key="index" @click="goNum(index)" class="flex flex-col hover:cursor-pointer items-center space-x-2.5 rtl:space-x-reverse">
                        <span :class="getClass(index)" class="flex items-center justify-center w-10 h-10 rounded-full shrink-0">
                            {{ index }}
                        </span>
                    </div>
                </div>
                <div class="divider"></div>
                <div>
                    <div class="m-10">
                        <div class="flex items-start space-x-2 relative">
                            <div class="text-lg font-bold relative z-10">Q{{ activeIndex }}.</div>
                            <div class="text-lg font-bold">
                                <img v-if="props.resultList[activeIndex-1]" src="@/assets/correct.png" class="w-16 absolute transform -translate-x-16 -translate-y-5">
                                <img v-else src="@/assets/wrong.png" class="w-16 absolute transform -translate-x-14 -translate-y-4">
                                {{ q1 }} <span class="text-theme-red font-bold underline whitespace-pre">{{ " " + props.correctList[activeIndex-1] + " " }}</span> {{ q2 }}
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
                            <input disabled v-model="answer" @keyup.enter="handleEnter" type="text" class="m-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="type your answer">
                        </div>

                    </div>
                </div>
    
            </div>
            <div class="flex justify-between m-2 items-center">
                <div class="font-bold text-lg whitespace-pre">
                    맞은 문항 수 : {{ answerCnt }} / 10 <span class="font-bold text-theme-red text-xl italic">{{ answerCnt >= 6 ? "Pass" : "Fail" }}</span>
                </div>
                <div id="button-container" class="flex justify- mt-3">
                    <button
                        @click="quit()" 
                        class="btn finish"
                        >나가기</button>
                </div>
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

.btn.finish {
    margin-right: 10px;
    background-color: #CC0000;
}
.btn.finish:hover {
    background-color: #f03737;
}
</style>