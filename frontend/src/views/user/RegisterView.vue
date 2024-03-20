<template>
  <div class="flex w-full h-screen relative">
    <!--stepper start-->
    <div
      id="sidebar-multi-level-sidebar"
      class="fixed top-0 left-0 z-40 w-[25vw] h-full overflow-auto transition-transform -translate-x-full sm:translate-x-0"
      aria-label="Sidebar"
    >
      <div class="h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800">
        <ul class="space-y-2 font-medium">
          <li>
            <a
              href="#"
              class="flex items-center p-2 mb-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <span class="flex-1 p-1 ms-3 whitespace-nowrap">정보입력</span>
              <svg
                v-if="steps[0]"
                class="w-6 h-6"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 256 256"
                id="check"
              >
                <rect width="256" height="256" fill="none"></rect>
                <polyline
                  fill="none"
                  stroke="#CC0000"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="24"
                  points="216 72.005 104 184 48 128.005"
                ></polyline>
              </svg>
            </a>
          </li>
          <li>
            <a
              href="#"
              class="flex items-center p-2 mb-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <span class="flex-1 p-1 ms-3 whitespace-nowrap">관심영상</span>
              <svg
                v-if="steps[1]"
                class="w-6 h-6"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 256 256"
                id="check"
              >
                <rect width="256" height="256" fill="none"></rect>
                <polyline
                  fill="none"
                  stroke="#CC0000"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="24"
                  points="216 72.005 104 184 48 128.005"
                ></polyline>
              </svg>
            </a>
          </li>
          <li>
            <button
              type="button"
              class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
            >
              <span
                class="flex-1 p-1 ms-3 text-left rtl:text-right whitespace-nowrap"
                >레벨테스트</span
              >
              <svg
                v-if="steps[2]"
                class="w-6 h-6"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 256 256"
                id="check"
              >
                <rect width="256" height="256" fill="none"></rect>
                <polyline
                  fill="none"
                  stroke="#CC0000"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="24"
                  points="216 72.005 104 184 48 128.005"
                ></polyline>
              </svg>
            </button>
            <ul :class="{ hidden: !isDropdownOpen }" class="py-2 space-y-2">
              <li v-for="i in 6" :key="i">
                <a
                  href="#"
                  class="flex items-center w-full p-2 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                  :class="{ 'text-red-700': level === i }"
                >
                  Lv.{{ i }}
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
    <!--stepper end-->
    <div class="ml-auto w-3/4 h-full">
      <component
        :is="registerSwitch"
        :level="level"
        @nextStep="nextStep"
        @updateLevel="handleUpdateLevel"
        @finishRegister="handleFinishRegister"
      ></component>
    </div>
  </div>
</template>

<script setup>
import { ref, shallowRef } from "vue";
import RegisterInfo from "@/components/user/RegisterInfoComponent.vue";
import RegisterLevel from "@/components/user/RegisterLevelComponent.vue";
import RegisterVideo from "@/components/user/RegisterVideoComponent.vue";
import RegisterResult from "@/components/user/RegisterLevelResultComponent.vue";

const isDropdownOpen = ref(false);
const steps = ref([true, false, false]);
const level = ref(1);
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

const handleFinishRegister = () => {
  registerSwitch.value = RegisterResult;
};

const handleUpdateLevel = (newLevel) => {
  level.value = newLevel;
};
</script>

<style></style>
