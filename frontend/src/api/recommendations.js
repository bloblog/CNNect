import { localAxios } from "./http";
import { ref } from "vue";
const local = localAxios();
const url = "/recommendations";

async function fetchRecommendations() {
    try {
      const accessToken = localStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("로그인이 필요합니다.");
      }
      
      const response = await local.get(`${url}/all`, {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      });
  
      console.log("비디오리스트", response.data);
      return response.data.data;
    } catch (error) {
      console.error(error);
    }
    
  }

export { fetchRecommendations };
