<template>
  <h3>제시 된 단어의 의미를 선택해주세요</h3>
  <RegisterLevelDetail :stage="steps[level - 1]"></RegisterLevelDetail>
  <div class="flex items-center w-full justify-center">
    <button
      @click="nextStep(2)"
      class="items-center mx-3 text-white bg-gray-400 hover:bg-gray-500 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center"
    >
      그만두기
    </button>
    <button
      @click="level < 6 ? level++ : registerComplete()"
      class="items-center text-white bg-theme-red hover:bg-theme-redbrown focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center"
    >
      {{ level < 6 ? "다음" : "제출" }}
    </button>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import RegisterLevelDetail from "@/components/user/RegisterLevelDetailComponent.vue";

const steps = ref(["A1", "A2", "B1", "B2", "C1", "C2"]);
const level = ref(1);
const emit = defineEmits(["updateLevel"]);

watch(level, (newValue) => {
  emit("updateLevel", newValue);
});
</script>

<style></style>
