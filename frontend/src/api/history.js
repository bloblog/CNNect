import { localAxios } from "./http";

const local = localAxios();
const url = "/history";

const videoHistory ={
    learningVideoList: [
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
    completionVideoList: [
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

function insertRegistHistory(param, success, fail){
    local.post(`${url}`, JSON.stringify(param)).then(success).catch(fail);
}

function insertVideoHistory(param, success, fail){
    // local.post(`${url}`, JSON.stringify(param)).then(success).catch(fail);
    return videoHistory
}

function insertWordHistory(param, success, fail){
    // local.post(`${url}`, JSON.stringify(param)).then(success).catch(fail);
    return wordhistory
}

export {
    insertVideoHistory,
    insertWordHistory,
    insertRegistHistory
}