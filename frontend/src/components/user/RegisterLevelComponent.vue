<template>
  <div class="flex flex-col justify-center h-full w-full items-center">
    <h3 class="font-bold m-7 text-lg font-[GmarketSansMedium]">
      Q. 제시된 단어의 의미를 선택해주세요
    </h3>
    <RegisterLevelDetail :stage="currentStage" ref="registerLevelDetail" />

    <div class="flex items-center w-full justify-center mt-4">
      <button
        data-modal-target="stop-modal"
        data-modal-toggle="stop-modal"
        class="mx-3 text-white bg-gray-400 hover:bg-gray-500 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5"
      >
        그만두기
      </button>
      <div
        id="stop-modal"
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
                data-modal-hide="stop-modal"
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
            <div class="modal-body">
              <div class="flex justify-center items-center">
                <div class="p-4 md:p-5 text-center">
                  <div class="font-bold text-lg mb-1">
                    정말 그만두시겠습니까?
                  </div>
                  <div class="mb-5">종료 시 지금 레벨로 확정됩니다.</div>
                  <div class="mb-5">
                    <button
                      class="mr-4 text-gray-400 border-b-2"
                      data-modal-hide="stop-modal"
                    >
                      이어서하기
                    </button>
                    <button
                      class="ml-4 text-white bg-theme-red hover:bg-theme-redbrown focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5"
                      data-modal-hide="stop-modal"
                      @click="register()"
                    >
                      종료
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <button
        @click="nextLevel"
        class="text-white bg-theme-red hover:bg-theme-redbrown focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5"
      >
        {{ level < 6 ? "다음" : "제출" }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, computed } from "vue";
import { initFlowbite, Modal } from "flowbite";
import RegisterLevelDetail from "@/components/user/RegisterLevelDetailComponent.vue";

const steps = ref(["A1", "A2", "B1", "B2", "C1", "C2"]);
const level = ref(1);
const registerLevelDetail = ref(null);
const emit = defineEmits(["updateLevel", "finishRegister"]);

// 현재 레벨에 해당하는 stage 값을 반환하는 computed property
const currentStage = computed(() => {
  return steps.value[level.value - 1];
});

watch(level, (newValue) => {
  // 'updateLevel' 이벤트를 발생시키고, 새로운 level 값을 전달합니다.
  emit("updateLevel", newValue);
});

const finishRegister = () => {
  emit("finishRegister");
};

const nextLevel = () => {
  const wrongCount = registerLevelDetail.value.checkAnswers();

  if (wrongCount > 1) {
    finishRegister();
  } else {
    if (level.value == 6) {
      finishRegister();
    } else {
      level.value++;
    }
  }
};

onMounted(() => {
  initFlowbite();
});

// set the modal menu element
const $targetEl = document.getElementById("stop-modal");

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
  },
  onShow: () => {
  },
  onToggle: () => {
  },
};

// instance options object
const instanceOptions = {
  id: "stop-modal",
  override: false,
};

const modal = new Modal($targetEl, options, instanceOptions);

const register = () => {
  finishRegister();
};
</script>
