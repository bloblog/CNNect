import { localAxios } from "./http";

const local = localAxios();
const url = "/voca";
const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
}


const wordhistory ={
    wordList: [
        {wordListId: 1, word: "apple", mean:"사과"},
        {wordListId: 2, word: "apple", mean:"사과"},
        {wordListId: 3, word: "apple", mean:"사과"},
        {wordListId: 4, word: "apple", mean:"사과"},
        {wordListId: 5, word: "apple", mean:"사과"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"배"},
        {wordListId: 7, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 8, word: "pear", mean:"배"},
        {wordListId: 9, word: "pear", mean:"배"},
        {wordListId: 10, word: "pear", mean:"배"},
        {wordListId: 6, word: "pear", mean:"89"},
        {wordListId: 7, word: "pear", mean:"90"},
        {wordListId: 8, word: "pear", mean:"91"},
        {wordListId: 9, word: "pear", mean:"92"},
        {wordListId: 8, word: "pear", mean:"93"},
        {wordListId: 9, word: "pear", mean:"94"},
        {wordListId: 95, word: "pear", mean:"95"},
        {wordListId: 96, word: "pear", mean:"96"},
        {wordListId: 97, word: "pear", mean:"97"},
        {wordListId: 98, word: "pear", mean:"98"},
        {wordListId: 99, word: "pear", mean:"99"},
        {wordListId: 100, word: "pear", mean:"배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배배"},

    ]
}


function getWordHistory(success, fail){
    // local.get(`${url}`).then(success).catch(fail);
    return wordhistory
}
function deleteWordHistory(param, success, fail){
    local.delete(`${url}/${param}`).then(success).catch(fail);
}

export{
    getWordHistory,
    deleteWordHistory
}