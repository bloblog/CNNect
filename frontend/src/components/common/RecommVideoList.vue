<template>
    <div>
      <div class="flex justify-between items-center">
        <button @click="prevPage" id="prevButton" class="text-gray-500 focus:outline-none">
          <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-chevron-left" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#CC0000" fill="none" stroke-linecap="round" stroke-linejoin="round">
            <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
            <path d="M15 6l-6 6l6 6" />
          </svg>
        </button>
        <div class="flex">
          <div class="grid-cols-3 flex space-x-2 " ref="imageContainer">
            <div v-for="(video, index) in videoList.slice(startIndex, endIndex)" :key="index" class="relative">
              <div class="flex flex-col" @click="goToStudy">
                <img :src="video.video_thumbnail" alt="video-image" class="object-fit rounded-md video-img-item img-container">
                <span class="badge absolute top-1 left-1">
                  <div id="badge" class="bg-white border-theme-red border-4 rounded-md font-bold text-theme-red text-lg p-0.5 pl-1 pr-1">
                    Lv. 3
                  </div>
                </span>
                <div class="text-xl font-bold mt-2" id="video-name">
                  {{video.video_name}}
                </div>


              </div>

            </div>
          </div>

        </div>

        <button @click="nextPage" id="nextButton" class="text-gray-500 focus:outline-none">
          <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-chevron-right" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#CC0000" fill="none" stroke-linecap="round" stroke-linejoin="round">
            <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
            <path d="M9 6l6 6l-6 6" />
          </svg>
        </button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, nextTick } from 'vue';
  import { useRoute, useRouter } from "vue-router";
  import axios from 'axios';

  const route = useRoute();
  const router = useRouter();

  const startIndex = ref(0);
  const endIndex = ref(3);

  const currentIndex = ref(0);
  const imageContainer = ref(null); // ref를 사용하여 DOM 요소에 접근할 변수 선언
  
  const videoList = ref([]);

  const fetchRecommendations = async () => {
  try {
    const response = await axios.get('백 서버URL/recommendations');
    videoList.value = response.data.recommended_news || []; 
  } catch (error) {
    console.error("서버 응답 오류:", error.response ? error.response.status : 'Unknown', error.response ? error.response.data : 'Unknown');
    console.error("서버로부터 응답이 없습니다.", error.request);
    console.error("요청 설정 오류:", error.message);
    console.error("에러 설정 정보:", error.config);
  }
};

const prevPage = () => {
  currentIndex.value = Math.max(currentIndex.value - 1, 0);
  updateImagePosition();
};

const nextPage = () => {
  const maxIndex = videoList.value.length - 3;
  currentIndex.value = Math.min(currentIndex.value + 1, maxIndex);
  updateImagePosition();
};

const updateImagePosition = () => {
  nextTick(() => {
    startIndex.value = currentIndex.value;
    endIndex.value = currentIndex.value + 3;
    console.log("startIndex:", startIndex.value, "endIndex:", endIndex.value);
  });
};
  
  onMounted(() => {
    fetchRecommendations();
    updateImagePosition();
  });

  const goToStudy = () => {
    router.push("/study");
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
  