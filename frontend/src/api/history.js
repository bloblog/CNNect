import { localAxios } from "./http";

const local = localAxios();
const url = "/history";
const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
}


function insertRegistHistory(param, success, fail){
    local.post(`${url}`, JSON.stringify(param)).then(success).catch(fail);
}

function insertVideoHistory(param, success, fail){
    local.post(`${url}`, JSON.stringify(param) ,config).then(success).catch(fail);
    // return videoHistory
}

function getLearningVideo(success, fail){
    local.get(`${url}/proceeding`,config).then(success).catch(fail);
    // return learningVideoList
}

function getCompletedVideo(success, fail){
    local.get(`${url}/done`,config).then(success).catch(fail);
    // return completedVideoList
}

function updateLastSentence(param, success, fail) {
    console.log(param);
    local.patch(`${url}`, param, config).then(success).catch(fail);
  }

export {
    insertVideoHistory,
    insertRegistHistory,
    getLearningVideo,
    getCompletedVideo,
    updateLastSentence
}