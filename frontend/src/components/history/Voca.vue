<template>
    <table>
        <tr v-for="wordData in currentPageWordList " :key="wordData.word">
            <th class="word"> {{ wordData.word }} </th>
            <th class="mean"> {{ wordData.mean }} </th>
        </tr>
    </table>
    <div>
      <button @click="previousPage" :disabled="currentPage === 0">이전</button>
      <span>Page {{ currentPage + 1 }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages-1">다음</button>
    </div>
</template>

<script setup>
    import {defineProps, ref ,computed } from 'vue';

    defineProps({
        history: Object
    })
    
    const sortedHistory = ref([]);

    // 이부분 이렇게가 맞나???
    sortedHistory.value = history.wordList.sort((a, b) => a.word.localeCompare(b.word));
    
    const currentPage = ref(0);
    const wordPerPage = 5;

    const totalPages = computed(() => Math.ceil(sortedHistory.length / wordPerPage));

    const currentPageWordList = computed(() => {
        const startIndex = currentPage.value * wordPerPage;
        return sortedHistory.slice(startIndex, startIndex + wordPerPage);
    });

    function nextPage() {
        if (currentPage.value < totalPages.value - 1) {
            currentPage.value++;
        }
    }

    function previousPage() {
        if (currentPage.value > 0) {
            currentPage.value--;
        }
    }


</script>

<style scoped>

</style>
