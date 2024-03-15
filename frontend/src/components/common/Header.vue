<template>
 <div v-if="(isLogin == true) && (!$route.meta.hideHeader)" class="header-frame top-0 z-20 sticky flex">
    <RouterLink to="/"
      ><span class="flex items-start mx-2 my-7 text-white"
        >Home</span
      ></RouterLink
    >
    <RouterLink to="/history"
      ><span class="flex items-start mx-2 my-7 text-white"
        >학습기록</span
      ></RouterLink
    >
    <RouterLink to="/study"
      ><span class="flex items-start mx-2 my-7 text-white"
        >학습진행</span
      ></RouterLink
    >

    <RouterLink to="/level"
      ><span class="flex items-start mx-2 my-7 text-white"
        >레벨테스트
      </span></RouterLink
    >
    <!-- Dropdown button -->
    <button
      id="dropdownDividerButton"
      data-dropdown-toggle="dropdownDivider"
      class="text-white font-semibold text-center inline-flex items-center absolute top-4 right-3"
      type="button"
    >
      <img
        alt="profile"
        :src="imgUrl"
        class="rounded-full w-12 h-12 border-2 border-gray-400 shadow-md mx-3"
      />
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
        class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow-md !left-8 !w-36 border"
      >
        <RouterLink to="/mypage"
          ><span class="block px-4 py-2 rounded-lg text-gray-700"
            >마이페이지</span
          ></RouterLink
        >

        <div class="py-1 bg-gray-200 rounded-b-lg">
          <a
            @click="logout()"
            class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-300"
            >로그아웃</a
          >
        </div>
      </div>
      <!-- Dropdown menu -->
    </button>
    >
  </div>
</template>

<script setup>
import { userStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";

const uStore = userStore();

const imgUrl = ref("");
const { isLogin, nickName, level } = storeToRefs(uStore);
const {setLogout} = uStore;


const logout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    setLogout()
    location.href = "/";
}

onMounted(() => {
  imgUrl.value = "/public/level/level" + level.value + ".png";
})
</script>

<style scoped>
.header-frame {
  @apply h-[10%] bg-black shadow-md;
}
</style>
