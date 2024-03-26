<template>
    <div>
      <h1 class="text-2xl font-bold p-3 ">학습 완료한 뉴스</h1>
      <div class="px-3 py-5 z-10 flex justify-center items-center" ref="videoBox">
        <div class="w-5/6 grid grid-cols-3 gap-4">
          <div :key="index" class="relative" v-for="(video, index) in completionVideoList">
            <div
              class="relative"
              @mouseover="showVideoPopup(index)"
              @mouseleave="hideVideoPopup(index)"
            >
              <img
                class="w-full h-auto"
                :src="`https://img.youtube.com/vi/${video.videoUrl}/maxresdefault.jpg`"
              />
              <span class="badge absolute top-1 left-1">
                  <div id="badge" class="bg-white border-theme-red border-4 rounded-md font-bold text-theme-red text-lg p-0.5 pl-1 pr-1">
                    Lv. {{video.videoLevel}}
                  </div>
              </span>
              <div
                v-if="isVideoPopup[index]"
                class="absolute top-0 left-0 w-full h-full flex justify-center items-center bg-black bg-opacity-50"
              >
                <iframe
                  :src="`https://www.youtube.com/embed/${video.videoUrl}?autoplay=1`"
                  frameborder="0"
                  allow="autoplay; encrypted-media"
                  allowfullscreen
                  class="absolute top-0 left-0 w-full h-full"
                ></iframe>
              </div>
            </div>
            <!-- @click="gotostudy(video.videoUrl)" -->
            <div class="title text-1xl font-bold " id="video-title" >
                {{video.videoTitle}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>

<script setup>
    import { ref , onMounted , defineProps } from 'vue';

    const props = defineProps({
        completionVideoList: Object
    })

    onMounted(() => {
        isVideoPopup.value = Array(props.completionVideoList.length).fill(false);
    });
    
    const isVideoPopup = ref([]);

    
    const showVideoPopup = (index) => {
        isVideoPopup.value[index] = true;
    };

    const hideVideoPopup = (index) => {
        isVideoPopup.value[index] = false;
    };



</script>

<style scoped>
.title{
  cursor: pointer;
}
#video-title {
    overflow: hidden;
    text-overflow: ellipsis;
    line-height: 1.4em;
    height: 2.8em;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;

  }

</style>
