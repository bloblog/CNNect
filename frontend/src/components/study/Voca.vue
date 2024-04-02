<script setup>
import { addWordList } from "@/api/voca";
import Swal from "sweetalert2";

const msg = Swal.mixin({
  position: "center",
  showConfirmButton: true,
  confirmButtonText: "확인",
  backdrop: true,
}); // alert창 기본틀

defineProps({
  isFinishedFetching: Boolean,
  wordMeanings: Object,
});

async function addWordbook(meanings, word) {
  const wordContent = word;
  const wordMean = meanings
    .map((meaning) => meaning.num + ". " + meaning.mean)
    .join("\n");

  try {
    addWordList(
      { wordContent: wordContent, wordMean: wordMean },
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
    Swal.fire({
      icon: "success",
      title: "단어장에 추가되었습니다.",
      text: `추가된 단어 : ${wordContent}`,
    });
    // alert(`${wordContent}가 단어장에 추가되었습니다.`);
  } catch (error) {
    console.error("단어 추가에 실패하였습니다.", error);
  }
}
</script>

<template>
  <div>
    <ul class="card_list">
      <li class="card" v-for="(meanings, word) in wordMeanings" :key="word">
        <div class="word-and-btn">
          <div class="word">{{ word }}</div>
          <button
            type="button"
            class="add_wordbook"
            @click="addWordbook(meanings, word)"
          >
            <!-- 추가 아이콘 -->
            <svg
              xmlns="http://www.w3.org/2000/svg"
              height="24"
              viewBox="0 -960 960 960"
              width="24"
            >
              <path
                d="M440-440H200v-80h240v-240h80v240h240v80H520v240h-80v-240Z"
              />
            </svg>
          </button>
        </div>
        <ul class="mean_list" v-if="meanings">
          <li class="mean_item" v-for="(meaning, idx) in meanings" :key="idx">
            <span class="num">{{ meaning.num }}. </span>
            <p class="mean">{{ meaning.mean }}</p>
          </li>
        </ul>
      </li>
    </ul>
    <div class="loader-container" v-if="!isFinishedFetching">
      <div class="loader"></div>
    </div>
    <div
      class="notConnected"
      v-if="isFinishedFetching && Object.keys(wordMeanings).length === 0"
    >
      <span class="material-symbols-outlined">error</span>
      <div class="failText">단어장 조회 실패</div>
    </div>
  </div>
</template>

<style scoped>
li.card {
  padding: 5px 15px;
  border-bottom: 1px solid #e2e2e2;
  opacity: 0;
  transform: translateY();
  opacity: 0;
  transform: translateY(20px);
  animation: cardAppear 0.5s forwards ease-out;
}
@keyframes cardAppear {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
.word-and-btn {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
button.add_wordbook {
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
  padding: 0 1px;
}
button.add_wordbook:hover {
  background-color: #cc0000;
  fill: #fff;
}
.word {
  color: #cc0000;
  font-size: 17px;
  font-weight: 600;
}
.mean_list {
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
  margin: 30px 0;
}
.loader {
  border: 5px solid #f3f3f3;
  border-top: 5px solid #cc0000;
  border-radius: 50%;
  width: 40px;
  height: 40px;
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
  margin: 20px;
}
.notConnected span {
  font-size: 30px;
  color: #cc0000;
}
.failText {
  margin: 10px;
}
</style>
