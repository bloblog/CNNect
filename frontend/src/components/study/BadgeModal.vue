<template>
    <!-- badge modal -->
    <div
        
        id="badge-modal"
        tabindex="-1"
        aria-hidden="true"
        class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full"
    >
        <div class="relative p-4 w-full max-w-md max-h-full">
            <!-- Modal content -->
            <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
            <!-- Modal header -->
            <div
                class="flex items-center justify-between rounded-t dark:border-gray-600"
            >
                <button
                type="button"
                class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                data-modal-hide="badge-modal"
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
                <div v-for="badge in badgeItem" class="flex">
                    <div>
                        {{ badge.badgeName }}
                    </div>
                </div>
                <div class="flex justify-center">
                    <button
                        @click="quit"
                        data-modal-hide="badge-modal"
                        type="button"
                        class="m-1 mb-5 text-white bg-theme-red hover:bg-theme-redbrown font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                    >
                        확인
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

const quit = () => {
    router.push("/");
}

</script>

<style scoped>

</style>