<template>
  <div class="header-frame top-0 z-20 sticky flex">
    <RouterLink to="/"><span class="flex items-start mx-2 my-7 text-white">Home</span></RouterLink>
    <RouterLink to="/history"><span class="flex items-start mx-2 my-7 text-white">학습기록</span></RouterLink>
    <RouterLink to="/study"><span class="flex items-start mx-2 my-7 text-white">학습진행</span></RouterLink>
    <RouterLink to="/user"><span class="flex items-start mx-2 my-7 text-white">마이페이지</span></RouterLink>
    <RouterLink to="/level"><span class="flex items-start mx-2 my-7 text-white">레벨테스트 </span></RouterLink>
  </div>
</template>

<script setup></script>

<style scoped>
.header-frame {
  @apply h-20 bg-black shadow-md;
}
</style>
