<template>
  <div class="flex w-full relative">
    <!--stepper start-->
    <div
      id="sidebar-multi-level-sidebar"
      class="fixed top-[] left-0 z-40 w-[20vw] h-full overflow-auto transition-transform -translate-x-full sm:translate-x-0"
      aria-label="Sidebar"
    >
      <div class="h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800">
        <ul class="space-y-2 font-medium">
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
      </div>
    </div>
    <!--stepper end-->
    <div class="ml-[20vw] w-3/4 h-full">
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
import { setUserLevelToken, setUserLevelNotToken } from "@/api/test";
import { storeToRefs } from "pinia";
import { userStore } from "@/stores/userStore";
import { handleVideoClick } from "@/api/user.js";
import RegisterLevel from "@/components/user/RegisterLevelComponent.vue";
import RegisterResult from "@/components/user/RegisterLevelResultComponent.vue";

const uStore = userStore();
const { userId } = storeToRefs(uStore);
const { setLevel } = uStore;

const level = ref(1);
const registerSwitch = shallowRef(RegisterLevel);

const handleFinishRegister = () => {
  registerSwitch.value = RegisterResult;
  if (localStorage.getItem("accessToken")) {
    setUserLevelToken({
      level: level.value,
    });
  } else {
    console.log(userId.value);
    setUserLevelNotToken({
      userId: userId,
      level: level.value,
    });
  }
  setLevel(level.value);
  handleVideoClick();
};

const handleUpdateLevel = (newLevel) => {
  level.value = newLevel;
};
</script>

<style></style>
