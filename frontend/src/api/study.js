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
    { order: 1, startTime: "2", content: "2 Their tactic to win in let's see if that takes e. kristen holin ida.", score: "7.1"},
    { order: 2, startTime: "4", content: "4 Thank you veryen tigtrumon the friction betweenm.s to our chiee up on capitol hill, monto, tellt and how it actuaca well, mitch m dicater would ultimately endorse therep a onald e have not sposi 2020 it tlege certa's ch mcconnell clared joe b t wi t spoken sincehapoint in the fo acquit him in his floor of this te, said attacks that, ultimately y re than basemcc y,omin rt , elaine chao, a taiwanese an l w suppories now, many are now owo with faat you cle ltwife repeatedly?.", score: "8.9"},
    { order: 3, startTime: "6", content: "6 Fe2021, y afr the attack ol,there's some more questiond sa i wou e nominee for presiden it were the former and he obv heing to be the mir part thoughf loor in th aftermath of jry >>pracand morally for responsi' olutely evhio this represents wolf repup the past, we'just ut all ohideputies on his leadee republicans, were slow to rparts in thushind trump.", score: null},
    { order: 4, startTime: "10", content: "10 Buno gav you can see from it.", score: null},
    { order: 5, startTime: "20", content: "Mitch mcconnell's , but , ti hspokenpite that announcement that cameayti, sti up on capitol hill.", score: null},
    { order: 6, startTime: "40", content: "Thank you very not a presidentden and his pocas rematch with donald trump ishe union tomorrownn senior whioupo story for us mj.", score: null},
    { order: 7, startTime: "60", content: "How ithpresidte de just ahead of his critical t lastg t retest, ris oiaof the gera mpaign.", score: null},
    { order: 8, startTime: "80", content: "That means that everything that d crgoino be with an eye towards nly broa bideoa going forward.", score: null},
  ],
  wordList: ["qwerqwer", "veryen", "capitol", "friction", "security", "homeland", "secretary"],
};

function getStudy(videoId, success, fail) {
  // 이곳에서 API를 호출하겠지만, 현재는 임시로 가상의 데이터를 사용합니다.
  // local.get(`${url}/${videoId}`).then(success).catch(fail);
  return studyData 
}

export { getStudy };