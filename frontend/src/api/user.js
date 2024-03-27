import { localAxios } from "./http";

const local = localAxios();
const url = "/user";
const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
} // 헤더에 accessToken 담아서 전송하기!!

function registUser(param, success, fail){
    local.post(`${url}/join`, JSON.stringify(param)).then(success).catch(fail);
}

function loginUser(param, success, fail){
    local.post(`${url}/login`, JSON.stringify(param)).then(success).catch(fail);
}

function emailCheck(param, success, fail){
    local.get(`${url}/check/${param}`).then(success).catch(fail);
}

function emailSend(param, success, fail){
    local.post(`${url}/email/send/${param}`).then(success).catch(fail);
}

function userInfo(success, fail){
    local.get(`${url}/mypage/info`, config).then(success).catch(fail);
}


export {
    registUser,
    loginUser,
    emailCheck,
    emailSend,
    userInfo
}