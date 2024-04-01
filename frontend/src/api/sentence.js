import { localAxios } from "./http";

const local = localAxios();
const url = "/sentence";

const config = {
  headers : {
      "Authorization" : "Bearer " + localStorage.getItem("accessToken")
  }
}

function updateScore(param, success, fail) {
  console.log(param);
  local.patch(`${url}`, param, config).then(success).catch(fail);
}

export { updateScore };