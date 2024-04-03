<template>
    <!-- Done Modal 내용 -->
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
                data-modal-hide="done-modal"
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
        <div class=" flex flex-col text-lg font-[GmarketSansMedium] items-center">
            <div class="pb-3">
                학습을 종료하시겠습니까?
            </div>
            <!-- Modal body -->
            <div class="flex flex-col items-center p-3">
                <div class="mb-3 text-lg">
                    학습을 종료하시겠습니까?
                </div>
                <div class="flex justify-center">
                    <button
                        @click="quit"
                        data-modal-hide="done-modal"
                        type="button"
                        class="m-1 mb-5 text-white bg-theme-red hover:bg-theme-redbrown font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                    >
                        종료
                    </button>
                    <button
                        data-modal-hide="done-modal"
                        type="button"
                        class="m-1 mb-5 text-white bg-gray-500 hover:bg-gray-600 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                    >
                        취소
                    </button>

                </div>
                
            </div>
        </div>
        </div>
    </div>
</template>

<script setup>

import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from "vue-router";

import { initFlowbite, Modal } from "flowbite";
import { updateStatus } from '@/api/history'
import { checkBadge } from '@/api/badge'

const props = defineProps({
    videoId: Number,
    categoryId: Number,
    answerCnt: Number,

})

const router = useRouter();

const badgeItem = ref([]);

const $targetEl = document.getElementById("badge-modal");


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

// instance options object
const instanceOptions = {
    id: "badge-modal",
    override: false,
};

const modal = new Modal($targetEl, options, instanceOptions); // 뱃지 획득 모달

const isBadge = ref(false);

const quit = () => {
    // 뱃지 획득 여부 체크
    checkBadge(
        props.categoryId,
        ({data}) => {
            if (data.data) {
                // 뱃지 데이터에 추가
                badgeItem.value = data.data;
                console.log(badgeItem.value)
                isBadge.value = true;
            }

            // pass 인 경우
            if (props.answerCnt >= 6) {
                // 학습 상태 변경
                // updateStatus(
                //     props.videoId,
                //     ({data}) => {
                //         console.log(data);
                //     },
                //     (error) => {
                //         console.log(error);
                //     }
                // )
            } else {
                // fail 시 반영안됨 안내해주기
            }

            router.push('/');

        },
        (error) => {
            console.log(error);
        }
    )
    
}

</script>

<style scoped>

</style>