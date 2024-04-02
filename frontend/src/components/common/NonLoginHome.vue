<template>
  <div id="background">
    <div>
      <img src="@/assets/logo.png" class="w-48 absolute top-10 left-10" />
    </div>

    <div
      class="relative overflow-hidden flex flex-col items-center justify-center"
      id="main-item-container"
    >
      <div class="flex pb-5">
        <h2 class="z-10 text-4xl font-bold text-white text-center">
          나에게 맞는 <span class="text-4xl font-bold text-theme-red">CNN 뉴스</span>로 영어를 배울 수 있습니다.
        </h2>
      </div>
      <h4 class="z-10 text-2xl text-white pb-10 text-center">
        당신의 관심사와 영어 수준을 바탕으로 추천해드립니다.
      </h4>
      <!-- <img src="@/assets/thumbnail.png" id="background-img" class="absolute max-w-none inset-y-80"> -->
      <div id="slider">
        <div class="image-box image-box-top">
          <div
            class="swiper-slide flex p-0.5"
            v-for="(image, index) in imagePaths.slice(0,9)"
            :key="index"
          >
            <img :src="image" class="w-80" />
          </div>
        </div>
        <div class="image-box image-box-bottom">
          <div
            class="swiper-slide flex p-0.5"
            v-for="(image, index) in imagePaths.slice(1,10)"
            :key="index"
          >
            <img :src="image" class="w-80" />
          </div>
        </div>
      </div>
      <button
        v-if="isLogin == false"
        data-modal-target="authentication-modal"
        data-modal-toggle="authentication-modal"
        class="flex z-10 text-white bg-theme-red hover:bg-theme-redbrown focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-2xl px-10 py-5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        type="button"
      >
        로그인하기
      </button>
      <!-- login modal -->
      <div
        id="authentication-modal"
        tabindex="-1"
        aria-hidden="true"
        class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full"
      >
        <div class="relative p-4 w-full max-w-md max-h-full">
          <!-- Modal content -->
          <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
            <!-- Modal header -->
            <div
              class="flex items-center justify-between rounded-t dark:border-gray-600"
            >
              <button
                type="button"
                class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                data-modal-hide="authentication-modal"
              >
                <svg
                  class="w-3 h-3"
                  aria-hidden="true"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 14 14"
                >
                  <path
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                  />
                </svg>
                <span class="sr-only">Close modal</span>
              </button>
            </div>
            <!-- Modal body -->
            <div class="p-4 md:p-5">
              <div class="space-y-4">
                <div>
                  <label
                    for="email"
                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                    >이메일</label
                  >
                  <input
                    type="email"
                    name="email"
                    id="email"
                    v-model="loginData.userEmail"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
                    required
                  />
                </div>
                <div>
                  <label
                    for="password"
                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                    >비밀번호</label
                  >
                  <input
                    type="password"
                    name="password"
                    id="password"
                    v-model="loginData.userPassword"
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                    required
                  />
                </div>
              </div>
              <button
                type="button"
                @click="login()"
                class="w-full mt-3 mb-3  text-white bg-theme-red hover:bg-theme-redbrown focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
              >
                로그인
              </button>
              <div class="text-sm font-medium text-gray-500 dark:text-gray-300">
                회원이 아니신가요?
                <a
                  data-modal-hide="authentication-modal"
                  @click="register()"
                  class="text-theme-redbrown hover:underline dark:text-blue-500"
                  >회원가입</a
                >
              </div>
            </div>
          </div>
        </div>
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
import Swal from "sweetalert2";

const route = useRoute();
const router = useRouter();
const uStore = userStore();

const loginData = ref({
  userEmail: "",
  userPassword: "",
});

const msg = Swal.mixin({
  position: "center",
  showConfirmButton: true,
  confirmButtonText: "확인",
  backdrop: true,
}); // alert창 기본틀

const { setLogin, setLogout, setNickname, setLevel } = uStore;
const { isLogin } = storeToRefs(uStore);

onMounted(() => {
  // setInterval(resetSlider, 5000);
  initFlowbite();
});

// const resetSlider = () => {
//   const slider = document.getElementById('slider');
//   const imageBox = document.querySelector('.image-box');

//   if (slider && imageBox) {
//     const sliderWidth = slider.offsetWidth;
//     const imageBoxWidth = imageBox.offsetWidth;
//     const distance = sliderWidth - imageBoxWidth;

//     if (slider.scrollLeft >= distance) {
//       // 배열의 끝에 도달하면 첫 번째 요소를 배열에 추가하여 배열을 원형으로 만들기
//       imagePaths.push(imagePaths[0]);
//     }

//     slider.scrollTo({ left: 0, behavior: 'smooth' });
//   }
// };

// set the modal menu element
const $targetEl = document.getElementById("authentication-modal");

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
  },
  onShow: () => {
  },
  onToggle: () => {
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
      localStorage.setItem("refreshToken", data.data.jwtToken.refreshToken);
      localStorage.setItem("accessToken", data.data.jwtToken.accessToken);
      setLogin();
      setLevel(data.data.level);
      setNickname(data.data.nickName);

      location.href = "/";
    },
    (error) => {
      Swal.fire({
      icon: "error",
      title: "로그인에 실패했습니다.",
      text : "아이디 및 비밀번호를 확인해주세요!"
    });
      // alert("아이디 및 비밀번호를 확인해주세요!");
      loginData.value.userPassword = "";
      console.log(error);
    }
  );
};

const register = () => {
  router.push("/register");
};

const imagePaths = [
  "https://i.ytimg.com/vi/jsxI0QjxJs8/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLAWZ1lChDEn0h9YVfSx_JAl0eD-zA",
  "https://i.ytimg.com/vi/rGMqBoF3hkU/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLBxvk7EL2K_kCRfy-QJKeIaQvHg6g",
  "https://i.ytimg.com/vi/2keAv3W3edw/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLCEn3b9fTH3MA1Dh8Q_s_d2vBez4Q",
  "https://i.ytimg.com/vi/7ozPfsLRxZw/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLAOOEKg7h8HZUeFIf8Adwl2FlmouQ",
  "https://i.ytimg.com/vi/Q1KbKe2eJaY/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLAofmgL2v0bY4aaZWRPSprL5-E93Q",
  "https://i.ytimg.com/vi/ibBNhhhTgMk/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLA9abB7-lskAlFlmgjx10cZU8Fy3Q",
  "https://i.ytimg.com/vi/jsxI0QjxJs8/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLAWZ1lChDEn0h9YVfSx_JAl0eD-zA",
  "https://i.ytimg.com/vi/rGMqBoF3hkU/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLBxvk7EL2K_kCRfy-QJKeIaQvHg6g",
  "https://i.ytimg.com/vi/2keAv3W3edw/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLCEn3b9fTH3MA1Dh8Q_s_d2vBez4Q",
  "https://i.ytimg.com/vi/2keAv3W3edw/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLCEn3b9fTH3MA1Dh8Q_s_d2vBez4Q",
  
];
</script>

<style scoped>
#background {
  height: 100vh;
  background-image: linear-gradient(45deg, #80112f, #0f1b4f);
}

#main-item-container {
  height: 100vh;
}

#slider {
  overflow: hidden;
  position: absolute;
  top: 50vh;
  /* width: 130vw; */
  transform: rotate(-15deg);
}

.image-box {
  width: 200vw;
  height: 100%;
  display: flex;
  flex-wrap: nowrap;
  animation: bannermove 50s linear infinite;
  opacity: 0.5;
}

.image-box-bottom {
  position: relative;
  right: 15%;
}

@keyframes bannermove {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(-50%, 0);
  }
}
</style>
