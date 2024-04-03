<template>
  <div>
    <div class="text-xl m-5 ml-10 font-[GmarketSansMedium]">
      <span class="font-semibold highlight">'{{ searchInput }}'</span>에 대한 제목
      검색 결과
    </div>

    <div
      class="search-frame h-[75vh] overflow-y-auto scrollbar-hide"
      ref="titleContainer"
    >
      <div
        v-if="videoViewList.length == 0"
        class="text-center flex items-center justify-center"
      >
        <div class="z-20 text-lg font-bold highlight pl-2 pr-2 mt-[10%]">
          검색 결과가 없습니다 👀
        </div>
      </div>
      <div v-else>
        <div class="fixed bottom-4 z-10 left-1/2 transform -translate-x-1/2">
          <button @click="scrollTo" class="bg-theme-red hover:bg-theme-redbrown text-white font-bold py-4 px-6 rounded-full shadow-lg">
            {{ buttonLabel }}
          </button>
        </div>
        <div class="grid grid-cols-4 gap-1 w-[90%] justify-center ml-[5%]">
          <div v-for="(video, index) in videoViewList" :key="index" class="relative m-2">
              <div class="flex flex-col" id="content-area" @click="goToStudy(video.videoId)">
                <img :src="`https://img.youtube.com/vi/${video.videoId}/mqdefault.jpg`" alt="video-image" class="max-h-50 rounded-md video-img-item img-container">
                <div class="overlay flex items-end">
                  <div class="text-lg font-bold m-2 text-white" id="video-name">
                      {{video.videoName}}
                  </div>
                </div>
                <span class="badge absolute top-[5%] left-[5%]">
                  <div id="badge" class="bg-white border-theme-red border-4 rounded-md font-bold text-theme-red text-xs pl-1 pr-1">
                    Lv. {{ video.videoLevel }}
                  </div>
                </span>
              </div>
            </div>
        </div>
      </div>
    </div>
    <div id="sentence-result" class="font-[GmarketSansMedium] text-xl mb-5 ml-10">
      <span class="font-semibold highlight">'{{ searchInput }}'</span>에 대한 문장
      검색 결과
    </div>
    <div
      class="search-frame overflow-y-auto scrollbar-hide"
      ref="scriptContainer"
    >
      <div
        v-if="scriptViewList.length == 0"
        class="text-center flex items-center justify-center"
      >
        <div class="z-20 text-lg font-bold highlight pl-2 pr-2 mt-[2%]">
          검색 결과가 없습니다 👀
        </div>
      </div>
      <div v-else>
        <div class="grid grid-cols-4 gap-7 w-[90%] ml-[5%]">
          <div
            v-for="(script, index) in scriptViewList"
            :key="index"
            @click="goToStudy(script.videoId)"
          >
            <div
            class="rounded-md font-semibold flex flex-col justify-center shadow-md px-5 py-3 hover:bg-red-200 h-32"
            >
              <div class="font-bold underline hover:cursor-pointer">
                <span class="inline-block bg-theme-red text-white text-xs px-2 py-1 rounded-full mr-1">
                  Lv. {{ script.videoLevel }}
                </span>
                {{ script.videoName }}
              </div>
              <span class="text-sm" v-html="highlightText(script.sentence, searchInput)"></span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="h-5"></div>
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

const categoryMapping = ref(['정치', '비즈니스', '스포츠', '스타일','엔터테인먼트', '건강']);


let curTitlePage = 1;
let curScriptPage = 1;

const titleContainer = ref(null);
const scriptContainer = ref(null);

const reachedResult = ref(false);
const buttonLabel = ref('문장 검색 결과');

const scrollTo = () => {
  if (!reachedResult.value) {
    window.scrollBy({top: document.getElementById('sentence-result').getBoundingClientRect().top, behavior: 'smooth'});
    reachedResult.value = true;
  } else {
    window.scrollTo({top: 0, behavior: 'smooth'});
    reachedResult.value = false;
  }
  updateButtonLabel();
}

const updateButtonLabel = () => {
  // const button = document.querySelector('#move-btn');
  if (reachedResult.value) {
    buttonLabel.value = '제목 검색 결과';
  } else {
    buttonLabel.value = '문장 검색 결과';
  }
}


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

<style scoped>
@media screen and (min-width: 400px) {
  #badge {
    font-size: 12px; /* 적절한 크기로 조정 */
    padding: 1px;
  }
}

@media screen and (min-width: 768px) {
  #badge {
    font-size: 15px;
    padding: 3px;
  }
}

@media screen and (min-width: 1024px) {
  #badge {
    font-size: 18px;
    padding: 5px;
  }
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* 투명한 검은색 배경 */
  opacity: 0; /* 초기에는 숨김 */
  transition: opacity 0.3s ease; /* 변화 시 부드럽게 전환 */
  border-radius: 10px;
}

.relative:hover .overlay {
  opacity: 1; /* 호버 시 레이어를 표시 */
}

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
