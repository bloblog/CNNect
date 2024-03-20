<template>
  <div
    v-if="!$route.meta.hideHeader"
    class="header-frame top-0 z-20 sticky flex"
  >
    <RouterLink to="/"
      ><span class="flex items-start mx-2 my-7 text-white"
        >Home</span
      ></RouterLink
    >
    <RouterLink v-if="isLogin" to="/history"
      ><span class="flex items-start mx-2 my-7 text-white"
        >학습기록</span
      ></RouterLink
    >
    <RouterLink v-if="isLogin" to="/study"
      ><span class="flex items-start mx-2 my-7 text-white"
        >학습진행</span
      ></RouterLink
    >

    <RouterLink v-if="isLogin" to="/level"
      ><span class="flex items-start mx-2 my-7 text-white"
        >레벨테스트
      </span></RouterLink
    >
    <!-- Search button -->
    <div class="relative">
      <div
        @click="toggleSearch()"
        class="border rounded-md top-6 absolute right-[18%] px-2 pt-1 sw-7 h-7"
      >
        <svg
          width="17"
          height="17"
          viewBox="0 0 17 17"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            fill-rule="evenodd"
            clip-rule="evenodd"
            d="M7.5 0C11.6421 0 15 3.35786 15 7.5C15 9.21015 14.4276 10.7866 13.464 12.0483L16.7071 15.2929C17.0976 15.6834 17.0976 16.3166 16.7071 16.7071C16.3466 17.0676 15.7794 17.0953 15.3871 16.7903L15.2929 16.7071L12.0483 13.464C10.7866 14.4276 9.21015 15 7.5 15C3.35786 15 0 11.6421 0 7.5C0 3.35786 3.35786 0 7.5 0ZM7.5 2C4.46243 2 2 4.46243 2 7.5C2 10.5376 4.46243 13 7.5 13C8.85114 13 10.0885 12.5128 11.0459 11.7045C11.091 11.5536 11.1738 11.412 11.2929 11.2929C11.412 11.1738 11.5536 11.091 11.7041 11.0446C12.5128 10.0885 13 8.85114 13 7.5C13 4.46243 10.5376 2 7.5 2Z"
            fill="#FFFFFF"
          />
        </svg>
      </div>
      <!-- 펼쳐지는 검색 input -->
      <transition name="slide">
        <div v-if="isSearchOpen" class="absolute top-0 left-0 p-2">
          <input
            type="text"
            class="border px-2 py-1 rounded-lg focus:outline-none"
            placeholder="Search..."
            @blur="closeSearch"
          />
        </div>
      </transition>
    </div>
    <!-- Dropdown button -->
    <button
      v-if="isLogin"
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
  </div>
</template>

<script setup>
import { userStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";

const uStore = userStore();

const imgUrl = ref("");
const { isLogin, nickName, level } = storeToRefs(uStore);
const { setLogout } = uStore;

const isSearchOpen = ref(false);

const logout = () => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  setLogout();
  location.href = "/";
};

onMounted(() => {
  imgUrl.value = "/public/level/level" + level.value + ".png";
});

const toggleSearch = () => {
  isSearchOpen.value = !isSearchOpen.value;
};
</script>

<style scoped>
.header-frame {
  @apply h-[10%] bg-black shadow-md;
}

.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s;
}

.slide-enter,
.slide-leave-to {
  transform: translateX(100%);
}
</style>
