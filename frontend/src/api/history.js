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
}

function getLearningVideo(success, fail){
    local.get(`${url}/proceeding`,config).then(success).catch(fail);
}

function getCompletedVideo(success, fail){
    local.get(`${url}/done`,config).then(success).catch(fail);
}

function getLastVideo(success, fail){
    local.get(`${url}/lastvideo`,config).then(success).catch(fail);
}

function updateLastSentence(param, success, fail) {
    console.log(param);
    local.patch(`${url}`, param, config).then(success).catch(fail);
  }

function updateStatus(param, success, fail) {
    console.log(config)
    local.patch(`${url}/pass/${param}`, null , config).then(success).catch(fail);
}

export {
    insertVideoHistory,
    insertRegistHistory,
    getLearningVideo,
    getCompletedVideo,
    updateLastSentence,
    updateStatus,
}