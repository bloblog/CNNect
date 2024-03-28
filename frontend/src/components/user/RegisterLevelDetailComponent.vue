<template>
  <div>hi</div>
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
      data.value = response.data;
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
        data.value = response.data;
        selectedAnswers.value = {};
      },
      (error) => {
        console.error(error);
      }
    );
  }
);

const data = ref([]);

const selectedAnswers = ref({});

const checkAnswers = () => {
  let count = 0;
  data.value.forEach((item) => {
    if (selectedAnswers.value[item.word_id] !== item.word_answer) {
      count++;
    }
  });
  return count;
};

defineExpose({ checkAnswers });

// 각 항목에 대해 초기 선택값을 null로 설정
data.value.forEach((item) => {
  selectedAnswers.value[item.word_id] = null;
});
</script>

<style></style>
