package me.right42.wordcloud.collector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NaverNewsCollector {

    private static final String NAVER_NEWS_URI = "https://news.naver.com/main/ranking/popularDay.nhn";

    public String getTitles() throws IOException {
        Document document = getDocument();
        StringBuilder text = new StringBuilder();

        Elements titleTags = document.select(".content .ranking_section li dt a");
        for (Element titleTag : titleTags) {
            text.append(titleTag.attr("title"));
        }

        return text.toString();
    }

    private Document getDocument() throws IOException {
        return Jsoup.connect(NAVER_NEWS_URI).get();
    }

}
