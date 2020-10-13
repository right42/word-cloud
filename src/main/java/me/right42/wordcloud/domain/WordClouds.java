package me.right42.wordcloud.domain;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WordClouds {
    private final Map<String, Integer> wordCloud = new HashMap<>();

    public static WordClouds creat(Iterable<? extends String> texts) {
        WordClouds wordCloud = new WordClouds();

        for (String text : texts) {
            wordCloud.put(text);
        }

        return wordCloud;
    }

    public void put(String word) {
        if(wordCloud.containsKey(word)){
            Integer length = wordCloud.get(word);
            wordCloud.put(word, length + 1);
        } else {
            wordCloud.put(word, 1);
        }
    }

    public List<WordCloud> toWordCloudList() {
        List<WordCloud> wordCloudList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : wordCloud.entrySet()) {
            wordCloudList.add(new WordCloud(entry.getKey(), entry.getValue()));
        }

        return wordCloudList;
    }
}
