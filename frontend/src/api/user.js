import { localAxios } from "./http";

const local = localAxios();
const url = "/user";

function registUser(param, success, fail){
    local.post(`${url}/join`, JSON.stringify(param)).then(success).catch(fail);
}

export {
    registUser
}