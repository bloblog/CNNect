<template>
  {{ stage }}
  <!-- <div v-for="(item, index) in data" :key="item.word_id" class="mb-4">
    {{ item.word_content }}
    <div class="flex flex-wrap items-center justify-center mt-2">
      <label
        class="inline-flex items-center mx-2"
        v-for="(mean, meanIndex) in item.word_mean"
        :key="meanIndex"
      >
        <input
          type="radio"
          name="word-meaning-{{ item.word_id }}"
          class="form-radio"
          :value="mean"
          v-model="selectedAnswers[index]"
        />
        <span class="ml-2">{{ mean }}</span>
      </label>
    </div>
  </div>
  <div class="flex items-center justify-center mt-5">
    <button
      @click="checkAnswers"
      class="mx-3 text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
    >
      제출
    </button>
  </div> -->
</template>

<script setup>
import { ref } from "vue";
const props = defineProps({
  stage: String,
});
</script>

<style></style>
