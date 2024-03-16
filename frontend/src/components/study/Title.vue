<script setup>
defineProps({
    videoData: Object
})
</script>

<template>
    <header>
        <div class="title">{{ videoData.videoName }}</div>
        <div class="level">Lv.{{ videoData.level }}</div>
    </header>
</template>

<style scoped>
header {
    position: sticky;
    top: 0;
    width: 100%;
    height: 4rem;
    background-color: #2d2d2d;
    color: #fff;
    z-index: 2;
    display: flex;
    line-height: 4rem;
    justify-content: space-between;
    padding: 0 15px;
}
.title {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 20px;
    font-weight: 800;
    padding: 0 10px;
}
.level {
    font-size: 18px;
    font-weight: 600;
    padding: 0 10px;
}
</style>