<template>
    <div>
      <div class="flex justify-between items-center">
        <button @click="prevPage" id="prevButton" class="text-gray-500 focus:outline-none">이전</button>
        <div class="overflow-hidden flex">
          <div class="grid-cols-3 flex space-x-4 transition-transform duration-300" ref="imageContainer">
            <div v-for="(image, index) in items" :key="index" class="w-80 h-64 bg-gray-300">
              {{ image }}
            </div>
          </div>
        </div>
        <button @click="nextPage" id="nextButton" class="text-gray-500 focus:outline-none">다음</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, nextTick } from 'vue';
  
  const currentIndex = ref(0);
  const items = ref([1, 2, 3, 4, 5]); // 이미지 리스트 아이템들
  const imageContainer = ref(null); // ref를 사용하여 DOM 요소에 접근할 변수 선언
  
  const prevPage = () => {
    currentIndex.value = Math.max(currentIndex.value - 1, 0);
    console.log(currentIndex.value)
    updateImagePosition();
  };
  
  const nextPage = () => {      
    const maxIndex = items.value.length - 3;
    currentIndex.value = Math.min(currentIndex.value + 1, maxIndex);
    console.log(currentIndex.value)
    updateImagePosition();
  };
  
  const updateImagePosition = () => {
    nextTick(() => {
        const unitWidth = imageContainer.value.children[0].offsetWidth;
        console.log(unitWidth)
        const newPosition = -currentIndex.value * unitWidth;
        imageContainer.value.style.transform = `translateX(${newPosition}px)`;
    });
  };
  
  onMounted(() => {
    updateImagePosition();
  });
  </script>
  
  <style scoped>
  </style>
  