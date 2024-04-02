import axios from "axios";
import { localAxios } from "./http";
import { handleVideoClick } from "@/api/user.js";

const local = localAxios();
const config = {
  headers: {
    Authorization: "Bearer " + localStorage.getItem("accessToken"),
  },
};

const config2 = {
  headers: {
    Authorization: "Bearer ",
  },
};


const REST_DATA_API = `https://j10a507.p.ssafy.io/data/level`;

async function getUserLevelTestList(level, success, fail) {
  axios.get(`${REST_DATA_API}/user/${level}`).then(success).catch(fail);
}

function setUserLevelToken(param, success, fail) {
  console.log(param);
  local.patch(`user/update/level`, param, config)
    .then(() => {
      if (success) {
      handleVideoClick();
        success(); 
      }
    })
    .catch(fail);
}
function setUserLevelNotToken(param, success, fail) {
  local.patch(`user/update/level/register`, param)
  .then(() => {
    if (success) {
      handleVideoClick();
      success(); 
    }
  })
  .catch(fail);
}

function getQuiz(param, success, fail) {
  local.get(`${REST_DATA_API}/video/${param}`).then(success).catch(fail);
}

export { getQuiz, getUserLevelTestList, setUserLevelToken, setUserLevelNotToken };
