<template>
  <div class="search-frame overflow-y-auto scrollbar-hide">
    <div class="text-xl mb-3">
      <span class="text-xl font-semibold">'{{ searchInput }}'</span>에 대한 제목
      검색 결과 n건
    </div>
    <div class="grid grid-cols-5 gap-3">
      <div v-for="(video, index) in videoList" :key="index">
        <div class="relative">
          <img
            class="w-full h-auto shadow-lg"
            :src="`https://img.youtube.com/vi/${video.id}/mqdefault.jpg`"
          />
          <div
            class="absolute top-1 left-1 border-red-500 border-2 rounded-md text-red-500 bg-white w-1/5"
          >
            <div class="text-center font-semibold">Lv.{{ video.level }}</div>
          </div>
          <div class="mt-2 font-semibold rounded-lg shadow-md pl-1 bg-gray-100">
            {{ video.title }}
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="search-frame overflow-y-auto scrollbar-hide">
    <div class="text-xl mb-3">
      <span class="text-xl font-semibold">'{{ searchInput }}'</span>에 대한 문장
      검색 결과 n건
    </div>
    <div class="grid grid-cols-5 gap-3">
      <div class="mb-[5%]" v-for="(script, index) in scriptList" :key="index">
        <div
          class="rounded-md font-semibold bg-gray-100 shadow-md px-1 py-1 mb-3 h-[40%]"
        >
          {{ script.title }}
        </div>
        <div
          class="rounded-md font-semibold bg-red-100 shadow-md px-1 py-1 mb-3 h-[50%]"
        >
          <span v-html="highlightText(script.sentence, searchInput)"></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const searchInput = ref("");
const videoList = [
  {
    id: "mtptFuBAg9Q",
    title:
      "Men are taking this class to become better husbands, brothers and sons",
    level: 1,
  },
  {
    id: "5F6YRQKZX9E",
    title: "‘Staggering cost’: Austin blasts Putin over troop losses",
    level: 1,
  },
  {
    id: "yNPM2obgE7g",
    title: "Why Gaetz says he's campaigning against fellow Republicans",
    level: 2,
  },
  {
    id: "LZ-px4nq8YQ",
    title: "See how Trump is fundraising off bond deadline",
    level: 3,
  },
  {
    id: "65CI8hznDy4",
    title:
      "Men are taking this class to become better husbands, brothers and sons",
    level: 1,
  },
  {
    id: "xTN1IcqZvOo",
    title: "‘Staggering cost’: Austin blasts Putin over troop losses",
    level: 3,
  },
  {
    id: "ei8wkDsxnaY",
    title: "See how Trump is fundraising off bond deadline",
    level: 1,
  },
  {
    id: "y8Cg3LwIcZk",
    title:
      "Men are taking this class to become better husbands, brothers and sons",
    level: 2,
  },
];
const scriptList = [
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "Amid rising concerns over cybersecurity, stringent security measures have been ...",
  },
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "Here with me now is Homeland Security Secretary Alejandro Mayorkas.",
  },
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "In light of recent security breaches, a comprehensive review of safety protocols has been undertaken ...",
  },
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "Amid rising concerns over cybersecurity, stringent security measures have been ...",
  },
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "Here with me now is Homeland Security Secretary Alejandro Mayorkas.",
  },
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "In light of recent security breaches, a comprehensive review of safety protocols has been undertaken ...",
  },
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "Amid rising concerns over cybersecurity, stringent security measures have been ...",
  },
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "Here with me now is Homeland Security Secretary Alejandro Mayorkas.",
  },
  {
    title: "Here Russia’s warning after Macron said Western",
    sentence:
      "In light of recent security breaches, a comprehensive review of safety protocols has been undertaken ...",
  },
];

watch(
  () => route.query,
  (newValue, oldValue) => {
    //검색어 변경
    searchInput.value = newValue.keyword;
  }
);

onMounted(() => {
  searchInput.value = route.query.keyword;
});

const highlightText = (sentence, word) => {
  const regex = new RegExp(word, "gi"); // 'i' 플래그를 사용하여 대소문자를 구분하지 않음
  return sentence.replace(regex, (match) => {
    // 원본 단어의 대소문자를 유지하고 스타일을 적용
    return `<span class="text-red-500 font-semibold">${match}</span>`;
  });
};
</script>

<style></style>
