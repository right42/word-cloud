package me.right42.wordcloud.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.right42.wordcloud.domain.WordCloud;
import me.right42.wordcloud.service.WorlCloudService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WordCloudController {

    private final WorlCloudService worlCloudService;

    @GetMapping("/wordcloud")
    public Result<List<WordCloud>> getWordCloud() throws IOException {
        return new Result<>(worlCloudService.getWordCloud());
    }

    @AllArgsConstructor
    @Getter
    private static class Result<T> {
        private final T body;
    }
}
