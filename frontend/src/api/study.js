import { localAxios } from "./http";

const local = localAxios();
const url = "/study";

const studyData = {
  videoId: "X_5IXAEebo4",
  videoName: "Mayorkas responds to Gov. Abbott's criticism of Biden's border actions",
  url: "https://www.youtube.com/embed/X_5IXAEebo4",
  date: "2024-03-11",
  level: 3,
  category: "정치",
  sentenceList: [
    { order: 1, startTime: "3", content: "hello", mean: "안녕하세요" , score: "7.1"},
    { order: 2, startTime: "9", content: "Good morning.", mean: "좋은아침입니다", score: "8.9"},
    { order: 3, startTime: "120", content: "Just over a year ago, I launched my campaign for president.", mean: "불과 1년 전, 저는 대통령 선거 캠페인을 시작했습니다.", score: null},
    { order: 4, startTime: "200", content: "Here with me now is Homeland  Security Secretary Alejandro Mayork.", mean: "저와 함께 여기 알레한드로 마요르카스 국토안보부 장관이 있습니다.", score: null},
    { order: 5, startTime: "300", content: "Here with me now is Homeland  Security Secretary Alejandro Mayork.", mean: "저와 함께 여기 알레한드로 마요르카스 국토안보부 장관이 있습니다.", score: null},
    { order: 6, startTime: "400", content: "Here with me now is Homeland  Security Secretary Alejandro Mayork.", mean: "저와 함께 여기 알레한드로 마요르카스 국토안보부 장관이 있습니다.", score: null},
    { order: 7, startTime: "500", content: "Here with me now is Homeland  Security Secretary Alejandro Mayork.", mean: "저와 함께 여기 알레한드로 마요르카스 국토안보부 장관이 있습니다.", score: null},
    { order: 8, startTime: "600", content: "Here with me now is Homeland  Security Secretary Alejandro Mayork.", mean: "저와 함께 여기 알레한드로 마요르카스 국토안보부 장관이 있습니다.", score: null},
  ],
  wordList: ["hello", "world", "my", "security", "homeland", "secretary"],
};

function getStudy(videoId, success, fail) {
  // 이곳에서 API를 호출하겠지만, 현재는 임시로 가상의 데이터를 사용합니다.
  // local.get(`${url}/${videoId}`).then(success).catch(fail);
  return studyData 
}

export { getStudy };