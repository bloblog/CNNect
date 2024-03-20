<script setup>
</script>

<template>
    <div class="popup" ref="popup">
        <div class="origin">
            <strong class="ENword">security</strong>
            <button type="button" class="add_wordbook">
                <span class="material-symbols-outlined">add</span>
            </button>
        </div>
        <ul class="mean_list">
            <li class="mean_item">
                <span class="num">1</span>.
                <p class="mean">
                    <span class="word_class">명사</span>
                    보안, 경비, 안보, 방위 (→high-security)
                </p>
            </li>
            <li class="mean_item">
                <span class="num">2</span>. 
                <p class="mean">
                    <span class="word_class">명사</span>
                    (큰 기업·조직의) 경비 담당 부서
                </p>
            </li>
            <li class="mean_item">
                <span class="num">3</span>.
                <p class="mean">
                    <span class="word_class">명사</span>
                    (미래를 위한) 보장
                </p>
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
    box-shadow: 0 0 5px rgba(145, 53, 53, 0.5);
    border-radius: 10px;
    background-color: #fffbfb;
    width: 400px;
}

.origin {
    display: flex;
    justify-content: space-between;
}
.origin .ENword {
    font-weight: 600;
    font-size: 16px;
    background: rgba(204, 0, 0, 0.15);
}
.origin button.add_wordbook {
    border-radius: 50%;
    background-color: #CC0000;
    color: #fff;
    margin-left: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 2px;
    box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.25);
}
.origin button.add_wordbook:hover {
    background-color: #a50000;
}
.origin button.close-button {
    color: #8e8e8e
}
.origin button.close-button:hover {
    color: #2a2a2a
}

.mean_list {
    font-size: 14px;
}
.mean_item .mean {
    display: inline;
    
}
.mean_item .mean .word_class {
    color: #6881a1;
}

</style>