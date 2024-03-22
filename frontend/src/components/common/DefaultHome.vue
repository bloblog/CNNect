<template>
    <div class="flex flex-col items-center p-10">
      <div id="now-video" class="mb-10">
        <h1 class="text-4xl font-bold p-5">학습 진행중인 뉴스</h1>
        <div 
          @click="goToStudy"
          @mouseover="handleMouseOver"
          @mouseleave="handleMouseLeave"
          id="now-video-container" 
          class="relative rounded-xl grid grid-cols-3"
          :class="{ 'opacity-80': hovered }"
          >
  
          <div id="container-layer" class="rounded-xl"></div>
          <div class="">
            <img id="now-video-img" src="" alt="Now Video Image">
          </div>
          <div class="p-10 flex flex-col justify-around col-span-3 sm:col-span-2">
            <div class="text-4xl font-bold" id="video-name"></div>
            <div>
              <div class="flex">
                <div class="text-2xl font-bold text-white z-10">문장 수</div>
                <div class="relative text-2xl left-16" id="sentence-count"></div>
              </div>
              <div class="flex">
                <div class="text-2xl whitespace-nowrap font-bold text-white z-10">마지막 문장</div>
                <div id="last-sentence" class="relative text-2xl left-5 whitespace-nowrap text-ellipsis overflow-hidden"></div>
              </div>
  
            </div>
          </div>
        </div>
      </div>
      <div id="recomm-video">
        <h1 class="text-4xl font-bold p-5">추천 뉴스</h1>
        <div id="recomm-video-container">
          <RecommVideoList/>
        </div>
      </div>
  
      
    </div>
    
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import { initFlowbite, Modal } from "flowbite";
  import { useRoute, useRouter } from "vue-router";
  import { loginUser } from "@/api/user";
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
    console.log(hovered.value);
  };
  
  const handleMouseLeave = () => {
    hovered.value = false;
    console.log(hovered.value);
  
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
    category_name: "Politics",
    video_date: "2024-03-13",
    video_name: "Don Lemon speaks out after Elon Musk cancelled his show on X",
    video_thumbnail : "https://i.ytimg.com/vi/pgVZnVTKqMw/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLCt-mOsWO1EQ4iRQiBnoCrAg1G3Ww"
  }
  
  // 페이지가 로드된 후에 이미지 태그의 src 속성에 변수 값을 할당
  window.onload = function() {
    document.getElementById("now-video-img").src = now_video.video_thumbnail;
    document.getElementById("video-name").innerText = now_video.video_name;
    document.getElementById("sentence-count").innerText = `1 / ${now_video.sentenceList.length}`;
    document.getElementById("last-sentence").innerText = now_video.sentenceList[1].text;
  };
  
  
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
  
  const login = () => {
    loginUser(
      loginData.value,
      ({ data }) => {
        console.log(data);
        console.log(data.data.level);
        localStorage.setItem("refreshToken", data.data.jwtToken.refreshToken);
        localStorage.setItem("accessToken", data.data.jwtToken.accessToken);
        setLogin();
        setLevel(data.data.level);
        setNickname(data.data.nickName);
  
        location.href = "/";
      },
      (error) => {
        alert("아이디 및 비밀번호를 확인해주세요!")
        loginData.value.userPassword = "";
        console.log(error);
      }
    );
  };
  
  
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
    border-radius: 10px;
    object-fit: contain;
  }
  
  #container-layer {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-image: linear-gradient(90deg, #00000037 10%, #000000);
      pointer-events: none; /* 이벤트를 통과시킴 */
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5); /* 그림자 추가 */
      
  }
  
  #video-name, #sentence-count, #last-sentence {
    color: white;
    z-index: 2;
  }
  
  </style>
  