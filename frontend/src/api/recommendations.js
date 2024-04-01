import { localAxios } from "./http";
const local = localAxios();
const url = "/recommendations";
const config = {
  headers : {
      "Authorization" : "Bearer " + localStorage.getItem("accessToken")
  }
}
async function fetchRecommendations() {
    try {
      const accessToken = localStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("로그인이 필요합니다.");
      }
      
      const response = await local.get(`${url}/all`, config);

      return response.data.data;
      console.log(response.data.data);
    } catch (error) {
      console.error(error);
    }
    
  }

export { fetchRecommendations };
