<script setup>
import { storeToRefs } from 'pinia'
import { useStudyStore } from '@/stores/studyStore.js'
const studyStore = useStudyStore()
const { curSentence } = storeToRefs(studyStore)

</script>

<template>
    <div class="shadowing">
        <div class="above-box">
            <div class="korean">{{ curSentence.mean }}</div>
            <div class="english">{{ curSentence.content }}</div>
        </div>
        <div class="below-box">
            <div class="below-left-box">
                <div class="listen">
                    <span class="material-symbols-outlined">
                        volume_up
                    </span>
                </div>
                <div class="speack">
                    <span class="material-symbols-outlined">
                        mic
                    </span>
                </div>
            </div>
            <div class="below-right-box">
                <div class="score"  :class="{ 'noScore': curSentence.score === null}">
                    {{ curSentence.score != null ?  curSentence.score : "도전"}}
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.shadowing {
    width: 100%;
    height: 100%;
    background-color: #ffffff;
    border: #0F1B4F 2px solid;
    border: 1px solid #CDCDCD;
    filter: drop-shadow(2px 2px 3px rgba(0, 0, 0, 0.25));
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
.shadowing .above-box {
    margin: 10px;
}
.shadowing .above-box .korean {
    color: #8A8A8A;
    padding: 15px;

}
.shadowing .above-box .english {
    color: #000000;
    font-weight: 600;
    font-size: 18px;
    padding: 15px;
}
.shadowing .below-box {
    border-top: #E3E3E3 solid 1px;
    height: 50px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.shadowing .below-left-box {
    display: flex;
}

.shadowing .below-left-box .listen span {
    box-sizing: border-box;
    padding: 10px 20px;
    height: 50px;
    border-right: #E3E3E3 1px solid;
}
.shadowing .below-left-box .speack {
    padding: 10px 20px;
    border-right: #E3E3E3 1px solid;

}
.shadowing .below-right-box .score {
    border: #c8c8c8 1px solid;
    border-radius: 20px;
    padding: 7px 20px;
    margin-right: 15px;
    color: #CC0000;
    font-weight: 600;
}
.noScore{
    color: #b3b3b3;
}
</style>