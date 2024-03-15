<template>
  <div class="view-frame flex items-start">
    <div id="profile" class="w-1/3">
      <!-- <span class="nav-title"> 프로필 </span> -->
      <div
        class="dark:!bg-navy-800 shadow-shadow-500 shadow-3xl rounded-primary relative mx-auto flex h-full w-full max-w-[550px] flex-col items-center bg-white bg-cover bg-clip-border p-[16px] dark:text-white dark:shadow-none"
      >
        <div
          class="relative mt-1 flex h-32 w-full justify-center rounded-xl bg-cover"
          style="background-image: url('https://i.ibb.co/FWggPq1/banner.png')"
        >
          <div
            class="absolute -bottom-12 flex h-[88px] w-[88px] items-center justify-center rounded-full border-[4px] border-white bg-pink-400"
          >
            <img
              class="h-full w-full rounded-full"
              :src="imgUrl"
              alt=""
            />
          </div>
        </div>
        <div class="mt-16 flex flex-col items-center">
          <h4 class="text-bluePrimary text-xl font-bold">{{ nickName }}</h4>
        </div>
        <div class="mt-6 mb-3 flex gap-4 md:!gap-14">
          <div class="flex flex-col items-center justify-center">
            <h3 class="text-bluePrimary text-2xl font-bold">{{ level }}</h3>
            <p class="text-lightSecondary text-sm font-normal">Level</p>
          </div>
          <div class="flex flex-col items-center justify-center">
            <h3 class="text-bluePrimary text-2xl font-bold">9.7K</h3>
            <p class="text-lightSecondary text-sm font-normal">Badge</p>
          </div>
          <div class="flex flex-col items-center justify-center">
            <h3 class="text-bluePrimary text-2xl font-bold">434</h3>
            <p class="text-lightSecondary text-sm font-normal">Study</p>
          </div>
        </div>
        <!--프로필카드끝나따-->
        <button
          type="button"
          class="text-white justify-center mt-5 bg-theme-redbrown hover:bg-theme-red focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          레벨 재측정
          <svg
            class="rtl:rotate-180 w-3.5 h-3.5 ms-2"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 14 10"
          >
            <path
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M1 5h12m0 0L9 1m4 4L9 9"
            />
          </svg>
        </button>
      </div>
    </div>
    <div id="badge" class="w-2/3">
      <span class="nav-title"> 획득 뱃지 </span>
    </div>
  </div>
</template>

<script setup>
import { userStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";

const uStore = userStore();

const imgUrl = ref("");
const { nickName, level } = storeToRefs(uStore);

onMounted(() => {
  imgUrl.value = "/public/level/level" + level.value + ".png";
})

</script>

<style></style>
