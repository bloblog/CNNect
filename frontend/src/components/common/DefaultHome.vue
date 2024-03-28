<template>
    <div class="flex flex-col items-center p-5">
      <div id="now-video" class="mb-10">
        <h1 class="text-xl font-[GmarketSansMedium] font-bold p-3 ml-10">학습 진행중인 뉴스</h1>
        <div class="flex justify-center">
          <div 
            @click="goToStudy"
            @mouseover="handleMouseOver"
            @mouseleave="handleMouseLeave"
            id="now-video-container" 
            class="relative rounded-xl grid grid-cols-5 w-[70vw]"
            :class="{ 'opacity-80': hovered }"
            >
            <div id="container-layer" class="rounded-xl"></div>
            <div class="bg-black col-span-2" id="video-img-container">
              <img id="now-video-img" :src=now_video.video_thumbnail alt="Now Video Image">
            </div>
            <div class="p-8 flex flex-col justify-between sm:col-span-3 mb-3" id="now-video-info">
              <div class="text-2xl font-bold font-[GmarketSansMedium]" id="video-name">[ Lv.{{ now_video.video_level }} ] {{ now_video.video_name }}</div>
              <div>
                <div class="flex">
                  <div class="text-lg font-bold text-white z-10">문장 수</div>
                  <div class="relative text-lg left-14" id="sentence-count">1 / {{ now_video.sentenceList.length }}</div>
                </div>
                <div class="flex">
                  <div class="text-lg whitespace-nowrap font-bold text-white z-10">마지막 문장</div>
                  <div id="last-sentence" class="relative text-lg left-5 whitespace-nowrap text-ellipsis overflow-hidden">{{ now_video.sentenceList[1].text }}</div>
                </div>
    
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="recomm-video">
        <h1 class="text-xl font-[GmarketSansMedium] font-bold p-3 ml-10">추천 뉴스</h1>
        <div id="recomm-video-container">
          <RecommVideoList/>
        </div>
      </div>
      <!-- 공백용 -->
      <div class="h-10"></div>
  
      
    </div>
    
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import { initFlowbite, Modal } from "flowbite";
  import { useRoute, useRouter } from "vue-router";
  import { userStore } from "@/stores/userStore";
  import { storeToRefs } from "pinia";


  
  import RecommVideoList from '@/components/common/RecommVideoList.vue'
  
  const route = useRoute();
  const router = useRouter();
  const uStore = userStore();
  
  const loginData = ref({
    userEmail: "",
    userPassword: "",
  });
  
  const { setLogin, setLogout, setNickname, setLevel } = uStore;
  const { isLogin } = storeToRefs(uStore);
  
  onMounted(() => {
    initFlowbite();
    console.log(modal.isVisible());

  });
  
  const hovered = ref(false);
  
  const handleMouseOver = () => {
    hovered.value = true;
  };
  
  const handleMouseLeave = () => {
    hovered.value = false;
  
  }
  
  const now_video = {
    sentenceList: [
      {
        "start": 0.033,
        "text": "Elon musk tells don lemon, quote, contract is canceled, end quote."
      },
      {
        "start": 4.904,
        "text": "Now, in a moment, i'm going to speak exclusively with don lemon and he has clips of the nearly hour and a half interview with musk, clips like this one that we received from don's production team."
      },
    ],
    category_id: "0",
    video_level: 4,
    video_date: "2024-03-13",
    video_name: "Don Lemon speaks out after Elon Musk cancelled his show on X",
    video_thumbnail : "https://i.ytimg.com/vi/pgVZnVTKqMw/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLCt-mOsWO1EQ4iRQiBnoCrAg1G3Ww"
  }

  
  
  // set the modal menu element
  const $targetEl = document.getElementById("authentication-modal");
  
  // options with default values
  const options = {
    placement: "bottom-right",
    backdrop: "dynamic",
    backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
    closable: true,
    onHide: () => {
      console.log("modal is hidden");
    },
    onShow: () => {
      console.log("modal is shown");
    },
    onToggle: () => {
      console.log("modal has been toggled");
    },
  };
  
  // instance options object
  const instanceOptions = {
    id: "authentication-modal",
    override: false,
  };
  
  const modal = new Modal($targetEl, options, instanceOptions);

  const goToStudy = () => {
    router.push("/study");
  };


  
  </script>
  
  <style scoped>
  #now-video, #recomm-video {
    width: 80vw;
  }

  
  #now-video-img{
    width:70rem;
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

  #video-name, #sentence-count, #last-sentence {
    color: white;
    z-index: 2;
  }

 #video-img-container{
    position: relative;
    border-radius: 10px;

  }

  #video-img-container::after {
    content: '';
    position: absolute;
    top: 0;
    left: 30%;
    right: 0;
    bottom: 0;
    background: linear-gradient(to right, rgba(255, 255, 255, 0), rgba(0, 0, 0, 0.708));
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
  }
  
  </style>
  