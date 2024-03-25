<template>
  <div v-if="(isLogin == true && !$route.meta.hideHeader) || $route.meta.isRegister" class="header-frame top-0 z-20 sticky flex items-center">
    <RouterLink to="/">
      <img src="@/assets/logo.png" class="h-6 m-5">
    </RouterLink>
    <RouterLink v-if="isLogin" to="/history"><span class="flex items-start mx-2 my-7 text-white">학습기록</span>
    </RouterLink>
    <RouterLink v-if="isLogin" to="/study"><span class="flex items-start mx-2 my-7 text-white">학습진행</span></RouterLink>

    <RouterLink v-if="isLogin" to="/level"><span class="flex items-start mx-2 my-7 text-white">레벨테스트
      </span></RouterLink>
    <!-- Search button -->
    <div class="search-area c-height absolute right-[20%]">
      <input class="custom-input" @keyup.enter="search()" v-model="searchInput" type="text" name="" ref="customInput" />
      <button class="single-search icon-area">
        <a @click="toggleSearch()" class="icon-area"><svg width="17" height="17" viewBox="0 0 17 17" fill="none"
            xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M7.5 0C11.6421 0 15 3.35786 15 7.5C15 9.21015 14.4276 10.7866 13.464 12.0483L16.7071 15.2929C17.0976 15.6834 17.0976 16.3166 16.7071 16.7071C16.3466 17.0676 15.7794 17.0953 15.3871 16.7903L15.2929 16.7071L12.0483 13.464C10.7866 14.4276 9.21015 15 7.5 15C3.35786 15 0 11.6421 0 7.5C0 3.35786 3.35786 0 7.5 0ZM7.5 2C4.46243 2 2 4.46243 2 7.5C2 10.5376 4.46243 13 7.5 13C8.85114 13 10.0885 12.5128 11.0459 11.7045C11.091 11.5536 11.1738 11.412 11.2929 11.2929C11.412 11.1738 11.5536 11.091 11.7041 11.0446C12.5128 10.0885 13 8.85114 13 7.5C13 4.46243 10.5376 2 7.5 2Z"
              fill="#000000" />
          </svg></a>
      </button>
    </div>

    <!-- Dropdown button -->
    <button v-if="isLogin" id="dropdownDividerButton" data-dropdown-toggle="dropdownDivider"
      class="text-white font-semibold text-center inline-flex items-center absolute right-3" type="button">
      <img alt="profile" :src="imgUrl" class="rounded-full w-10 h-10 border-2 border-gray-400 shadow-md mx-3" />
      {{ nickName }}님 어서오세요!
      <svg class="w-2.5 h-2.5 ms-3 my-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
        viewBox="0 0 10 6">
        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4" />
      </svg>
      <!-- Dropdown menu -->
      <div id="dropdownDivider"
        class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow-md !left-8 !w-36 border">
        <RouterLink to="/mypage"><span class="block px-4 py-2 rounded-lg text-gray-700">마이페이지</span></RouterLink>

        <div class="py-1 bg-gray-200 rounded-b-lg">
          <a @click="logout()" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-300">로그아웃</a>
        </div>
      </div>
      <!-- Dropdown menu -->
    </button>
  </div>
</template>

<script setup>
import { userStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";
import { useRouter, onBeforeRouteUpdate } from "vue-router";

const uStore = userStore();
const router = useRouter();

const imgUrl = ref("");
const customInput = ref(null);
const searchInput = ref("");
const { isLogin, nickName, level } = storeToRefs(uStore);
const { setLogout } = uStore;
const currentURL = ref("");

const isSearchOpen = ref(false);

const logout = () => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  setLogout();
  location.href = "/";
};

const search = () => {
  console.log("enter : ")
  console.log(searchInput.value)
  const keyword = searchInput.value
  router.push({path : '/search', query : {keyword}});
  searchInput.value = "";
  toggleSearch()
}

onMounted(() => {
  imgUrl.value = "/public/level/level" + level.value + ".png";
  currentURL.value = window.location.href;
});


const toggleSearch = () => {
  if (isSearchOpen.value) {
    // 검색바 열린 상태일때
    const input = customInput.value;
    const currentOffsetLeft = input.offsetLeft;
    //클릭 시 .custom-input에 스타일 적용
    customInput.value.style.padding = "0";
    customInput.value.style.caretColor = "#262626";

    input.style.width = `0px`; // 예시에 맞게 변경 가능
    input.style.left = `${currentOffsetLeft}px`;

    // customInput의 시작점을 오른쪽 끝으로 이동시켜 왼쪽으로 늘어나는 효과를 만듦

    // 클릭 시 .icon-area에 스타일 적용
    const iconArea = document.querySelector(".icon-area");
    iconArea.style.background = "white";
    iconArea.style.color = "#262626";
    iconArea.style.borderTopLeftRadius = "30px";
    iconArea.style.borderBottomLeftRadius = "30px";
  } else {
    const input = customInput.value;
    const currentOffsetLeft = input.offsetLeft;
    //클릭 시 .custom-input에 스타일 적용
    customInput.value.style.padding = "0 10px";
    customInput.value.style.caretColor = "#262626";

    input.style.width = `${input.offsetWidth + 200}px`; // 예시에 맞게 변경 가능
    input.style.left = `${currentOffsetLeft}px`;

    // customInput의 시작점을 오른쪽 끝으로 이동시켜 왼쪽으로 늘어나는 효과를 만듦

    // 클릭 시 .icon-area에 스타일 적용
    const iconArea = document.querySelector(".icon-area");
    iconArea.style.background = "white";
    iconArea.style.color = "#262626";
    iconArea.style.borderTopLeftRadius = "0";
    iconArea.style.borderBottomLeftRadius = "0";
  }
  isSearchOpen.value = !isSearchOpen.value;
};


</script>

<style scoped>
.header-frame {
  @apply h-[10%] bg-black shadow-md;
}

.single-search {
  margin-bottom: auto;
  margin-top: auto;
  height: 40px;
  background-color: white;
  border-radius: 30px;
  padding: 10px;
}

.search-area {
  display: flex;
  justify-content: center;
}

.c-height {
  height: 100%;
}

.custom-input {
  border-radius: 30px;
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
  border: 0;
  margin-top: auto;
  margin-bottom: auto;
  height: 40px;
  outline: 0;
  width: 0;
  line-height: 40px;
  transition: width 0.4s linear;
}

.icon-area {
  justify-content: center;
  align-items: flex-start;
  border-radius: 30px;
  height: 40px;
  width: 40px;
  float: right;
  display: flex;
  text-decoration: none;
  color: #262626;
}
</style>
