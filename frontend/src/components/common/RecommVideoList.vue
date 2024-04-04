<template>
  <div class="flex justify-between items-center w-[80vw]">
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
    <div class="flex" v-if="videoList.length > 0">
      <div class="grid grid-cols-3 gap-3" ref="imageContainer">
        <div v-for="(video, index) in videoList.slice(startIndex, endIndex)" :key="index" class="relative">
          <div class="flex flex-col" @click="goToStudy(video.videoId)">
            <img
              :src="video.videoThumbnail"
              alt="video-image"
              class="object-fit rounded-md video-img-item img-container"
            />
            <span class="badge absolute top-3 left-3">
              <div
                id="badge"
                class="bg-white border-theme-red border-4 rounded-md font-bold text-theme-red text-md pl-1 pr-1"
              >
                Lv. {{ video.videoLevel }}
              </div>
            </span>
            <div class="overlay flex items-end">
                <div class="text-white text-lg font-bold m-2" id="videoName">
                    {{video.videoName}}
                </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <div v-else class="grid grid-cols-3 flex space-x-2">
      <div v-for="n in 3" :key="n" class="animate-pulse bg-gray-200 rounded-md video-img-item img-container">
        <div class="rounded w-72 h-48"></div>
      </div>
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
  </div>
  
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import { useRoute, useRouter } from "vue-router";
import { fetchRecommendations } from "@/api/recommendations.js";
import { handleVideoClick } from "@/api/user.js";

const router = useRouter();

const startIndex = ref(0);
const endIndex = ref(3);

const currentIndex = ref(0);
const imageContainer = ref(null);

const videoList = ref([]);

const isLoading = ref(true);

onMounted(async () => {
  try {
    await fetchRecommendations().then((res)=>{

videoList.value = res;
console.log("비디오리스트", videoList.value);
isLoading.value = false;
});
await nextTick();
  } catch (error) {
    console.error(error);
    isLoading.value = false; 
  }
});

// 다음 페이지로 이동
const nextPage = () => {
  if(currentIndex.value === videoList.value.length - 3){
    currentIndex.value = 0;
  }else{
    currentIndex.value = Math.min(currentIndex.value + 3, videoList.value.length - 3);
  }
  updateImagePosition();
};
const print = () =>{
  console.log("비디오리스트", videoList.value);
};
// 이전 페이지로 이동
const prevPage = () => {
  if(currentIndex.value === 0){
    currentIndex.value = videoList.value.length - 3;
  }else{
    currentIndex.value = Math.max(currentIndex.value - 3, 0);
  }
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
const goToStudy = (videoId) => {
  handleVideoClick();
  router.push({ name: 'study', params: { videoId: videoId } });
};
</script>

<style scoped>


.img-container {
  overflow: hidden;
  height: 30vh;
  object-fit: cover;
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); 
  opacity: 0; /* 초기에는 숨김 */
  transition: opacity 0.3s ease; /* 변화 시 부드럽게 전환 */
  border-radius: 10px;
  z-index: 1;
}

.relative:hover .overlay {
  opacity: 1; /* 호버 시 레이어를 표시 */
}

#content-area:hover {
  cursor: pointer;
  transform: scale(1.02);
  color: white;
}
#videoName {
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.4em;
  height: 4.3em;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  transition: text-decoration 0.3s ease;
  font: white;
  z-index: 2;
}

</style>
