import { localAxios } from "./http";

const local = localAxios();
const url = "/user";

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

export {
    registUser,
    loginUser,
    emailCheck,
    emailSend
}