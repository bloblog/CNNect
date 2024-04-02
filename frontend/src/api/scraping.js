import axios from "axios";
import cheerio from "cheerio";

const cache = {};

const getFromCache = (key) => {
    const item = cache[key];
    if (item && (item.expire > Date.now())) {
        return item.data;
    }
    return null;
};

const saveToCache = (key, data, ttl) => {
    const expire = Date.now() + ttl * 1000;
    cache[key] = { data, expire };
};

const getDict = async (searchWord, retryCount = 0, signal) => {
    try {
        const cachedData = getFromCache(searchWord);
        if (cachedData) {
            return cachedData;
        }

        const html = await axios.get(`/daum/search.do?q=${searchWord}`, { signal });
        let list = [];
        const $ = cheerio.load(html.data);

        $('ul.list_search').each(function() {
            const $prevDiv = $(this).prev('div');
            const $a = $prevDiv.find('strong > a');
            const aText = $a.clone().children().remove().end().text().trim();
            const spanText = $a.find('span').text().trim();

            if (aText === '' && aText !== spanText) {
                $(this).find('span.txt_search').each((i, element) => {
                    list.push({
                        searchedWord: spanText,
                        num: i + 1,
                        mean: $(element).text(),
                    });
                });
                return false;
            }
            
        });

        saveToCache(searchWord, list, 86400); // 캐시 만료 시간 1일

        return list.length > 0 ? list : null;

    } catch (error) {
        if (axios.isCancel(error)) {

        } else if (retryCount < 5) {
            return await getDict(searchWord, retryCount + 1, signal);
        } else {
            console.log("너냐");
            return null;
        }
    }
};

export { getDict }