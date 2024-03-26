<script setup>
import { ref, onMounted } from 'vue'


onMounted(() => {

})

const activeIndex = ref(1);

const goPrev = () => {
    if (activeIndex.value > 1) {
      activeIndex.value = activeIndex.value-1;
    }
};

const goNext = () => {
    if (activeIndex.value < 10) {
        activeIndex.value = activeIndex.value + 1;
    }
}

const goNum = (num) => {
    activeIndex.value = num;
    
}

const getClass = (index) => {
    return {
        'text-white bg-theme-red': index === activeIndex.value,
        'text-white bg-theme-navy': index !== activeIndex.value
    };
}

const answer = ref('');
const answerList = ref([]);

const addToAnswerList = () => {
  

  console.log(answerList.value)
};


const blank = ref("_".repeat(15));

</script>

<template>
    <div class="mt-10">
        <!-- <div>{{ $route.query.videoId }}</div> -->
        <div class="p-3 w-[60vw] relative left-[20vw]">
            <div id="quiz-container" class="min-w-fit border-gray-400 border-2">
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
                        <div class="flex items-start space-x-2">
                            <div class="text-lg font-bold">Q{{ activeIndex }}.</div>
                            <div class="text-lg font-bold">
                                Here with me now is Homeland {{ blank }} Secretary Alejandro Mayorkas.
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
                            <input v-model="answer" @input="addToAnswerList" type="text" id="helper-text" class="m-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 " placeholder="type your answer">
                        </div>

                    </div>
                </div>
    
            </div>
            <div id="button-container" class="flex justify-end m-3">
                <button @click="goPrev" class="btn back">이전</button>
                <button v-if="activeIndex != 10" @click="goNext" class="btn next">다음</button>
                <button v-else @click="submit" class="btn finish">제출</button>
            </div>
        </div>
    </div>

</template>

<style scoped>
#underbar {
    border-bottom: 2px grey solid;
    width: 8vw;
    display: block;
    margin: 0 3px 0 3px;
}

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