<template>
  <div
    class="search-frame h-[50%] overflow-y-auto scrollbar-hide"
    ref="titleContainer"
  >
    <div class="text-xl mb-5">
      <span class="text-xl font-semibold highlight">'{{ searchInput }}'</span>에 대한 제목
      검색 결과
    </div>
    <div
      v-if="videoViewList.length == 0"
      class="text-center flex items-center justify-center"
    >
      <div class="z-20 text-lg font-bold highlight pl-2 pr-2 mt-[5%]">
        검색 결과가 없습니다 👀
      </div>
    </div>
    <div v-else>
      <div class="grid grid-cols-4 gap-8 w-[90%] justify-center ml-[5%]">
        <div v-for="(video, index) in videoViewList" :key="index" class="">
          <div class="relative" @click="goToStudy(video.videoId)">
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
              class="mt-3 h-20 text-center flex items-center justify-center font-semibold rounded-lg shadow-md pl-1 bg-gray-100"
            >
              {{ video.videoName }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div
    class="search-frame h-[30%] overflow-y-auto scrollbar-hide"
    ref="scriptContainer"
  >
    <div class="text-xl mb-5">
      <span class="text-xl font-semibold highlight">'{{ searchInput }}'</span>에 대한 문장
      검색 결과
    </div>
    <div
      v-if="scriptViewList.length == 0"
      class="text-center flex items-center justify-center"
    >
      <div class="z-20 text-lg font-bold highlight pl-2 pr-2 mt-[2%]">
        검색 결과가 없습니다 👀
      </div>
    </div>
    <div v-else>
      <div class="grid grid-cols-4 gap-3 w-[90%] justify-center ml-[5%]">
        <div
          class="mb-[5%]"
          v-for="(script, index) in scriptViewList"
          :key="index"
          @click="goToStudy(script.videoId)"
        >
          <div
            class="rounded-md font-semibold text-center flex items-center justify-center bg-gray-100 shadow-md px-1 py-1 mb-3 h-20"
          >
            {{ script.videoName }}
          </div>
          <div
            class="rounded-md font-semibold text-center flex items-center justify-center bg-red-100 shadow-md px-1 py-1 mb-3 h-32"
          >
            <span v-html="highlightText(script.sentence, searchInput)"></span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { searchByTitle, searchByScript } from "@/api/search";
import { handleVideoClick } from "@/api/user.js";

const route = useRoute();
const router = useRouter();
const searchInput = ref("");
const videoAllList = ref([]); // api로 받아온 전체 검색 결과
const videoViewList = ref([]); // 스크롤 내릴때마다 +12개씩
const scriptAllList = ref([]);
const scriptViewList = ref([]);

let curTitlePage = 1;
let curScriptPage = 1;

const titleContainer = ref(null);
const scriptContainer = ref(null);

watch(
  () => route.query,
  (newValue, oldValue) => {
    //검색어 변경
    searchInput.value = newValue.keyword;
    searchTime();
  }
);

onMounted(() => {
  searchInput.value = route.query.keyword;
  searchTime();
  handleTitleScroll();
  titleContainer.value.addEventListener("scroll", handleTitleScroll);
  scriptContainer.value.addEventListener("scroll", handleScriptScroll);
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
      videoAllList.value = data.data;
      videoViewList.value = [...videoAllList.value.slice(0, 12)];
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
      scriptAllList.value = data.data;
      scriptViewList.value = [...scriptAllList.value.slice(0, 12)];
      console.log(data);
    },
    (error) => {
      console.log(error);
    }
  );
};

const handleTitleScroll = (e) => {
  console.log(
    titleContainer.value.scrollHeight +
      " " +
      titleContainer.value.scrollTop +
      " " +
      titleContainer.value.clientHeight
  );
  console.log(
    titleContainer.value.scrollHeight - (titleContainer.value.scrollTop + 50)
  );
  const scrollBottom =
    titleContainer.value.scrollHeight - (titleContainer.value.scrollTop + 50);
  if (scrollBottom <= titleContainer.value.clientHeight) {
    // 스크롤이 맨 아래로 도달했을 때 실행할 코드 작성
    console.log("title - 맨 아래로 스크롤했습니다!");
    videoViewList.value = [
      ...videoViewList.value,
      ...videoAllList.value.slice(curTitlePage * 12, curTitlePage * 12 + 12),
    ];
    curTitlePage++;
  }
};

const handleScriptScroll = (e) => {
  const scrollBottom =
    scriptContainer.value.scrollHeight - (scriptContainer.value.scrollTop + 50);
  if (scrollBottom <= scriptContainer.value.clientHeight) {
    // 스크롤이 맨 아래로 도달했을 때 실행할 코드 작성
    console.log("script - 맨 아래로 스크롤했습니다!");
    scriptViewList.value = [
      ...scriptViewList.value,
      ...scriptAllList.value.slice(curScriptPage * 12, curScriptPage * 12 + 12),
    ];
    curScriptPage++;
  }
};

const goToStudy = (videoId) => {
  handleVideoClick();
  router.push({ name: 'study', params: { videoId: videoId } });
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

.highlight {
  display: inline;
  box-shadow: inset 0 -10px 0 #cc000040;
}
</style>
