<template>
  <div>
    <div class="flex mt-0 justify-around" id="video-container">
      <div v-if="loading" class="text-center m-32">
        <div role="status">
          <svg aria-hidden="true" class="inline w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-theme-red" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
              <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
          </svg>
          <span class="sr-only">Loading...</span>
      </div>
    </div>
    <div v-else>
        <div v-if="videoList.length > 0" class="grid grid-cols-3 p-3" ref="imageContainer">
          <div v-for="(video, index) in videoList" :key="index" class="relative m-2">
            
            <div class="flex flex-col" id="content-area" @click="goToStudy(video.video_id)">
              <img :src="video.video_thumbnail" alt="video-image" class="rounded-md video-img-item img-container">
              <div class="overlay flex items-end">
                <div class="text-lg font-bold m-2" id="video-name">
                    {{video.video_name}}
                </div>
            </div>
              <span class="badge absolute top-[5%] left-[5%]">
                <div id="badge" class="bg-white border-theme-red border-4 rounded-md font-bold text-theme-red text-xs pl-1 pr-1">
                  Lv. {{ video.video_level }}
                </div>
              </span>
            </div>
          </div>
        </div>
        <div v-else>
          <div class="font-bold text-lg" id="no-content-area">해당 카테고리의 뉴스가 없습니다.</div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, watch, onMounted, nextTick, defineProps } from 'vue';
import { useRoute, useRouter } from "vue-router";
import { videoPaging } from "@/api/video";
import { handleVideoClick } from "@/api/user.js";

const route = useRoute();
const router = useRouter();

const goToStudy = (videoId) => {
  handleVideoClick();
  router.push({ name: 'study', params: { videoId: videoId } });
};

const props = defineProps({
  category: Number
});

const videoList = ref([]);
const loading = ref(false);

onMounted(() => {
  loading.value = true; // 로딩 시작
  videoPaging(
    props.category,
    0, 9,
    ({ data }) => {
      videoList.value = data.content;
      loading.value = false; // 로딩 완료
    },
    (error) => {
      console.log(error);
      loading.value = false;
    }
  );
});

watch(() => props.category, (newValue) => {
  updateVideoList(newValue);
});

// 카테고리별 영상 렌더링
const updateVideoList = (category) => {
  loading.value = true; // 로딩 시작

  videoPaging(
    category,
    0, 9,
    ({ data }) => {
      videoList.value = data.content;
      loading.value = false; // 로딩 완료
    },
    (error) => {
      console.log(error);
      loading.value = false;
    }
  );
};

document.addEventListener('scroll', () => {
  const scrollPosition = window.innerHeight + window.scrollY;
  const pageHeight = document.documentElement.scrollHeight - 1;

  if (scrollPosition >= pageHeight) {
    fetchMoreVideos();
  }
});

// 추가적인 비디오 가져오기
const fetchMoreVideos = async () => {
const startIndex = videoList.value.length + 1;

videoPaging(
  props.category,
  startIndex, 9,
  ({ data }) => {
    videoList.value = [...videoList.value, ...data.content];
  },
  (error) => {
    console.log(error);
  }
);
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

#video-container {
  border: 1px solid lightgray;
  border-radius: 10px;
  width: 75vw;
  
}
#content-area {
  transition: transform 0.3s ease;
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

#content-area:hover {
  cursor: pointer;
  transform: scale(1.02);
  color: white;
}

.img-container {
  overflow: hidden;
  object-fit: cover;
}

#video-name {
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.4em;
  height: 4.3em;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  transition: text-decoration 0.3s ease;
}


#no-content-area {
  height: 60vh;
  display: flex;
  align-items: center;

}

</style>