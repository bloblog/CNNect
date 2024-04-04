import axios from "axios"

const { VITE_VUE_API_URL, VITE_VUE_API_URL2 } = import.meta.env;

// api 호출할 파일에서 아래 설정 후 호출
// import { localAxios } from "./http";
// const local = localAxios();
// const url = "/chat";

// local vue api axios instance
function localAxios() {
  // console.log(VITE_VUE_API_URL);
  // console.log(import.meta.env);
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  instance.interceptors.response.use(
    function (response) {   
      return response
    },
    async (error) => {
      const {
        config,
        response: { status }
      } = error

      if (status === 401) {
        const originalRequest = config
        const refreshToken = localStorage.getItem('refreshToken')

        if (refreshToken) {
          await instance
            .get('/user/refreshtoken', {
              refreshToken: localStorage.getItem('refreshToken')
            })
            .then((response) => {
              const newAccessToken = response.data.accessToken
              localStorage.setItem('accessToken', newAccessToken)
              localStorage.setItem('refreshToken', response.data.refreshToken)

              return instance(originalRequest)
            })
        }
      }
    }
  )
  return instance;
}

function localAxios2() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL2,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { localAxios, localAxios2 };
