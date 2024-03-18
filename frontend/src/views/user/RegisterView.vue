<template>
  <div class="flex w-full">
    <!--stepper start-->
    <aside
      id="sidebar-multi-level-sidebar"
      class="fixed top-0 left-0 z-40 w-1/5 h-screen transition-transform -translate-x-full sm:translate-x-0"
      aria-label="Sidebar"
    >
      <div class="h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800">
        <ul class="space-y-2 font-medium">
          <li>
            <a
              href="#"
              class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <span class="ms-3">정보입력</span>
            </a>
          </li>
          <li>
            <a
              href="#"
              class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <span class="flex-1 ms-3 whitespace-nowrap">관심영상</span>
            </a>
          </li>
          <li>
            <button
              type="button"
              class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
            >
              <span
                class="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap"
                >레벨테스트</span
              >
              <svg
                :class="{ hidden: !isDropdownOpen }"
                class="w-3 h-3"
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
            </button>
            <ul :class="{ hidden: !isDropdownOpen }" class="py-2 space-y-2">
              <li>
                <a
                  href="#"
                  class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                  >Lv.1</a
                >
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                  >Lv.2</a
                >
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                  >Lv.3</a
                >
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </aside>
    <!--stepper end-->
    <component
      class="w-4/5"
      :is="registerSwitch"
      @nextStep="nextStep"
    ></component>
  </div>
</template>

<script setup>
import { ref, computed, shallowRef } from "vue";
import RegisterInfo from "@/components/user/RegisterInfoComponenet.vue";
import RegisterLevel from "@/components/user/RegisterLevelComponenet.vue";
import RegisterVideo from "@/components/user/RegisterVideoComponent.vue";

const isDropdownOpen = ref(false);
const steps = ref([true, false, false]);

const colorChange = (index) => {
  return computed(() => {
    let bgColor = "";
    let textColor = "";
    let state = steps.value[index];
    console.log(state);
    switch (state) {
      case true:
        bgColor = "bg-red-600";
        textColor = "text-white";
        break;
      case false:
        bgColor = "bg-white";
        textColor = "text-gray-500";
        break;
    }

    if (steps.value[index]) {
      return ["bg-red-600", "text-white"]; // 활성 상태일 때 클래스
    } else {
      return ["bg-white", "text-gray-500"]; // 비활성 상태일 때 클래스
    }
  }).value;
};

const registerSwitch = shallowRef(RegisterInfo);

const nextStep = (input) => {
  if (input == 1) {
    registerSwitch.value = RegisterVideo;
    steps.value[1] = true;
  } else if (input == 2) {
    isDropdownOpen.value = !isDropdownOpen.value;
    console.log(isDropdownOpen.value);
    registerSwitch.value = RegisterLevel;
    steps.value[2] = true;
  }
};
</script>

<style></style>
