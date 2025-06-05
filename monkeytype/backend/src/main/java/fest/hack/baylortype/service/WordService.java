package fest.hack.baylortype.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import fest.hack.baylortype.model.Word;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;
import fest.hack.baylortype.model.User;

@Service
public class WordService {
    private final Random random = new Random();

    public String[] getAllWords() throws IOException {
        return (Files.readString(Paths.get("words"))).split("\n");
    }

    public List<Word> generateWords(int amount, User user) throws IOException {
        String[] words = getAllWords();
        List<Word> wordsList = new ArrayList<>();

        for(int i = 0; i < amount; ++i){
            wordsList.add(new Word(words[random.nextInt(words.length)], i, user));
        }
        return wordsList;
    }

    public List<String> toOrderedStringList(List<Word> wordEntities) {
        if (wordEntities == null || wordEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return wordEntities.stream()
                .sorted(Comparator.comparingInt(Word::getWordIndex))
                .map(Word::getWord)
                .collect(Collectors.toList());
    }
}
