import axios from "axios";
import { localAxios } from "./http";

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
  local.patch(`user/update/level`, param, config).then(success).catch(fail);
}

function setUserLevelNotToken(param, success, fail) {
  local.patch(`user/update/level`, param, config2).then(success).catch(fail);
}

export { getUserLevelTestList, setUserLevelToken, setUserLevelNotToken };
