<template>
  <div class="flex flex-col items-center m-5">
      <ul class="flex flex-wrap text-center text-gray-500 dark:text-gray-400">
        <li v-for="(tab, index) in tabs" :key="index" class="me-2 font-[GmarketSansMedium]">
          <a href="#" @click="changeTab(index)"
            :class="['inline-block px-2 py-2', index === activeTab ? 'text-theme-red font-bold active border-b-4 border-theme-red' : 'hover:text-gray-900 hover:bg-gray-100']">
            {{ tab }}
          </a>
        </li>
      </ul>
      <div>
        <categoryVideoList :category='activeTab'/>
      </div>

  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";

import categoryVideoList from '@/components/common/CategoryVideoList.vue'

const route = useRoute();
const router = useRouter();
const searchInput = ref("");

const tabs = ref(['정치', '비즈니스', '스포츠', '스타일', '엔터테인먼트', '건강']);
const activeTab = ref(0);

const changeTab = (index) => {
  activeTab.value = index;
};



</script>

<style scoped>

</style>
