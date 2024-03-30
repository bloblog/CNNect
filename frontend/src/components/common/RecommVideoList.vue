<template>
  <div>
    <div class="flex justify-between items-center">
      <button
        @click="prevPage"
        id="prevButton"
        class="text-gray-500 focus:outline-none"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="icon icon-tabler icon-tabler-chevron-left"
          width="44"
          height="44"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="#CC0000"
          fill="none"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M15 6l-6 6l6 6" />
        </svg>
      </button>

      <div class="flex" v-if="!isLoading">
        <div class="grid-cols-3 flex space-x-2" ref="imageContainer">
          <div v-if="videoList && videoList.length !== 0" class="relative">
            <div v-for="(video, index) in videoList.value.slice(startIndex, endIndex)" :key="index">
              <div class="flex flex-col" @click="goToStudy(video)">
                <img
                  :src="video.video_thumbnail"
                  alt="video-image"
                  class="object-fit rounded-md video-img-item img-container"
                />
                <span class="badge absolute top-3 left-3">
                  <div
                    id="badge"
                    class="bg-white border-theme-red border-4 rounded-md font-bold text-theme-red text-md pl-1 pr-1"
                  >
                    Lv. {{ video.video_level }}
                  </div>
                </span>
                <div class="text-md font-bold mt-2" id="video-name">
                  {{ video.video_name }}
                </div>
              </div>
            </div>
          </div>
          <div v-else class="grid-cols-3 flex space-x-2">
            <div v-for="n in 3" :key="n" class="animate-pulse rounded-md video-img-item img-container">
              <div class="rounded w-[20vw] h-52 bg-gray-200"></div>
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        로딩
      </div>

      <button
        @click="nextPage"
        id="nextButton"
        class="text-gray-500 focus:outline-none"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="icon icon-tabler icon-tabler-chevron-right"
          width="44"
          height="44"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="#CC0000"
          fill="none"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M9 6l6 6l-6 6" />
        </svg>
      </button>
      <button @click="print">프린트</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import { useRoute, useRouter } from "vue-router";
import { sendTokenToSaveRM } from "@/api/user.js";
import { fetchRecommendations } from "@/api/recommendations.js";

const route = useRoute();
const router = useRouter();

const startIndex = ref(0);
const endIndex = ref(3);

const currentIndex = ref(0);
const imageContainer = ref(null); // ref를 사용하여 DOM 요소에 접근할 변수 선언

// 비디오 리스트 ref
const videoList = ref([]);

// fetchRecommendations 함수 호출하여 데이터 가져오기
const isLoading = ref(true); // 데이터 로딩 상태 관리를 위한 변수

onMounted(async () => {
  isLoading.value = true; // 데이터 로딩 시작
  try {
    const responseData = await fetchRecommendations();
    videoList.value = responseData || [];
    console.log(videoList.value);
    isLoading.value = false; // 데이터 로딩 완료
  } catch (error) {
    console.error(error);
    isLoading.value = false; // 에러 발생 시에도 로딩 완료 처리
  }
});



// 다음 페이지로 이동
const nextPage = () => {
  const maxIndex = videoList.value.length - 3;
  currentIndex.value = Math.min(currentIndex.value + 1, maxIndex);
  updateImagePosition();
};
const print = () =>{
  console.log("비디오리스트", videoList.value);
};
// 이전 페이지로 이동
const prevPage = () => {
  currentIndex.value = Math.max(currentIndex.value - 1, 0);
  updateImagePosition();
};

// 이미지 위치 업데이트
const updateImagePosition = () => {
  nextTick(() => {
    startIndex.value = currentIndex.value;
    endIndex.value = currentIndex.value + 3;
  });
};

// 학습 페이지로 이동
const goToStudy = (video) => {
  // 학습 페이지로 이동하는 코드 추가
};

</script>

<style scoped>
#video-name {
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.4em;
  height: 2.8em;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  width: 20vw;
}

.img-container {
  overflow: hidden;
  height: 25vh;
  object-fit: cover;
}
</style>
