import axios from "axios";
import cheerio from "cheerio";

const getDict = async (searchWord, retryCount = 0) => {
    try {
        const html = await axios.get(`http://cors-anywhere.herokuapp.com/https://dic.daum.net/search.do?q=${searchWord}`);
        let list = [];
        const $ = cheerio.load(html.data);
        const elements = $("ul.list_search").first().find("span.txt_search");
        elements.each((i, element) => {
            list[i] = {
                num: i + 1,
                mean: $(element).text(),
            };
        });
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