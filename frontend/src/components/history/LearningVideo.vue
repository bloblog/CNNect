<template>
  
  <div id="now-video">
    <div v-if="props.curVideo.videoId" class="flex">
      <div class="Carousel-btn">
          <svg class="sysmbol-btn" xmlns="http://www.w3.org/2000/svg" @click="changeVideoOrder('fore')" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
              <path d="M400-80 0-480l400-400 71 71-329 329 329 329-71 71Z"/>
          </svg>
      </div>
      <div
        @click="goToStudy"
        @mouseover="handleMouseOver"
        @mouseleave="handleMouseLeave"
        id="now-video-container"
        class="relative rounded-xl grid grid-cols-3 w-[70vw]"
        :class="{ 'opacity-80': hovered }"
      >
        <div id="container-layer" class="rounded-xl"></div>
        <div class="bg-black" id="video-img-container">
          <img
            id="now-video-img"
            :src="`https://img.youtube.com/vi/${curVideo.videoId}/maxresdefault.jpg`"
            alt="Now Video Image"
          />
        </div>
        <div
          class="p-10 flex flex-col justify-between col-span-3 sm:col-span-2 mb-3"
          id="now-video-info"
        >
          <div class="text-3xl font-bold font-[GmarketSansMedium]" id="video-name">
            [ Lv. {{ curVideo.videoLevel }} ] {{ curVideo.videoName }}
          </div>
          <div>
            <div class="flex">
              <div class="text-xl font-bold text-white z-10">문장 수</div>
              <div class="relative text-xl left-16" id="sentence-count">
                {{ curVideo.completedSentenceNum }} /
                {{ curVideo.totalSentenceNum }}
              </div>
            </div>
            <div class="flex">
              <div class="text-xl whitespace-nowrap font-bold text-white z-10">
                마지막 문장
              </div>
              <div
                id="last-sentence"
                class="relative text-xl left-5 whitespace-nowrap text-ellipsis overflow-hidden"
              >
                {{ curVideo.lastSentence }}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="Carousel-btn">
          <svg xmlns="http://www.w3.org/2000/svg" @click="changeVideoOrder('back')" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
              <path d="m321-80-71-71 329-329-329-329 71-71 400 400L321-80Z"/>
          </svg>
      </div>
    </div>
    <div v-else>
      <div id="no-content" class=" rounded-xl bg-gray-200 w-[70vw] h-[35vh] flex items-center justify-center overflow-hidden">
        <!-- <div class="animate-pulse flex items-center justify-center"> -->
        <div class="flex items-center justify-center">
            <!-- 배경 애니메이션을 위한 요소 -->
        </div>
        <div class="text-center">
          <div class="z-20 text-lg font-bold highlight pl-2 pr-2">
              학습 진행중인 뉴스가 없습니다
          </div>
          <div class="z-20">
              둘러보기 탭에서 CNN 뉴스를 구경해보세요 👀
          </div>
        </div>
    </div>


    </div>
    
  </div>   
</template>

<script setup>
import { ref, computed, defineProps, onMounted } from "vue";

const props = defineProps({
  curVideo: Object,
});

onMounted(() => {
    getLearningVideo(
    ({ data }) => {
        if (data.data) {
            learningVideoHistory.value = data.data;
            currentOrder.value = 0;
            totalVideos.value = learningVideoHistory.value.length;
            console.log(learningVideoHistory.value)
        }
    },
    (error) => {
      console.log(error);
    }
    );

    completedVideoHistory.value = getCompletedVideo();
    wordHistory.value = getWordHistory();

})

const hovered = ref(false);
  
const handleMouseOver = () => {
  hovered.value = true;
};

const handleMouseLeave = () => {
  hovered.value = false;

}

const currentOrder = ref(0);
const totalVideos = ref(0);

const changeVideoOrder = (direction) => {
    if (direction === 'fore') {
        console.log('앞')
        if (currentOrder.value > 0) {
            currentOrder.value -= 1;
            setCurVideo(currentOrder.value);
        } else {
            currentOrder.value = totalVideos.value-1;
            setCurVideo(currentOrder);
        }
    } else if (direction === 'back') {
        console.log('뒤')

        if (currentOrder.value < totalVideos.value-1) {
            currentOrder.value += 1;
            setCurVideo(currentOrder.value);
        } else {
            currentOrder.value = 0;
            setCurVideo(currentOrder);
        }
    }
}
const learningVideoHistory = ref([])

const setCurVideo = (idx) => {
    props.curVideo.value = learningVideoHistory.value[idx];
}

</script>

<style scoped>

.highlight{
  display: inline;
  box-shadow: inset 0 -10px 0 #cc000040; 
}

#now-video-img {
  width: 70rem;
  height: 100%;
  border-radius: 10px;
  object-fit: contain;
}

#container-layer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: linear-gradient(-90deg, #000000, #000000);
  pointer-events: none; /* 이벤트를 통과시킴 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
}

#now-video-info {
  height: 100%;
}

#sentence-count,
#last-sentence {
  color: white;
  z-index: 2;
}

#video-img-container {
  position: relative;
  border-radius: 10px;
}

#video-img-container::after {
  content: "";
  position: absolute;
  top: 0;
  left: 30%;
  right: 0;
  bottom: 0;
  background: linear-gradient(
    to right,
    rgba(255, 255, 255, 0),
    rgba(0, 0, 0, 0.708)
  );
  pointer-events: none;
  z-index: 2;
}

#video-name {
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.4em;
  height: 2.8em;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  color: white;
  z-index: 2;
}

</style>
