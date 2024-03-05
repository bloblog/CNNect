import axios from "axios";

const { VITE_VUE_API_URL } = import.meta.env;

// api 호출할 파일에서 아래 설정 후 호출
// import { localAxios } from "./http";
// const local = localAxios();
// const url = "/chat";

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  return instance;
}

export { localAxios };
