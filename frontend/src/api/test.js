import axios from "axios";
import { ref } from "vue";

// const REST_DATA_API = `https:/j10a507.p.ssafy.io/data/level`;
const REST_DATA_API = `http://localhost:5000/data/level`;

async function getUserLevelTestList(level, success, fail) {
  axios.get(`${REST_DATA_API}/user/${level}`).then(success).catch(fail);
}

export { getUserLevelTestList };
