package me.right42.wordcloud.creator;

import me.right42.wordcloud.domain.WordClouds;
import org.openkoreantext.processor.KoreanTokenJava;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import scala.collection.Seq;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class WordCloudCreator {

    private static final List<String> STOP_WORDS = new ArrayList<>();

    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("[^\\uAC00-\\uD7A3xfe0-9a-zA-Z\\\\]");

    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:\\static\\stopwords_kr.txt");

        STOP_WORDS.addAll(Files.readAllLines(file.toPath()));
    }

    public WordClouds createWordCloud(String data) {
        return WordClouds.creat(preProcessing(data));
    }

    private List<String> preProcessing(String data) {
        List<String> texts = new ArrayList<>();

        Seq<KoreanTokenizer.KoreanToken> tokenize = OpenKoreanTextProcessorJava.tokenize(data);
        List<KoreanTokenJava> koreanTokenJavas = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokenize);

        for (KoreanTokenJava koreanTokenJava : koreanTokenJavas) {
            String text = koreanTokenJava.getText();

            if(!StringUtils.isEmpty(koreanTokenJava.getStem())) {
                text = koreanTokenJava.getStem();
            }

            CharSequence normalizeText = OpenKoreanTextProcessorJava.normalize(text);

            if (normalizeText.length() < 2 ||
                    SPECIAL_CHARACTER_PATTERN.matcher(normalizeText).find() ||
                    CollectionUtils.containsInstance(STOP_WORDS, normalizeText)
            ) {
                continue;
            }

            texts.add(text);
        }

        return texts;
    }

    public List<String> getStopWords(){
        return Collections.unmodifiableList(STOP_WORDS);
    }
}
