<template>
  <div
    v-if="
      (isLogin == true && !$route.meta.hideHeader) || $route.meta.isRegister
    "
    class="header-frame top-0 z-20 sticky flex justify-between"
  >
    <div class="flex items-center">
      <RouterLink to="/">
        <img src="@/assets/logo.png" class="h-6 m-5">
      </RouterLink>
      <RouterLink v-if="isLogin" to="/history"
        ><span class="flex items-start mx-2 text-white">학습기록</span>
      </RouterLink>
      <RouterLink v-if="isLogin" to="/video"><span class="flex items-start mx-2 text-white">둘러보기</span></RouterLink>

      <RouterLink v-if="isLogin" to="/level"
        ><span class="flex items-start mx-2 text-white"
          >레벨테스트
        </span></RouterLink
      >
    </div>
    
    <div class="flex items-center">
      <!-- Search button -->
      <div class="search-area relative">
        <input class="custom-input" @keyup.enter="search()" v-model="searchInput" type="text" name="" ref="customInput" />
        <button class="single-search icon-area" @click="toggleSearch()">
          <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-search" viewBox="0 0 25 25" stroke-width="3" stroke="#CC0000" fill="none" stroke-linecap="round" stroke-linejoin="round">
              <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
              <path d="M10 10m-7 0a7 7 0 1 0 14 0a7 7 0 1 0 -14 0" />
              <path d="M21 21l-6 -6" />
            </svg>
        </button>
      </div>

      <!-- Dropdown button -->
      <button v-if="isLogin" id="dropdownDividerButton" data-dropdown-toggle="dropdownDivider"
        class="text-white font-semibold text-center inline-flex items-center pl-3 relative" type="button">
        <img alt="profile" :src="imgUrl" class="rounded-full w-10 h-10 border-2 border-gray-400 shadow-md mx-3" />
        {{ nickName }}님 어서오세요!
        
        <svg
          class="w-2.5 h-2.5 ms-3 my-1"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 10 6"
        >
          <path
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="m1 1 4 4 4-4"
          />
        </svg>
        <!-- Dropdown menu -->
        <div
          id="dropdownDivider"
          class="hidden z-10 bg-white divide-y divide-gray-100 rounded-lg shadow-md absolute !top-[-30%] !left-[10%] !w-36 border"
        >
          <div class="hover:bg-gray-300 rounded-t-lg">
            <RouterLink to="/mypage"
              ><span class=" block px-5 py-3 text-gray-700"
                >마이페이지</span
              ></RouterLink
            >
          </div>
  
          <div class="rounded-b-lg hover:bg-gray-300">
            <a
              @click="logout()"
              class="block px-5 py-3 text-sm text-theme-red "
              >로그아웃</a
            >
          </div>
        </div>
        <!-- Dropdown menu -->
      </button>
    </div>
    
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
  console.log("enter : ");
  console.log(searchInput.value);
  const keyword = searchInput.value;
  router.push({ path: "/search", query: { keyword } });
  searchInput.value = "";
  toggleSearch();
};

onMounted(() => {
  imgUrl.value = "/level/level" + level.value + ".png";
  console.log(level.value + " " + imgUrl.value);
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
    iconArea.style.borderTopLeftRadius = "0px";
    iconArea.style.borderBottomLeftRadius = "0px";

    // 서치 영역이 닫힐 때 서치 아이콘의 radius를 변경하는 부분을 setTimeout으로 감싸서 일정 시간이 지난 후에 원래 radius로 복원
    setTimeout(() => {
      iconArea.style.borderTopLeftRadius = "30px";
      iconArea.style.borderBottomLeftRadius = "30px";
    }, 400);
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
  @apply h-[] bg-black shadow-md;
  min-height: fit-content;
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
  /* justify-content: center; */
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
