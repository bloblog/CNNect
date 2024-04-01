<template>
    <div class="">
        <div class="word-list" v-if="currentPageWordList.length !== 0 ">
            <ul class="flex flex-col items-center min-h-[65vh]">
                <li v-for="wordData in currentPageWordList" :key="wordData.word" class ="item">
                    <div class="word-item">
                        <div class="word font-bold text-lg text-center"> {{ wordData.wordContent }} </div>
                        <div class="mean"> {{ wordData.wordMean }} </div>
                    </div>
                    <div class="del">
                        <svg xmlns="http://www.w3.org/2000/svg" @click="delWord(wordData.wordListId)" height="24" viewBox="0 -960 960 960" width="24"><path d="m336-280 144-144 144 144 56-56-144-144 144-144-56-56-144 144-144-144-56 56 144 144-144 144 56 56ZM480-80q-83 0-156-31.5T197-197q-54-54-85.5-127T80-480q0-83 31.5-156T197-763q54-54 127-85.5T480-880q83 0 156 31.5T763-763q54 54 85.5 127T880-480q0 83-31.5 156T763-197q-54 54-127 85.5T480-80Zm0-80q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z" paddin="10px" fill="#cc0000"/>
                        </svg>
                    </div>
                </li>
            </ul>
            <div class="page">
                <div>
                    <svg class="sysmbol-btn" xmlns="http://www.w3.org/2000/svg" @click="previousPage" :disabled="currentPage === 0" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
                        <path d="M400-80 0-480l400-400 71 71-329 329 329 329-71 71Z"/>
                    </svg>
                </div>
                <div class="page-container">
                    <span v-for="page in visiblePages" :key="page" @click="goToPage(page)" :class="{ active: currentPage === page - 1 }" class="page-item">{{ page }}</span>
                </div>
                <div>
                    <svg xmlns="http://www.w3.org/2000/svg" @click="nextPage" :disabled="currentPage === totalPages-1" height="24" viewBox="0 -960 960 960" width="24" fill="#CC0000">
                        <path d="m321-80-71-71 329-329-329-329 71-71 400 400L321-80Z"/>
                    </svg>
                </div>  
            </div>  
            <!-- 여백 -->
            <div class="h-3"></div>
        </div>
        <div v-else class="blank flex items-center justify-center font-bold text-lg">저장된 단어가 존재하지 않습니다.</div>
        
    </div>
    
</template>

<script setup>
    import { ref , computed , defineProps } from 'vue';
    import { deleteWordHistory } from '@/api/voca';

    const props = defineProps({
        wordHistory: Object
    })
    
    // const sortedHistory = ref([]);

    // sortedHistory.value = props.wordHistory.wordList;
    
    const currentPage = ref(0);
    const wordPerPage = 5;

    const totalPages = computed(() => Math.ceil(props.wordHistory.length / wordPerPage));

    const currentPageWordList = computed(() => {
        const startIndex = currentPage.value * wordPerPage;
        return props.wordHistory.slice(startIndex, startIndex + wordPerPage);
    });


    const visiblePages = computed(() => {
        const pages = [];
        const currentPageValue = currentPage.value + 1;

        const startPage = Math.max(1, currentPageValue - 2); // 현재 페이지를 중심으로 앞쪽에 최대 2개의 페이지를 보여줍니다.
        const endPage = Math.min(totalPages.value, currentPageValue + 2); // 현재 페이지를 중심으로 뒷쪽에 최대 2개의 페이지를 보여줍니다.
       

        if(currentPageValue === totalPages.value ){
            if(totalPages.value >= 5){
                pages.push(totalPages.value-4);
                pages.push(totalPages.value-3);
            }else if(totalPages.value===4){
                pages.push(totalPages.value-3);
            }
        }else if(currentPageValue === totalPages.value-1){
            if(totalPages.value >= 5){
                pages.push(totalPages.value-4);
            }
        }

        for (let i = startPage; i <= endPage; i++) {
            pages.push(i);
        }
        
        if(currentPageValue === 1 ){
            if(totalPages.value >= 5){
                pages.push(currentPageValue+3);
                pages.push(currentPageValue+4);
            }else if(totalPages.value===4){
                pages.push(currentPageValue+3);
            }
        }else if(currentPageValue === 2 ){
            if(totalPages.value >= 5){
                pages.push(currentPageValue+3);
            }
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
    const index = props.wordHistory.findIndex(item => item.wordListId === wordListId);
    if (index !== -1) {
        props.wordHistory.splice(index, 1); // 배열에서 삭제
        if (props.wordHistory.slice(currentPage.value * wordPerPage, currentPage.value * wordPerPage + wordPerPage).length === 0 && currentPage.value > 0) {
            currentPage.value--;
        }
    }
    deleteWordHistory(wordListId)
}


</script>

<style scoped>
.item{
    display: flex;
    flex-direction: row;
    width: 90%;
}
.word-item {
    border: 1px solid #e2e2e2;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    margin: 10px 0 10px 0px;
    width: 100%;
    display: flex;
}

.del{
    color: #CC0000;
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    margin : 15px;
    cursor: pointer;
}

.word {
    margin : 15px;
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
    position: relative;
    height: 80vh;
    min-height: fit-content;
    margin-top: 3vh;
    overflow-y: scroll;
}
.active{
    color: #CC0000;
}
.page-container{
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    text-align: center;
    min-width: 20vw;
}
.page{
    display: flex;
    justify-content: center; /* 수평 가운데 정렬 */
    align-items: center; /* 수직 가운데 정렬 */
    /* bottom: 8%; */
    left: 50%;
    width: 100%; /* 페이지 네비게이션의 너비를 조정할 수 있습니다. */
    background-color: #fff; /* 필요에 따라 배경색을 지정하세요 */
    padding-top: 2%;

}
.page-item {
  margin: 0 10px; 
  cursor: pointer;
}

.blank{
    height: 80vh;
    font-family: 'GmarketSansMedium';
}
</style>
