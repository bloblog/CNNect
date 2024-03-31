<template>
  <div class="relative flex flex-col justify-center items-center h-screen">
    <div class="relative inline-block">
      <!-- 배경 투명도 박스 -->
      <div class="z-10">
        <div class="font-bold text-2xl mb-5">
          당신의 영어실력은
          <span class="font-bold text-6xl text-red-700"> Lv.{{ level }}</span>
          입니다.
        </div>
        <div class="relative w-[100%] h-4 bottom-8 bg-red-600 opacity-15"></div>
      </div>
    </div>

    <div class="formatted-text">
      <!-- 각 문장을 별도의 div로 래핑 -->
      <div
        v-for="(item, index) in formattedData"
        :key="index"
        v-html="item"
        class="paragraph"
      ></div>
    </div>
    <!-- router 추후 수정 -->
    <button
      class="ml-4 mt-7 text-white bg-theme-red hover:bg-theme-redbrown focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5"
      data-modal-hide="stop-modal"
      @click="finishRegister()"
    >
      학습하러 가기
    </button>
  </div>
</template>

<script setup>
import { ref, computed, defineProps } from "vue";
import { useRoute, useRouter } from "vue-router";
import { userStore } from "@/stores/userStore";

const router = useRouter();
const uStore = userStore();

const { setLogin, setLevel } = uStore;

const props = defineProps({
  level: Number,
});

const data = ref([
  "매우 기초적이고, 일상적인 영어 표현을 사용할 수 있습니다. <br> 조심스럽고 정확한 발음, 긴 호흡을 포함한 느린 의사 전달이 가능합니다. <br> 매우 짧고 단순한 지문, 친숙한 이름들과 단어들을 이해할 수 있습니다.",
  "매우 기초적인 수준의 개인적, 가족 혹은 업무관련 용어를 이해할 수 있습니다. <br> 느리고, 명료한 의사표현의 요구사항을 충족할 수 있습니다. <br> 친숙한 주제의 짧고 단순한 지문을 이해할 수 있습니다.",
  "직장, 학교 또는 여행 중에 공통 주제의 중요 요점을 이해할 수 있습니다. <br> 명료한 연설의 전반적이고 세부적인 디테일을 이해할 수 있습니다. <br> 관심있는 주제의 사실에 대한 지문을 이해할 수 있습니다.",
  "복잡한 지문의 주요 아이디어를 이해할 수 있습니다. <br> 표준 구어, 방송 프로그램의 내용을 이해할 수 있습니다. <br> 많은 단어와 복잡한 문법으로 구성된 지문들을 이해할 수 있습니다.",
  "추상적인 주제에 대한 긴 연설을 상대적으로 쉽게 이해할 수 있습니다. <br> 더 긴 지문과 복잡한 의미를 이해할 수 있습니다. <br> 전문분야가 아니더라도 복잡한 지문의 상세한 디테일을 이해할 수 있습니다.",
  "듣고 읽는 모든 것을 쉽게 이해할 수 있습니다. <br> 원어민이 빠른 속도로 말하는 것을 이해할 수 있습니다. <br> 추상적, 구조적으로 복잡한 텍스트와 문학작품을 이해할 수 있습니다.",
]);

const formattedData = computed(() =>
  data.value[props.level - 1].split("<br>").map((line) => line.trim())
);

const finishRegister = () => { // 회원가입 종료시 자동 로그인 처리
  setLevel(props.level);
  setLogin();
  window.location.href = "/"
  // router.push('/')
}
</script>

<style scoped>
.position-relative {
  position: relative;
}

.paragraph {
  margin-bottom: 0.3rem; /* 원하는 간격으로 조정 */
}
</style>
