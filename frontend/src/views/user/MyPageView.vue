<template>
  <div class="view-frame flex items-start overflow-y-scroll scrollbar-hide">
    <div id="profile" class="w-1/3 sticky top-0">
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
          @click="goToLevel"
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
    <div id="badge" class="w-2/3 flex flex-col items-center justify-center">
      <span class="nav-title mb-5"> 획득 뱃지 </span>

      <div class="flex-container relative z-10 items-center justify-center">
        <div
          v-for="(badge, index) in badgeList"
          :key="index"
          class="relative flex-item items-center justify-center"
          @mouseover="changeOpacity(index, true)"
          @mouseleave="changeOpacity(index, false)"
        >
          <div
            class="flex border rounded-full w-32 h-32 justify-center items-center"
            :class="{
              'filter grayscale bg-gray-200 opacity-40': badge.check == false,
              'border-4 border-badge-bronze-line bg-badge-bronze':
                badge.tier == 1 && badge.check == true,
              'border-4 border-badge-silver-line bg-badge-silver':
                badge.tier == 2 && badge.check == true,
              'border-4 border-badge-gold-line bg-badge-gold':
                badge.tier == 3 && badge.check == true,
            }"
          >
            <img class="flex" :src="badge.url" />
          </div>
          <div
            :style="{ opacity: hoverList[index], transition: 'opacity 0.5s' }"
          >
            <div
              class="absolute -top-20 -left-5 z-30 w-48 h-28 text-sm font-light text-gray-500 bg-gray-200 rounded-lg border border-gray-200 shadow-sm duration-300 dark:text-gray-400 dark:border-gray-600 dark:bg-gray-800"
            >
              <div
                class="py-2 px-3 bg-gray-200 z-30 rounded-t-lg border-b border-gray-400 dark:border-gray-600 darkbg-gray-700"
              >
                <h3 class="font-bold text-gray-900 dark:text-white">
                  {{ badge.badgeName }}
                </h3>
              </div>
              <div class="py-2 px-3">
                <p class="font-semibold text-sm">
                  [{{
                    badge.badgeCondition == "SPEAKING"
                      ? "스피킹 " + badge.badgeScore + "점 이상 문장"
                      : badge.badgeCategory + " 카테고리의 영상"
                  }}]
                </p>

                <p class="font-semibold">{{ badge.badgeCount }}개 달성</p>
              </div>
            </div>
            <div
              class="absolute top-4 left-[35%] z-20 transform -translate-x-1/2"
            >
              <div
                class="w-6 h-6 z-20 bg-gray-200 border border-gray-200 rotate-45"
              ></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { userStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import { userInfo } from "@/api/user";
import { allBadges, myBadges } from "@/api/badge";

const uStore = userStore();
const router = useRouter();

const imgUrl = ref("");
const { nickName, level } = storeToRefs(uStore);
const badge = ref(0);
const video = ref(0);

const badgeList = ref([]);
const hoverList = ref([]); // 각 뱃지 idx별 hover 여부 저장
const userBadge = ref([]);

const showTooltip = ref(false);

onMounted(() => {
  imgUrl.value = "/level/level" + level.value + ".png";
  console.log(level.value + " " + imgUrl.value);
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
                ? "/badge/" + condition + "/" + category + ".png"
                : "/badge/" + condition + "/" + "speaking.png";
            let check = userBadge.value.some((badge) => {
              return JSON.stringify(badge) === JSON.stringify(data.data[i]);
            });
            let tier =
              data.data[i].badgeId % 3 == 1
                ? 1
                : data.data[i].badgeId % 3 == 2
                ? 2
                : 3;
            console.log(check);
            data.data[i].url = url;
            data.data[i].check = check;
            data.data[i].tier = tier;
            badgeList.value.push(data.data[i]);
            console.log(data.data[i]);
            hoverList.value = Array(badgeList.value.length).fill(0);
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

const changeOpacity = (index, isHover) => {
  hoverList.value[index] = isHover ? 0.8 : 0;
};

const goToLevel = () => {
  router.push("/level");
};
</script>

<style>
.flex-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between; /* 각 이미지를 컨테이너의 양 끝에 정렬 */
}

.flex-item {
  flex-basis: calc(
    21% - 5px
  ); /* 한 줄에 4개씩 배치하므로 각 항목의 너비는 25%로 설정 */
  margin-bottom: 20px; /* 이미지 아래에 여백 추가 */
}

.flex-item img {
  width: 75%; /* 이미지를 꽉 채웁니다. */
  height: auto; /* 가로 비율을 유지하면서 세로 비율을 자동으로 조정합니다. */
}
</style>
