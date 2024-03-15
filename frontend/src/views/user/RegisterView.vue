<template>
  <div class="flex justify-between items-center">
    <ol
      class="flex items-center w-full pl-56 pr-56 pt-8 text-sm font-medium text-center text-gray-500 dark:text-gray-400 sm:text-base"
    >
      <!-- 회원가입 단계 stepper(각 단계 progress : true면 background-color 변경하기)-->
      <li
        class="flex md:w-full items-center sm:after:content-[''] after:w-full after:h-1 after:border-b after:border-gray-200 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700"
      >
        <span
          :class="colorChange(0)"
          class="flex items-center justify-center w-8 h-8 border border-gray-500 rounded-full shrink-0 dark:border-blue-500"
        >
          1
        </span>
        <span
          class="flex items-center ml-3 whitespace-nowrap after:content-['/'] sm:after:hidden after:mx-2 after:text-gray-200 dark:after:text-gray-500"
        >
          정보입력
        </span>
      </li>
      <li
        class="flex md:w-full items-center whitespace-nowrap after:content-[''] after:w-full after:h-1 after:border-b after:border-gray-200 after:border-1 after:hidden sm:after:inline-block after:mx-6 xl:after:mx-10 dark:after:border-gray-700"
      >
      <span
          :class="colorChange(1)"
          class="flex items-center justify-center w-8 h-8 border border-gray-500 rounded-full shrink-0 "
        >
          2
        </span>
        <span
          class="flex items-center ml-3 after:content-['/'] sm:after:hidden after:mx-2 after:text-gray-200 dark:after:text-gray-500"
        >
          관심영상
        </span>
      </li>
      <li class="flex items-center whitespace-nowrap">
        <span
          :class="colorChange(2)"
          class="flex items-center justify-center w-8 h-8 border border-gray-500 rounded-full shrink-0 "
        >
          3
        </span>
        <span
          class="flex items-center ml-3 after:content-['/'] sm:after:hidden after:mx-2 after:text-gray-200 dark:after:text-gray-500"
        >
          레벨테스트
        </span>
      </li>
    </ol>
  </div>
  <!--stepper end-->
  <component :is="registerSwitch" @nextStep="nextStep"></component>

</template>

<script setup>
import {ref, computed, shallowRef} from 'vue';
import RegisterInfo from '@/components/user/RegisterInfoComponenet.vue';
import RegisterLevel from '@/components/user/RegisterLevelComponenet.vue';
import RegisterVideo from '@/components/user/RegisterVideoComponent.vue';

const steps = ref([
  true, false, false
])

const colorChange = (index) => {
  return computed(() => {
    let bgColor = ''
    let textColor = ''
    let state = steps.value[index]
    console.log(state)
    switch(state) {
      case true:
        bgColor = 'bg-red-600'
        textColor = 'text-white'
        break
      case false:
        bgColor = 'bg-white'
        textColor = 'text-gray-500'
        break
    }

    return `${bgColor} ${textColor}`
  }).value
}

const registerSwitch = shallowRef(RegisterInfo)

const nextStep = input => {
  if(input == 1){
    registerSwitch.value = RegisterVideo
    steps.value[1] = true;
  }else if(input == 2){
    registerSwitch.value = RegisterLevel
    steps.value[2] = true;
  }
}

</script>

<style></style>
