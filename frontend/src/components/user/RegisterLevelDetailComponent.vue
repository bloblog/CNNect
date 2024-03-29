<template>
  <div
    v-for="item in question"
    :key="item.word_id"
    class="w-[60%] flex mb-4 border border-gray-300 rounded-lg p-4"
  >
    <div class="w-1/2 text-center text-lg font-bold">
      {{ item.word_content }}
    </div>
    <div class="w-1/2 flex items-center justify-center">
      <p class="w-3/8 h-1"></p>
      <div class="w-5/8 text-center">
        <label
          class="block items-center mr-2 text-start mb-2"
          v-for="(mean, meanIndex) in item.word_mean"
          :key="meanIndex"
        >
          <input
            type="radio"
            :name="'word-meaning-' + item.word_id"
            class="accent-red-500"
            :value="mean"
            v-model="selectedAnswers[item.word_id]"
          />
          <span class="ml-2">{{ mean }}</span>
        </label>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
const props = defineProps({
  stage: String,
});
const emit = defineEmits(["wrongCountUpdated"]);
const level = ["A1", "A2", "B1", "B2", "C1", "C2"];
import { getUserLevelTestList } from "@/api/test";

onMounted(() => {
  getUserLevelTestList(
    props.stage,
    (response) => {
      question.value = response.data;
      console.log(question.value);
    },
    (error) => {
      console.error(error);
    }
  );
});

watch(
  () => props.stage,
  async (newValue) => {
    getUserLevelTestList(
      newValue,
      (response) => {
        question.value = response.data;
        selectedAnswers.value = {};
      },
      (error) => {
        console.error(error);
      }
    );
  }
);

const question = ref([]);

const selectedAnswers = ref({});

const checkAnswers = () => {
  let count = 0;
  question.value.forEach((item) => {
    if (selectedAnswers.value[item.word_id] !== item.word_answer) {
      count++;
    }
  });
  return count;
};

defineExpose({ checkAnswers });

// 각 항목에 대해 초기 선택값을 null로 설정
question.value.forEach((item) => {
  selectedAnswers.value[item.word_id] = null;
});
</script>

<style></style>
