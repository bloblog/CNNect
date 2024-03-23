<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

defineProps({
    selectedText: String,
    selectedWordMeanings: Object
})

const popup = ref(null);
const emit = defineEmits(['closePopup'])

const checkOutsideClick = (e) => {
    if (!popup.value.contains(e.target)) {
        emit('closePopup')
    }
};

onMounted(() => {
    document.addEventListener('mouseup', checkOutsideClick);
});

onUnmounted(() => {
    document.removeEventListener('mouseup', checkOutsideClick);
});
</script>

<template>
    <div class="popup" ref="popup">
        <div class="origin">
            <strong class="ENword">{{ selectedText }}</strong>
            <button type="button" class="add_wordbook">
                <span class="material-symbols-outlined">add</span>
            </button>
        </div>
        <div class="loader-container" v-if="selectedWordMeanings.length === 0">
            <div class="loader"></div>
        </div>
        <ul class="mean_list" >
            <li class="mean_item" v-for="meaning in selectedWordMeanings" :key="meaning.num">
                <span class="num">{{ meaning.num }}. </span>
                <p class="mean">{{ meaning.mean }}</p>
            </li>
        </ul>
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
    color: #cc0000;
    margin-left: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.origin button.add_wordbook:hover {
    background-color: #cc0000;
    color: #fff;
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
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
</style>