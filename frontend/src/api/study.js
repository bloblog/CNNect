import { localAxios } from "./http";

const local = localAxios();
const url = "/video/study";

const config = {
  headers : {
      "Authorization" : "Bearer " + localStorage.getItem("accessToken")
  }
}

function getStudy(videoId, success, fail) {
  local.get(`${url}/${videoId}`, config).then(success).catch(fail);
}

export { getStudy };