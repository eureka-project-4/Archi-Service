package com.archiservice.badword;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BadWordFilterService {

    private final Set<String> badWords;

    private static final String BAD_WORDS_PATH = "src/main/resources/badwords.txt";
    public BadWordFilterService() {
        this.badWords = loadBadWords();
    }

    private Set<String> loadBadWords() {
        try {
            Path path = Paths.get(BAD_WORDS_PATH);
            Set<String> wordsFromFile = Files.lines(path, StandardCharsets.UTF_8)
                    .filter(line -> !line.trim().isEmpty())  // 빈 줄 제거
                    .map(String::trim)
                    .collect(Collectors.toSet());

            log.info("불용어 {} 개 로드 완료", wordsFromFile.size());
            return wordsFromFile;
        } catch (IOException e) {
            log.error("불용어 파일 로드 실패");
            return Collections.emptySet();
        }
    }

    public boolean containsBadWord(String content) {
        if (content == null || content.trim().isEmpty()) {
            return false;
        }

        String normalizedContent = content.toLowerCase()
                .replaceAll("\\s+", "")  // 공백 제거
                .replaceAll("[^가-힣a-zA-Z0-9]", ""); // 특수문자 제거

        return badWords.stream()
                .anyMatch(badWord -> normalizedContent.contains(badWord.toLowerCase()));
    }

    public List<String> findBadWords(String content) {
        if (content == null) return Collections.emptyList();

        String normalizedContent = content.toLowerCase()
                .replaceAll("\\s+", "")
                .replaceAll("[^가-힣a-zA-Z0-9]", "");

        return badWords.stream()
                .filter(badWord -> normalizedContent.contains(badWord.toLowerCase()))
                .collect(Collectors.toList());
    }
}
