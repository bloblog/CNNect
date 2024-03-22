<template>
  <div>
    
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
