import { localAxios, localAxios2 } from "./http";
const local = localAxios();
const local2 = localAxios2();
const url = "/user";
const urll = "/recommendation";
const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
}

function registUser(param, success, fail){
    local.post(`${url}/join`, JSON.stringify(param)).then(success).catch(fail);
}

function loginUser(param, success, fail){
    local.post(`${url}/login`, JSON.stringify(param)).then(success).catch(fail);
}

function emailCheck(param, success, fail){
    local.get(`${url}/check/${param}`).then(success).catch(fail);
}

function emailSend(param, success, fail){
    local.post(`${url}/email/send/${param}`).then(success).catch(fail);
}

function userInfo(success, fail){
    local.get(`${url}/mypage/info`, config).then(success).catch(fail);
}

async function handleVideoClick() {
    try {
        const accessToken = localStorage.getItem("accessToken");
        if (!accessToken) {
            throw new Error("로그인이 필요합니다.");
        }
        const response = await local2.get(`${urll}/script`, config);
        return response.data;
    } catch (error) {
        console.error(error);
        if (error.response) {
            // 서버 응답이 있을 경우
            console.error("서버 응답 상태 코드:", error.response.status);
            console.error("서버 응답 데이터:", error.response.data);
        } else if (error.request) {
            // 요청이 전송되었지만 응답이 없을 경우
            console.error("요청을 보냈지만 응답이 없습니다.");
        } else {
            // 오류가 발생한 경우
            console.error("오류가 발생했습니다:", error.message);
        }
        throw new Error('추천 뉴스를 중 오류가 발생했습니다.');
    }
}

export {
    registUser,
    loginUser,
    emailCheck,
    emailSend,
    userInfo,
    handleVideoClick
}