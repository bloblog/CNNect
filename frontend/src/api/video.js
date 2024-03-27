import { localAxios } from "./http";

const local = localAxios();
const url = "/video";
const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
}

async function allVideo(success, fail){
    local.get(`${url}/all`).then(success).catch(fail);
}

async function catVideo(categoryId, success, fail){
    local.get(`${url}/category/${categoryId}`, config).then(success).catch(fail);
}

async function videoPaging(categoryId, pageNo, pageSize, success, fail){
    local.get(`${url}/entities?categoryId=${categoryId}&pageNo=${pageNo}&pageSize=${pageSize}`, config).then(success).catch(fail);
}

export {
    allVideo,
    catVideo,
    videoPaging,
}