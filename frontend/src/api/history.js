import { localAxios } from "./http";

const local = localAxios();
const url = "/history";

function insertRegistHistory(param, success, fail){
    local.post(`${url}`, JSON.stringify(param)).then(success).catch(fail);
}

export {
    insertRegistHistory
}