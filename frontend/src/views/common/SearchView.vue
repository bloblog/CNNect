<template>
  <div class="search-frame h-[50%] overflow-y-auto scrollbar-hide">
    <div class="text-xl mb-5">
      <span class="text-xl font-semibold">'{{ searchInput }}'</span>에 대한 제목
      검색 결과
    </div>
    <div class="grid grid-cols-4 gap-8 w-[90%] justify-center ml-[5%]">
      <div v-for="(video, index) in videoList" :key="index" class="">
        <div class="relative">
          <img
            class="w-full h-auto shadow-lg border border-gray-400"
            :src="`https://img.youtube.com/vi/${video.videoId}/mqdefault.jpg`"
          />
          <div
            class="absolute top-1 left-1 border-red-500 border-2 rounded-md text-red-500 bg-white w-1/5"
          >
            <div class="text-center font-semibold">
              Lv.{{ video.videoLevel }}
            </div>
          </div>
          <div
            class="mt-3 h-20 text-center font-semibold rounded-lg shadow-md pl-1 bg-gray-100"
          >
            {{ video.videoName }}
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="search-frame h-[30%] overflow-y-auto scrollbar-hide">
    <div class="text-xl mb-5">
      <span class="text-xl font-semibold">'{{ searchInput }}'</span>에 대한 문장
      검색 결과
    </div>
    <div class="grid grid-cols-4 gap-3 w-[90%] justify-center ml-[5%]">
      <div class="mb-[5%]" v-for="(script, index) in scriptList" :key="index">
        <div
          class="rounded-md font-semibold text-center bg-gray-100 shadow-md px-1 py-1 mb-3 h-20"
        >
          {{ script.videoName }}
        </div>
        <div
          class="rounded-md font-semibold text-center bg-red-100 shadow-md px-1 py-1 mb-3 h-32"
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
import { searchByTitle, searchByScript } from "@/api/search";

const route = useRoute();
const router = useRouter();
const searchInput = ref("");
const videoList = ref([]);
// [
//   {
//     id: "mtptFuBAg9Q",
//     title:
//       "Men are taking this class to become better husbands, brothers and sons",
//     level: 1,
//   },
//   {
//     id: "5F6YRQKZX9E",
//     title: "‘Staggering cost’: Austin blasts Putin over troop losses",
//     level: 1,
//   },
//   {
//     id: "yNPM2obgE7g",
//     title: "Why Gaetz says he's campaigning against fellow Republicans",
//     level: 2,
//   },
//   {
//     id: "LZ-px4nq8YQ",
//     title: "See how Trump is fundraising off bond deadline",
//     level: 3,
//   },
//   {
//     id: "65CI8hznDy4",
//     title:
//       "Men are taking this class to become better husbands, brothers and sons",
//     level: 1,
//   },
//   {
//     id: "xTN1IcqZvOo",
//     title: "‘Staggering cost’: Austin blasts Putin over troop losses",
//     level: 3,
//   },
//   {
//     id: "ei8wkDsxnaY",
//     title: "See how Trump is fundraising off bond deadline",
//     level: 1,
//   },
//   {
//     id: "y8Cg3LwIcZk",
//     title:
//       "Men are taking this class to become better husbands, brothers and sons",
//     level: 2,
//   },
// ];
const scriptList = ref([]);
// [
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "Amid rising concerns over cybersecurity, stringent security measures have been ...",
//   },
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "Here with me now is Homeland Security Secretary Alejandro Mayorkas.",
//   },
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "In light of recent security breaches, a comprehensive review of safety protocols has been undertaken ...",
//   },
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "Amid rising concerns over cybersecurity, stringent security measures have been ...",
//   },
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "Here with me now is Homeland Security Secretary Alejandro Mayorkas.",
//   },
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "In light of recent security breaches, a comprehensive review of safety protocols has been undertaken ...",
//   },
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "Amid rising concerns over cybersecurity, stringent security measures have been ...",
//   },
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "Here with me now is Homeland Security Secretary Alejandro Mayorkas.",
//   },
//   {
//     title: "Here Russia’s warning after Macron said Western",
//     sentence:
//       "In light of recent security breaches, a comprehensive review of safety protocols has been undertaken ...",
//   },
// ];

watch(
  () => route.query,
  (newValue, oldValue) => {
    //검색어 변경
    searchInput.value = newValue.keyword;
    searchTime()
  }
);

onMounted(() => {
  searchInput.value = route.query.keyword;
  searchTime();
});

const highlightText = (sentence, word) => {
  const regex = new RegExp(word, "gi"); // 'i' 플래그를 사용하여 대소문자를 구분하지 않음
  return sentence.replace(regex, (match) => {
    // 원본 단어의 대소문자를 유지하고 스타일을 적용
    return `<span class="text-red-500 font-semibold">${match}</span>`;
  });
};

const searchTime = () => {
  searchByTitle(
    searchInput.value,
    ({ data }) => {
      videoList.value = data.data;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );

  searchByScript(
    searchInput.value,
    ({ data }) => {
      data.data.forEach((script) => {
        if (script.sentence) {
          const index = script.sentence.indexOf(searchInput.value);
          const start = Math.max(0, index - 40);
          const end = Math.min(
            script.sentence.length,
            index + searchInput.value.length + 40
          );
          script.sentence =
            "..." + script.sentence.substring(start, end) + "...";
          console.log("변경한 text : " + script.sentence);
        }
      });
      scriptList.value = data.data;
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<style>
.scrollbar-hide {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
.scrollbar-hide::-webkit-scrollbar {
  display: none;
}
</style>
