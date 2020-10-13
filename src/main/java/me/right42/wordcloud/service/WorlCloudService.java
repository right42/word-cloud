package me.right42.wordcloud.service;

import lombok.RequiredArgsConstructor;
import me.right42.wordcloud.collector.NaverNewsCollector;
import me.right42.wordcloud.creator.WordCloudCreator;
import me.right42.wordcloud.domain.WordCloud;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorlCloudService {

    private final WordCloudCreator wordCloudCreator;

    private final NaverNewsCollector naverNewsCollector;

    public List<WordCloud> getWordCloud() throws IOException {
        return wordCloudCreator.createWordCloud(naverNewsCollector.getTitles()).toWordCloudList();
    }
}
