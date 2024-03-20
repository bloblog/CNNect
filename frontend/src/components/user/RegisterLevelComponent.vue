<template>
  <div class="flex flex-col justify-center h-full w-full items-center">
    <h3>Lv. {{ level }} 제시 된 단어의 의미를 선택해주세요</h3>
    <RegisterLevelDetail :stage="steps[level - 1]" ref="registerLevelDetail" />

    <div class="flex items-center w-full justify-center">
      <button
        @click="nextStep(2)"
        class="mx-3 text-white bg-gray-400 hover:bg-gray-500 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5"
      >
        그만두기
      </button>
      <button
        @click="nextLevel"
        class="text-white bg-theme-red hover:bg-theme-redbrown focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5"
      >
        {{ level < 6 ? "다음" : "제출" }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import RegisterLevelDetail from "@/components/user/RegisterLevelDetailComponent.vue";

const steps = ref(["A1", "A2", "B1", "B2", "C1", "C2"]);
const level = ref(1);
const registerLevelDetail = ref(null);
const emit = defineEmits(["updateLevel"]);

watch(level, (newValue) => {
  // 'updateLevel' 이벤트를 발생시키고, 새로운 level 값을 전달합니다.
  emit("updateLevel", newValue);
});

const nextLevel = () => {
  const wrongCount = registerLevelDetail.value.checkAnswers();

  if (wrongCount > 2) {
    alert("통과 불가");
    // updateLevel() <- 유저의 레벨 업데이트
  } else {
    console.log(level.value);
    if (level.value == 6) {
      alert("최종 레벨");
      // updateLevel() <- 유저의 레벨 업데이트
    } else {
      level.value++;
    }
  }
};
</script>
