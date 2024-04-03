<template>
  <div class="">
    <div class=" overflow-y-scroll flex flex-col items-center justify-center">
      <div class="font-[GmarketSansMedium] text-xl font-bold mt-7 ">
        CNNect에서 학습하고 싶은 영상을 골라주세요!
      </div>
      <!-- 영상 목록 출력(3x3 한번에 9개씩 보여주기 -> 스크롤 내리면 다음 영상 목록 로드하기)-->
      <div class="px-3 py-5 flex justify-center items-center" ref="videoBox">
        <div class="w-5/6 grid grid-cols-3 gap-3 h-[90vh]">
          <div
            :key="index"
            v-for="(video, index) in videoViewList"
          >
            <!--hear icon-->
            <div class="flex flex-col items-center justify-center pt-2">
              <div
                class="flex flex-col items-center px-4 mr-3 font-semibold text-sm h-fit text-center rounded-lg shadow-md"
              >
                <div
                class="relative"
                @mouseover="showVideoPopup(index)"
                @mouseleave="hideVideoPopup(index)"
              >
                <!-- :class="changeThumbnail(index)" -->
                <img
                  class="w-full h-auto"
                  @click="addLike(index)"
                  :src="`https://img.youtube.com/vi/${video.video_id}/mqdefault.jpg`"
                />
                <div
                  v-if="isVideoPopup[index]"
                  class="absolute top-0 left-0 w-full h-full flex justify-center items-center bg-black bg-opacity-50"
                >
                  <iframe
                    :src="`https://www.youtube.com/embed/${video.video_id}?autoplay=1`"
                    frameborder="0"
                    allow="autoplay; encrypted-media"
                    allowfullscreen
                    class="absolute top-0 left-0 w-full h-full"
                  ></iframe>
                </div>
              </div>
              <div class="flex justify-center m-2">
                <div class="text-start mr-1 font-[] font-semibold" id="video-name">
                  {{ video.video_name }}
                </div>
                <div>
                  <div class="rounded-full border px-1 py-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="30"
                      height="30"
                      :fill="videoLike[index] == true ? 'red' : 'white'"
                      :transform="videoLike[index] == true ? 'scale(1.0)' : 'scale(0.9)'"
                      @click="addLike(index)"
                      viewBox="0 0 24 24"
                    >
                      <path
                        :stroke="videoLike[index] == true ? 'none' : 'lightgray'"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="1"
                        d="M12.01 6.001C6.5 1 1 8 5.782 13.001L12.011 20l6.23-7C23 8 17.5 1 12.01 6.002Z"
                      />
                    </svg>
                  </div>
  
                </div>
  
              </div>
  
              </div>
              
            </div>
          </div>
          <div class="h-16"></div>
        </div>
      </div>
      
    </div>
    <div class="flex items-center w-full justify-center">
      <button
        @click="nextStep(2)"
        class="z-10 fixed bottom-5 items-center text-white bg-theme-red hover:bg-theme-redbrown focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center"
      >
        다음
      </button>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { userStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { insertRegistHistory } from "@/api/history";
import { handleVideoClick } from "@/api/user.js";
import { registerVideo } from "@/api/video";
import Swal from "sweetalert2";

const uStore = userStore();

const { userId } = storeToRefs(uStore);

const videoAllList = ref([]); // 전체 video 저장
const videoViewList = ref([]); // 현재 보이는 video 저장
const addList = ref([]); // 유저가 선택한 영상 -> 학습 기록에 저장
const videoLike = ref([]); // 전체 영상 목록과 동일한 배열 크기 -> true/false로 선택 여부관리
const isVideoPopup = ref([]);
// 전체 영상 목록과 동일한 배열 크기 -> 팝업창으로 영상 미리보기 띄울지 여부 -> mousevoer시 true, mouseleave시 false
const videoBox = ref(null); // 스크롤 인식을 위한 컨테이너
let flag = false;

const msg = Swal.mixin({
  position: "center",
  showConfirmButton: true,
  confirmButtonText: "확인",
  backdrop: true,
}); // alert창 기본틀

let page = 1;

onMounted(() => {
  registerVideo(
    ({ data }) => {
      console.log(data.data);
      videoAllList.value = data.data.slice(0, 27);
      for (let i = 0; i < videoAllList.value.length; i++) {
        if (videoAllList.value[i].video_name.length > 60) {
          videoAllList.value[i].video_name =
            videoAllList.value[i].video_name.slice(0, 60) + "...";
        }
      }
      videoViewList.value = [...videoAllList.value.slice(0,9)];
    },
    (error) => {
      console.log(error);
    }
  );

  videoLike.value = Array(videoAllList.value.length).fill(false);
  isVideoPopup.value = Array(videoAllList.value.length).fill(false);
  handleScroll();
  document.addEventListener("scroll", handleScroll);
});

const emit = defineEmits(["nextStep"]);

// 다음 단계로 이동
const nextStep = async (input) => {
  if (!flag) {
    Swal.fire({
      icon: "warning",
      html: 
        '<h4><b>관심 영상을 하나 이상 선택해주세요!<b><h4>',
      confirmButtonColor: '#cc0000',
    });
  } else {
    emit("nextStep", input);
    for (let i = 0; i < videoAllList.value.length; i++) {
      if (videoLike.value[i]) {
        addList.value.push({
          userId: userId.value,
          videoId: videoAllList.value[i].video_id,
          historyStatus: false,
          historySentence: "register",
        });
      }
    }
  }



  try {
    await new Promise((resolve, reject) => {
      insertRegistHistory(
        addList.value,
        ({ data }) => {
          resolve();
        },
        (error) => {
          console.log(error);
          reject(error);
        }
      );
    });

    await handleVideoClick();
  } catch (error) {
    console.error("오류 발생:", error);
  }
};

const handleScroll = (e) => {
  let scrollPosition =
    window.scrollY || window.pageYOffset || document.documentElement.scrollTop;
  let pageHeight = document.documentElement.scrollHeight;
  let windowHeight =
    window.innerHeight ||
    document.documentElement.clientHeight ||
    document.body.clientHeight;

  if (scrollPosition + windowHeight + 100 >= pageHeight) {
    // 페이지가 맨 아래로 스크롤되었을 때 실행할 동작
    videoViewList.value = [
      ...videoViewList.value,
      ...videoAllList.value.slice(page * 9, page * 9 + 9),
    ];
    page++;

    scrollPosition =
      window.scrollY ||
      window.pageYOffset ||
      document.documentElement.scrollTop;
    pageHeight = document.documentElement.scrollHeight;
    windowHeight =
      window.innerHeight ||
      document.documentElement.clientHeight ||
      document.body.clientHeight;
  }
};

const addLike = (index) => {

  if (!flag) flag = true;
  videoLike.value[index] = !videoLike.value[index];
};

const changeThumbnail = (index) => {
  return computed(() => {
    let blur = "";
    let state = videoLike.value[index];
    switch (state) {
      case true:
        blur = "grayscale-[50%]";
        break;
      case false:
        break;
    }
    return `${blur}`;
  }).value;
};

const showVideoPopup = (index) => {
  isVideoPopup.value[index] = true;
};

const hideVideoPopup = (index) => {
  isVideoPopup.value[index] = false;
};
</script>

<style>
.gray-effect {
  filter: grayscale(10%);
}

#video-name {
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.4em;
  height: 2.9em;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  transition: text-decoration 0.3s ease;
}

svg {
  transition: fill 0.3s ease;
  
}
</style>
