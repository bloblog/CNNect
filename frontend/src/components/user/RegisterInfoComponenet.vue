<template>
  <form class="max-w-md mx-auto mt-10">
    <div class="flex items-center mb-5">
      <div class="w-1/4">
        <label
          for="email"
          class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900 dark:text-white"
          >이메일</label
        >
      </div>
      <div class="w-3/4">
        <input
          type="email"
          id="email"
          v-model="formData.email"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="name@google.com"
          required
        />
      </div>
    </div>
    <div class="flex items-center mb-5">
      <div class="w-1/4">
        <label
          for="password"
          class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900 dark:text-white"
          >비밀번호</label
        >
      </div>
      <div class="w-3/4">
        <input
          type="password"
          id="password"
          v-model="formData.password"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          required
        />
      </div>
    </div>
    <div class="flex items-center mb-5">
      <div class="w-1/4">
        <label
          for="password"
          class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900 dark:text-white"
          >비밀번호 확인</label
        >
      </div>
      <div class="w-3/4">
        <input
          type="password"
          id="passworConfirm"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          required
        />
      </div>
    </div>
    <div class="flex items-center mb-5">
      <div class="w-1/4">
        <label
          for="nickname"
          class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900 dark:text-white"
          >닉네임</label
        >
      </div>
      <div class="w-3/4">
        <input
          type="text"
          id="nickname"
          v-model="formData.nickname"
          class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          required
        />
      </div>
    </div>
    <div class="flex items-center w-full justify-center">
      <button
        @click="nextStep(1)"
        class="items-center text-white bg-theme-red hover:bg-theme-redbrown focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center"
      >
        다음
      </button>
    </div>
  </form>
</template>

<script setup>
import { ref } from 'vue';


const formData = ref({
  email : '',
  password : '',
  nickname : ''
})

const emit = defineEmits(['nextStep'])
const nextStep = (input) => {
    // 1. 다음 단계 이동
    emit('nextStep', input)

    // 2. 기본 user 정보 insert api 호출
    console.log(formData);
}
</script>

<style></style>
