<template>
  <div>
    <div v-if="paginatedVideos && paginatedVideos.length > 0">
        <div class="px-3 py-5 z-10 flex justify-center items-center" ref="videoBox">
        <div class="w-5/6 grid grid-cols-3 gap-4">
            <div :key="index" class="relative" v-for="(video, index) in paginatedVideos" @click="goToStudy(video.videoId)">
                <div class="video-img">
                    <img :src="`https://img.youtube.com/vi/${video.videoId}/maxresdefault.jpg`" class="w-full h-auto" />
                    <span class="badge absolute top-1 left-1">
                        <div id="badge" class="bg-white border-theme-red border-4 rounded-md font-bold text-theme-red text-lg p-0.5 pl-1 pr-1">
                            Lv. {{video.videoLevel}}
                        </div>
                    </span>
                </div>
                <div class="title text-1xl font-bold" id="video-title">{{ video.videoName }}</div>
            </div>
        </div>
        </div>
        <div class="page">
            <div>
                <svg class="sysmbol-btn" xmlns="http://www.w3.org/2000/svg" @click="previousPage" :disabled="currentPage === 0" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
                    <path d="M400-80 0-480l400-400 71 71-329 329 329 329-71 71Z"/>
                </svg>
            </div>
            <div class="page-container">
                <span v-for="page in visiblePages" :key="page" @click="goToPage(page)" :class="{ active: currentPage === page - 1 }" class="page-item">{{ page }}</span>
            </div>
            <div>
                <svg xmlns="http://www.w3.org/2000/svg" @click="nextPage" :disabled="currentPage === totalPages-1" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
                    <path d="m321-80-71-71 329-329-329-329 71-71 400 400L321-80Z"/>
                </svg>
            </div>  
        </div>
    </div>
    <div v-else class="grid-cols-3 flex space-x-2">
        <div class="rounded-xl w-[70vw] border-2 border-gray-200 text-center p-14 font-[GmarketSansMedium]">학습 완료한 뉴스가 없습니다.</div>
        
    </div>
    
  </div>
</template>

<script setup>
import { defineProps, ref, computed } from 'vue';
import { useRouter } from "vue-router";

const props = defineProps({
  completedVideoList: Object
});
    const router = useRouter();

    const currentPage = ref(0);
    const videodPerPage = 9;

    const totalPages = computed(() => Math.ceil(props.completedVideoList.length / videodPerPage));
    const paginatedVideos = computed(() => {
        const startIndex = currentPage.value * videodPerPage;
        return props.completedVideoList.slice(startIndex, startIndex + videodPerPage);
    });


    const visiblePages = computed(() => {
        const pages = [];
        const currentPageValue = currentPage.value + 1;

        const startPage = Math.max(1, currentPageValue - 2); 
        const endPage = Math.min(totalPages.value, currentPageValue + 2); 
       

        if(currentPageValue === totalPages.value ){
            if(totalPages.value >= 5){
                pages.push(totalPages.value-4);
                pages.push(totalPages.value-3);
            }else if(totalPages.value===4){
                pages.push(totalPages.value-3);
            }
        }else if(currentPageValue === totalPages.value-1){
            if(totalPages.value >= 5){
                pages.push(totalPages.value-4);
            }
        }

        for (let i = startPage; i <= endPage; i++) {
            pages.push(i);
        }
        
        if(currentPageValue === 1 ){
            if(totalPages.value >= 5){
                pages.push(currentPageValue+3);
                pages.push(currentPageValue+4);
            }else if(totalPages.value===4){
                pages.push(currentPageValue+3);
            }
        }else if(currentPageValue === 2 ){
            if(totalPages.value >= 5){
                pages.push(currentPageValue+3);
            }
        }
        return pages;
    });


    function nextPage() {
        if (currentPage.value < totalPages.value - 1) {
            currentPage.value++;
        }
    }

    function previousPage() {
        if (currentPage.value > 0) {
            currentPage.value--;
        }
    }

    function goToPage(page) {
        currentPage.value = page - 1;
    }

    const goToStudy = (videoId) => {
        router.push({ name: 'study', params: { videoId: videoId } });
    };

</script>

<style scoped>

.video-img,
.title {
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
.active{
    color: #CC0000;
}
.page{
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    /* bottom: 8%; */
    left: 50%;
    width: 100%; /* 페이지 네비게이션의 너비를 조정할 수 있습니다. */
    background-color: #fff; /* 필요에 따라 배경색을 지정하세요 */
    padding-top: 2%;

}
.page-item {
  margin: 0 10px; 
  cursor: pointer;
}
</style>