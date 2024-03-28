import { localAxios } from "./http";

const local = localAxios();
const url = "/history";
const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
}

const learningVideoList ={
    learningVideoHistory: [
        {order: 1, videoTitle: "start", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 2, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 3, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 4, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 5, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 6, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},  
        {order: 7, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 8, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 9, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 10, videoTitle: "end", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32}
    ],

}

const completedVideoList = {
    completedVideoHistory: [
        {order: 11, videoTitle: "He said Russia's wardningnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnHe said Russia's wardningnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 12, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 13, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 14, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 15, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 16, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 17, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 18, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 19, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32},
        {order: 20, videoTitle: "Here Russia's warning after Macron said Western", videoUrl: "zsBZ_WEIuJ4",  lastSentence: "He said Russia's wardning", videoLevel: 3, completedSentenceNum: 10, totalSentenceNum: 32}
    ]
}

function insertRegistHistory(param, success, fail){
    local.post(`${url}`, JSON.stringify(param)).then(success).catch(fail);
}

function insertVideoHistory(param, success, fail){
    // local.post(`${url}`, JSON.stringify(param)).then(success).catch(fail);
    return videoHistory
}

function getLearningVideo(success, fail){
    local.get(`${url}/proceeding`,config).then(success).catch(fail);
    // return learningVideoList
}

function getCompletedVideo(param, success, fail){
    // local.get(`${url}`, JSON.stringify(param)).then(success).catch(fail);
    return completedVideoList
}

export {
    insertVideoHistory,
    insertRegistHistory,
    getLearningVideo,
    getCompletedVideo
}