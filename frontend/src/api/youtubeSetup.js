let apiReady = false;

function loadYouTubeIframeAPI() {
    if (window.YT) return; // API가 이미 로드된 경우 중복 로드 방지
    let tag = document.createElement('script');
    tag.src = "https://www.youtube.com/iframe_api";
    let firstScriptTag = document.getElementsByTagName('script')[0];
    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

    window.onYouTubeIframeAPIReady = function() {
        apiReady = true;
        document.dispatchEvent(new CustomEvent('YouTubeAPIReady'));
    };
}

export { loadYouTubeIframeAPI, apiReady };