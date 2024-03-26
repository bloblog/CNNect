<template>
    <div class  = "word-list">
        <ul>
            <li v-if="currentPageWordList.length !== 0 " v-for="wordData in currentPageWordList " :key="wordData.word" class = "item">
                <div class="word-item">
                    <div class="word"> {{ wordData.word }} </div>
                    <div class="mean"> {{ wordData.mean }} </div>
                </div>
                <div class="del">
                    <span class="material-symbols-outlined" @click="delWord(wordData.wordListId)">
                        cancel
                    </span>
                </div>
            </li>
            <li v-else class="blank ">저장된 단어가 존재하지 않습니다.</li>
        </ul>       
    </div>
    <div class="page">
        <div>
            <span class="material-symbols-outlined" @click="previousPage" :disabled="currentPage === 0">
                arrow_back_ios
            </span>
        </div>
        <div class="page-container">
            <span v-for="page in visiblePages" :key="page" @click="goToPage(page)" :class="{ active: currentPage === page - 1 }" class="page-item">{{ page }}</span>
        </div>

        <div>
            <span class="material-symbols-outlined" @click="nextPage" :disabled="currentPage === totalPages-1">
                arrow_forward_ios
            </span>
        </div>  
    </div>
</template>

<script setup>
    import { ref , computed , defineProps } from 'vue';

    const props = defineProps({
        wordHistory: Object
    })
    
    // const sortedHistory = ref([]);

    // sortedHistory.value = props.wordHistory.wordList;
    
    const currentPage = ref(0);
    const wordPerPage = 5;

    const totalPages = computed(() => Math.ceil(props.wordHistory.wordList.length / wordPerPage));

    const currentPageWordList = computed(() => {
        const startIndex = currentPage.value * wordPerPage;
        return props.wordHistory.wordList.slice(startIndex, startIndex + wordPerPage);
    });


    const visiblePages = computed(() => {
        const pages = [];
        const currentPageValue = currentPage.value + 1;

        const startPage = Math.max(1, currentPageValue - 2); // 현재 페이지를 중심으로 앞쪽에 최대 2개의 페이지를 보여줍니다.
        const endPage = Math.min(totalPages.value, currentPageValue + 2); // 현재 페이지를 중심으로 뒷쪽에 최대 2개의 페이지를 보여줍니다.
       


        for (let i = startPage; i <= endPage; i++) {
            pages.push(i);
        }

        return pages;
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

    function goToPage(page) {
        currentPage.value = page - 1;
    }

function delWord(wordListId) {
    const index = props.wordHistory.wordList.findIndex(item => item.wordListId === wordListId);
    if (index !== -1) {
        props.wordHistory.wordList.splice(index, 1); // 배열에서 삭제
        if (props.wordHistory.wordList.slice(currentPage.value * wordPerPage, currentPage.value * wordPerPage + wordPerPage).length === 0 && currentPage.value > 0) {
            currentPage.value--;
        }
    }
}


</script>

<style scoped>
.item{
    display: flex;
    flex-direction: row;
}
.word-item {
    border: 1px solid #e2e2e2;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    margin: 15px;
    padding: 0 10px;
    width: 90%;
    display: flex;
    flex-direction: row;
}
.del{
    color: #CC0000;
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    margin : 10px;
    cursor: pointer;
}

.word {
    margin : 10px;
    color: #CC0000;
    font-weight: 600;
    width: 20%;
    border-right: #E3E3E3 1px solid;
}
.mean {
    margin : 10px;
    width: 80%;
}
.word-list{
    height: 80%;
    overflow-y: scroll;
}
.active{
    color: #CC0000;
}
.page-container{
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    text-align: center;
    width: 15%;
}
.page{
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    bottom: 8%;
    left: 50%;
    width: 100%; /* 페이지 네비게이션의 너비를 조정할 수 있습니다. */
    background-color: #fff; /* 필요에 따라 배경색을 지정하세요 */
    padding-top: 1%;

}
.page-item {
  margin: 0 10px; 
  cursor: pointer;
}
.blank{
    margin-top: 20%;
    color: #CC0000;
    text-align: center;
    font-size: 200%;
    font-style: bold;
}
</style>
