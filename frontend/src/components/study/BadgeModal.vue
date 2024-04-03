<template>
    <!-- badge modal -->
    <div class="">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
        <!-- Modal header -->
        <div
            class="flex items-center justify-between rounded-t dark:border-gray-600"
        >
            <button
            type="button"
            class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
            @click="close"
            >
            <svg
                class="w-3 h-3"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 14 14"
            >
                <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                />
            </svg>
            <span class="sr-only">Close modal</span>
            </button>
        </div>
        <!-- Modal body -->
        <div class="flex flex-col items-center">
            <div v-for="badge in badgeList" class="flex flex-col items-center">
                <div 
                    class="rotate"
                    >
                    <img :src="getUrl(badge)">
                </div>
                <div class="text-lg font-[GmarketSansMedium] p-3">
                    <span :class="{
                        'highlight-bronze':
                            getTier(badge) == 1,
                        'hightlight-sliver':
                            getTier(badge) == 2,
                        'highlight-gold':
                            getTier(badge) == 3,
                        }">
                    "{{ badge.badgeName }}"</span> 뱃지를 획득하셨습니다!
                </div>
            </div>
            <div class="flex justify-center">
                <button
                    @click="quit"
                    data-modal-hide="badge-modal"
                    type="button"
                    class="m-1 mb-5 text-white bg-gray-600 hover:bg-gray-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                >
                    메인으로
                </button>
                <button
                    @click="goToMypage"
                    data-modal-hide="badge-modal"
                    type="button"
                    class="m-1 mb-5 text-white bg-theme-red hover:bg-theme-redbrown font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                >
                    뱃지 확인하기
                </button>

            </div>
            
        </div>
        </div>
    </div>
        
</template>

<script setup>

import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
    badgeItem: Array,
})
const badgeList = ref(props.badgeItem);

watch(() => props.badgeItem, (newValue) => {
    badgeList.value = newValue;
  console.log("modal = " + badgeList.value)
});

const close = () => {
    const badgeModalElement = document.getElementById("badge-modal");
    const badgeModalBackElement = document.getElementById("badge-modal-backdrop");
    badgeModalElement.classList.add("hidden");
    badgeModalBackElement.classList.add("hidden");
    router.replace('/');
}


const options = {
    placement: "bottom-right",
    backdrop: "dynamic",
    backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
    closable: true,
    onHide: () => {
        console.log("modal is hidden");
    },
    onShow: () => {
        console.log("modal is shown");
    },
    onToggle: () => {
        console.log("modal has been toggled");
    },
};



const quit = () => {
    router.push("/");
}

const goToMypage = () => {
    router.push('/mypage')
}

const getUrl = (badge) => {
    const url = badge.badgeCondition == "VIDEO"
            ? "/badge/" + badge.badgeCondition + "/" + badge.category.categoryName + ".png"
            : "/badge/" + badge.badgeCondition + "/" + "speaking.png";
    return url;
}

const getTier = (badge) => {
    const tier = 
        badge.badgeId % 3 == 1
        ? 1
        : badge.badgeId % 3 == 2
        ? 2
        : 3;
    return tier;
}

</script>

<style scoped>
.rotate {
  animation: spin 2s linear; /* 애니메이션 적용 */
}

@keyframes spin {
  0% { transform: rotateY(0deg); } /* 시작 지점 */
  100% { transform: rotateY(360deg); } /* 끝 지점 */
}

.highlight-bronze {
    display: inline;
    box-shadow: inset 0 -10px 0 #e58d3579;
}

.highlight-sliver {
    display: inline;
    box-shadow: inset 0 -10px 0 #c6c3b972;
}

.highlight-gold {
    display: inline;
    box-shadow: inset 0 -10px 0 #f9d85375;
}

</style>