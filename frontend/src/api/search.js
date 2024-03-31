import { localAxios } from "./http";

const local = localAxios()
const url = "/search"

function searchByTitle(param, success, fail){
    local.get(`${url}/title?keyword=${param}`).then(success).catch(fail);
}

function searchByScript(param, success, fail){
    local.get(`${url}/script?keyword=${param}`).then(success).catch(fail);
}

export {
    searchByTitle,
    searchByScript
}