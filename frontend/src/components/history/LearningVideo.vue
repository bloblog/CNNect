<template>
  <div class="Carousel-btn">
     <svg class="sysmbol-btn" xmlns="http://www.w3.org/2000/svg" @click="changeVideoOrder('backward')" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
        <path d="M400-80 0-480l400-400 71 71-329 329 329 329-71 71Z"/>
     </svg>
  </div>  
  <div id="now-video" >
    <div
      @click="goToStudy"
      @mouseover="handleMouseOver"
      @mouseleave="handleMouseLeave"
      id="now-video-container"
      class="relative rounded-xl grid grid-cols-3"
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
        <div class="text-3xl font-bold" id="video-name">
          [Lv. {{ curVideo.videoLevel }}] {{ curVideo.videoName }}
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
  </div>
  <div class="Carousel-btn">
    <svg xmlns="http://www.w3.org/2000/svg" @click="changeVideoOrder('foreward')" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
        <path d="m321-80-71-71 329-329-329-329 71-71 400 400L321-80Z"/>
    </svg>
  </div>    
</template>

<script setup>
import { ref, computed, defineProps } from "vue";

const props = defineProps({
  curVideo: Object,
});



</script>

<style scoped>
#now-video,
#recomm-video {
  width: 80vw;
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
