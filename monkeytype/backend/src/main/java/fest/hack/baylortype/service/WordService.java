package fest.hack.baylortype.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WordService {
    private final Random random = new Random();

    public String[] getAllWords() throws IOException {
        return (Files.readString(Paths.get("words"))).split("\r\n");
    }

    public List<String> generateWords(int amount) throws IOException {
        String[] words = getAllWords();
        List<String> wordsList = new ArrayList<>();

        for(int i = 0; i < amount; ++i){
            wordsList.add(words[random.nextInt(words.length)]);
        }
        return wordsList;
    }
}
