import { localAxios } from "./http";

const local = localAxios();
const url = "/voca";
const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
}


function getWordHistory(success, fail){
    local.get(`${url}`, config).then(success).catch(fail);
}

function addWordList(param, success, fail){
    local.post(`${url}`, JSON.stringify(param), config).then(success).catch(fail);
}

function deleteWordHistory(param, success, fail){
    local.delete(`${url}/${param}`).then(success).catch(fail);
}

export{
    getWordHistory,
    addWordList,
    deleteWordHistory
}