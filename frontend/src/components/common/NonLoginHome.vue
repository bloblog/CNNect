<template>
  <div id="background" class="">
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
      <div id="slider">
        <div class="image-box image-box-top">
          <div
            class="swiper-slide flex p-0.5"
            v-for="(url, index) in imageUrls.slice(0, imageUrls.length / 2)"
            :key="index"
          >
            <img :src="`https://img.youtube.com/vi/${url}/mqdefault.jpg`"class="h-48" />
          </div>
        </div>
        <div class="image-box image-box-bottom">
          <div
            class="swiper-slide flex p-0.5"
            v-for="(url, index) in imageUrls.slice(imageUrls.length / 2 + 1, imageUrls.length)"
            :key="index"
          >
            <img :src="`https://img.youtube.com/vi/${url}/mqdefault.jpg`"class="h-48" />

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
  initFlowbite();
});

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
      html: 
        "<h3><b>로그인에 실패했습니다.</b></h3>" + 
        "아이디 및 비밀번호를 확인해주세요!",
      confirmButtonColor: '#cc0000',

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

const imageUrls = [
  'wwg8M7Kypkw', '64ivmhyyRSI', '9AdixAJVTO0', 'mtptFuBAg9Q', '5F6YRQKZX9E', 'yNPM2obgE7g', 'CDphmhaToV4', '65CI8hznDy4', 'xTN1IcqZvOo', 'VyUFobjUTng', 'ei8wkDsxnaY', 'y8Cg3LwIcZk', 'buqKnrMLwVc', 'eT9E4zRwuGY', '4JrhaZrpKBc', 'wFqG62O9kYI', 'DGjSzIhmXRk', '2IXl4qJGrRk', 'W47cFRJ2iI4', 'fJzoicql6Yw', 'dbUkYLU8Z8U', 'P5vyhx3HC1k', '9VExWe3tHfk', 'vHbU9ighePk', 'BKrSjYvpYGg', 'WRlWOmgImBA', 'djCKmQVkftY', 'qcdSuzWRSjo', 'sVv72VtZinc', 'GIUR37P4764'
  , 'iTrj85LRly0', 'Qmjsl9wPiM4', 'EmxDe77ODBQ', 'eZQBUUYHsn0', 'vKxY6i3X8Es', 'GGMC0CiVxzI', 'jWJpmQgv39Y', 'plHxvb9sd3U', 'gRdXGpnovPg', 'w4CThc7mfEo', 'ZbLwPZWT7hg', 'ALe0rotHLhs', '3a9eC3rTTkU', 'KGaa6GbYhrA', 'OXaCtWxCxng'
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
  top: 60vh;
  /* width: 200vw; */
  transform: rotate(-15deg);
}

.image-box {
  width: 450vw;
  height: 100%;
  display: flex;
  flex-wrap: nowrap;
  animation: bannermove 150s linear infinite;
  opacity: 0.3;
}

.image-box-bottom {
  position: relative;
  left: 20%;
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
