<template>
  <div class="view-frame flex items-start overflow-y-auto">
    <div id="profile" class="w-1/2 sticky top-0">
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
            <img class="h-full w-full rounded-full" :src="imgUrl" alt="" />
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
            <h3 class="text-bluePrimary text-2xl font-bold">{{ badge }}</h3>
            <p class="text-lightSecondary text-sm font-normal">Badge</p>
          </div>
          <div class="flex flex-col items-center justify-center">
            <h3 class="text-bluePrimary text-2xl font-bold">{{ video }}</h3>
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
    <div id="badge" class="w-1/2 flex flex-col items-center justify-center">
      <span class="nav-title mb-5"> 획득 뱃지 </span>
      <div class="flex-container items-center justify-center">
        <div v-for="badge in badgeList" :key="badge.badgeId" class="flex-item items-center justify-center">
          <div
            class="flex border rounded-full w-32 h-32 justify-center items-center "
            :class="{'filter grayscale bg-gray-200 opacity-40' : badge.check == false,
                      'border-4 border-yellow-500 bg-yellow-300' : badge.tier == 1
                    }"
          >
            <img class="flex" :src="badge.url" 
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { userStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";
import { userInfo } from "@/api/user";
import { allBadges, myBadges } from "@/api/badge";

const uStore = userStore();

const imgUrl = ref("");
const { nickName, level } = storeToRefs(uStore);
const badge = ref(0);
const video = ref(0);

const badgeList = ref([]);
const userBadge = ref([]);

onMounted(() => {
  imgUrl.value = "/public/level/level" + level.value + ".png";
  userInfo(
    ({ data }) => {
      console.log(data);
      badge.value = data.data.userBadgeCount;
      video.value = data.data.userVideoCount;
    },
    (error) => {
      console.log(error);
    }
  );

  myBadges(
    ({ data }) => {
      console.log(data);
      userBadge.value = data.data;
      allBadges(
        ({ data }) => {
          console.log(data);
          for (let i = 0; i < data.data.length; i++) {
            let condition = data.data[i].badgeCondition;
            let category = data.data[i].badgeCategory;
            let url =
              condition == "VIDEO"
                ? "/public/badge/" + condition + "/" + category + ".png"
                : "/public/badge/" + condition + "/" + condition + ".png";
            let check = userBadge.value.some((badge) => {
              return JSON.stringify(badge) === JSON.stringify(data.data[i]);
            });
            let tier = data.data[i].badgeId % 3 == 1 ? 1 : (data.data[i].badgeId % 3 == 2 ? 2 : 3);
            console.log(check);
            data.data[i].url = url;
            data.data[i].check = check;
            data.data[i].tier = tier;
            badgeList.value.push(data.data[i]);
            console.log(data.data[i]);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    (error) => {
      console.log(error);
    }
  );
});
</script>

<style>
.flex-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between; /* 각 이미지를 컨테이너의 양 끝에 정렬 */
}

.flex-item {
  flex-basis: calc(
    25% - 10px
  ); /* 한 줄에 4개씩 배치하므로 각 항목의 너비는 25%로 설정 */
  margin-bottom: 20px; /* 이미지 아래에 여백 추가 */
}

.flex-item img {
  width: 75%; /* 이미지를 꽉 채웁니다. */
  height: auto; /* 가로 비율을 유지하면서 세로 비율을 자동으로 조정합니다. */
}
</style>
