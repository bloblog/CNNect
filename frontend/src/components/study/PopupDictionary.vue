<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { addWordList } from "@/api/voca";
import Swal from "sweetalert2";

const msg = Swal.mixin({
  position: "center",
  showConfirmButton: true,
  confirmButtonText: "확인",
  backdrop: true,
}); // alert창 기본틀

const props = defineProps({
  selectedText: String,
  selectedWordMeanings: Object,
  isFinishedFetchingPopup: Boolean,
});

const popup = ref(null);
const emit = defineEmits(["closePopup"]);

const checkOutsideClick = (e) => {
  if (!popup.value.contains(e.target)) {
    emit("closePopup");
  }
};

async function addWordbook(meanings, word) {
  const wordContent = word;
  const wordMean = meanings
    .map((meaning) => meaning.num + ". " + meaning.mean)
    .join("\n");

  addWordList(
    { wordContent: wordContent, wordMean: wordMean },
    (response) => {
      // console.log("response::", response.data);
      Swal.fire({
        icon: "success",
        title: `${wordContent} 추가 완료`,
        text: "내 단어장에 추가되었습니다.",
      });
    },
    (error) => {
      console.log(error);
    }
  );
}

onMounted(() => {
  document.addEventListener("mouseup", checkOutsideClick);
});

onUnmounted(() => {
  document.removeEventListener("mouseup", checkOutsideClick);
});
</script>

<template>
  <div class="popup" ref="popup">
    <div class="loader-container" v-if="!props.isFinishedFetchingPopup">
      <div class="loader"></div>
    </div>
    <div v-else>
      <div class="origin">
        <strong class="ENword" v-if="props.selectedWordMeanings.length > 0">
          {{ props.selectedWordMeanings[0].searchedWord }}
        </strong>
        <button
          type="button"
          class="add_wordbook"
          v-if="props.selectedWordMeanings.length > 0"
          @click="addWordbook(props.selectedWordMeanings, props.selectedWordMeanings[0].searchedWord)"
        >
          <!-- 추가 아이콘 -->
          <svg
            xmlns="http://www.w3.org/2000/svg"
            height="24"
            viewBox="0 -960 960 960"
            width="24"
          >
            <path d="M440-440H200v-80h240v-240h80v240h240v80H520v240h-80v-240Z" />
          </svg>
        </button>
      </div>
      <ul class="mean_list">
        <li
          class="mean_item"
          v-for="meaning in props.selectedWordMeanings"
          :key="meaning.num"
        >
          <span class="num">{{ meaning.num }}. </span>
          <p class="mean">{{ meaning.mean }}</p>
        </li>
      </ul>
      <div
        class="notConnected"
        v-if="
          props.isFinishedFetchingPopup && props.selectedWordMeanings.length === 0
        "
      >
        <!-- 오류 아이콘 -->
        <svg
          xmlns="http://www.w3.org/2000/svg"
          height="30"
          viewBox="0 -960 960 960"
          width="30"
        >
          <path
            d="M480-280q17 0 28.5-11.5T520-320q0-17-11.5-28.5T480-360q-17 0-28.5 11.5T440-320q0 17 11.5 28.5T480-280Zm-40-160h80v-240h-80v240Zm40 360q-83 0-156-31.5T197-197q-54-54-85.5-127T80-480q0-83 31.5-156T197-763q54-54 127-85.5T480-880q83 0 156 31.5T763-763q54 54 85.5 127T880-480q0 83-31.5 156T763-197q-54 54-127 85.5T480-80Zm0-80q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"
          />
        </svg>
        <div class="failText">검색 결과가 없습니다.</div>
      </div>
    </div>
    
  </div>
  <ul class="mean_list">
    <li
      class="mean_item"
      v-for="meaning in props.selectedWordMeanings"
      :key="meaning.num"
    >
      <span class="num">{{ meaning.num }}. </span>
      <p class="mean">{{ meaning.mean }}</p>
    </li>
  </ul>
  <div
    class="notConnected"
    v-if="
      props.isFinishedFetchingPopup && props.selectedWordMeanings.length === 0
    "
  >
    <span class="material-symbols-outlined">error</span>
    <div class="failText">검색 결과가 없습니다.</div>
  </div>
</template>

<style scoped>
.popup {
  margin-top: 10px;
  position: absolute;
  padding: 10px 15px;
  line-height: 25px;
  border-radius: 10px;
  border: rgb(232, 209, 209) 1px solid;
  box-shadow: 0 0 10px rgba(217, 105, 105, 0.3);
  background-color: #ffefef;
  width: 400px;
}

.origin {
  display: flex;
  justify-content: space-between;
}
.origin .ENword {
  font-weight: 600;
  font-size: 18px;
  color: #cc0000;
}
.origin button.add_wordbook {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 25px;
  width: 25px;
  border-radius: 50%;
  border: #cc0000 solid 1px;
  fill: #cc0000;
  margin-left: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.origin button.add_wordbook:hover {
  background-color: #cc0000;
  fill: #fff;
}

.mean_list {
  margin-top: 5px;
  font-size: 15px;
  display: flex;
  flex-wrap: wrap;
  color: #281212;
}
.mean_list .mean_item .mean {
  display: inline;
  margin-right: 15px;
}
.loader-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 15px 0;
}
.loader {
  border: 5px solid #ffd5d5;
  border-top: 5px solid #cc0000;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  animation: spin 2s linear infinite;
}
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
.notConnected {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  fill: #cc0000;
}
.failText {
  margin: 5px;
  font-size: 16px;
  font-weight: 400;
}
</style>
