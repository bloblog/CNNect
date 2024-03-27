<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const activeIndex = ref(1);

onMounted(() => {
    const existAnswer = props.answerList.find(answer => Object.keys(answer)[0] == activeIndex.value);
    if (existAnswer) {
        answer.value = existAnswer[activeIndex.value];
    } else {
        answer.value = "미입력"
    }
});

const answer = ref();

const props = defineProps({
    resultList: Array,
    answerList: Array
  });

const goNum = (num) => {
    activeIndex.value = num;
    
    const existAnswer = props.answerList.find(answer => Object.keys(answer)[0] == activeIndex.value);
    if (existAnswer) {
        answer.value = existAnswer[activeIndex.value];
    } else {
        answer.value = "미입력"
    }
}

const Quit = () => {
    router.push("/");
}

const correctList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];


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
        <!-- <div>{{ $route.query.videoId }}</div> -->
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
                                <img v-if="props.resultList[activeIndex - 1]" src="@/assets/correct.png" class="w-16 absolute transform -translate-x-16 -translate-y-5">
                                <img v-else src="@/assets/wrong.png" class="w-16 absolute transform -translate-x-14 -translate-y-4">
                                Here with me now is Homeland <span class="text-theme-red font-bold underline whitespace-pre">{{ " " + correctList[activeIndex-1] + " " }}</span> Secretary Alejandro Mayorkas.
                            </div>
                        </div>

                        <div class="flex ml-10 mt-3">
                            저와 함께 여기 알레한드로 마요르카스 국토안보부 장관이 있습니다.
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
                    <button @click="Quit" class="btn finish">나가기</button>
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