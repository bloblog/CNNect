import { localAxios } from "./http";

const local = localAxios();
const url = "/badge";
const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
} // 헤더에 accessToken 담아서 전송하기!!

async function allBadges(success, fail){
    local.get(`${url}/alllist`).then(success).catch(fail);
}

async function myBadges(success, fail){
    local.get(`${url}/mylist`, config).then(success).catch(fail);
}

async function checkBadge(param, success, fail){
    local.get(`${url}/newbadge?categoryId=${param}`, config).then(success).catch(fail);
}

export {
    allBadges,
    myBadges,
    checkBadge,
}