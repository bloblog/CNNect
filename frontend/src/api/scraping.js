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

const getDict = async (searchWord, retryCount = 0) => {
    try {
        const cachedData = getFromCache(searchWord);
        if (cachedData) {
            return cachedData;
        }

        const html = await axios.get(`/daum/search.do?q=${searchWord}`);
        let list = [];
        const $ = cheerio.load(html.data);
        const elements = $("ul.list_search").first().find("span.txt_search");
        elements.each((i, element) => {
            list[i] = {
                num: i + 1,
                mean: $(element).text(),
            };
        });

        saveToCache(searchWord, list, 86400); // 캐시 만료 시간 1일

        return list;

    } catch (error) {
        if (retryCount < 3) {
            return await getDict(searchWord, retryCount + 1);
        } else {
            return null;
        }
    }
};

export { getDict }