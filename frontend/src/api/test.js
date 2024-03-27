import axios from "axios";
import { ref } from "vue";

const REST_DATA_API = `https:/j10a507.p.ssafy.io/data/level`;
const userLevelTestList = ref([]);

const getUserLevelTestList = async function (level) {
  try {
    const response = await axios.get(`${REST_DATA_API}/user/${level}`);
    userLevelTestList.value = response.data.data;
  } catch (error) {
    console.log(error);
  }
};

export { userLevelTestList, getUserLevelTestList };
