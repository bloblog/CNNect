<template>
  <div class="flex items-center justify-center my-3">
    <h3>레벨테스트 페이지</h3>
  </div>
  <div class="flex items-center w-full justify-center">
    <button
      @click="nextStep(2)"
      class="items-center mx-3 text-white bg-gray-400 hover:bg-gray-500 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center"
    >
      그만두기
    </button>
    <button
      @click="registerComplete()"
      class="items-center text-white bg-theme-red hover:bg-theme-redbrown focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center"
    >
      완료
    </button>
  </div>
</template>

<script setup>
import {useRouter} from 'vue-router';

const router = useRouter()

const registerComplete = () => {
    alert("회원가입이 완료되었습니다!");
    router.push('/');
}

</script>

<style></style>
