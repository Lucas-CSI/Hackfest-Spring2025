package fest.hack.baylortype.service;

import fest.hack.baylortype.model.Score;
import fest.hack.baylortype.model.User;
import fest.hack.baylortype.repository.ScoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final UserService userService;
    private final WordService wordService;
    private final ScoreRepository scoreRepository;

    @Autowired
    public GameService(UserService userService, WordService wordService, ScoreRepository scoreRepository) {
        this.userService = userService;
        this.wordService = wordService;
        this.scoreRepository = scoreRepository;
    }

    private Double calculateAccuracy(String[] submittedWords, List<String> wordList) {
        if (wordList == null || wordList.isEmpty()) {
            if (submittedWords == null || submittedWords.length == 0) {
                return 1.0; 
            }
            return 0.0; 
        }

        int charCorrect = 0;
        int charTotal = 0;

        int commonLength = Math.min(submittedWords.length, wordList.size());

        for (int i = 0; i < commonLength; i++) {
            String submittedWord = submittedWords[i];
            String correctWord = wordList.get(i);

            if (submittedWord == null) submittedWord = ""; 
            if (correctWord == null) correctWord = "";   


            for (int j = 0; j < submittedWord.length(); j++) {
                charTotal++; 
                if (j < correctWord.length() && submittedWord.charAt(j) == correctWord.charAt(j)) {
                    charCorrect++;
                }
            }
            if (submittedWord.length() < correctWord.length()) {
                charTotal += (correctWord.length() - submittedWord.length());
            }
        }

        if (submittedWords.length < wordList.size()) {
            for (int i = submittedWords.length; i < wordList.size(); i++) {
                String correctWord = wordList.get(i);
                if (correctWord != null) {
                    charTotal += correctWord.length();
                }
            }
        }else if (submittedWords.length > wordList.size()) {
            for (int i = wordList.size(); i < submittedWords.length; i++) {
                String extraSubmittedWord = submittedWords[i];
                if (extraSubmittedWord != null) {
                    charTotal += extraSubmittedWord.length();
                }
            }
        }


        if (charTotal == 0) {
            return (charCorrect == 0) ? 1.0 : 0.0; 
        }

        return ((double) charCorrect) / charTotal;
    }

    public List<String> startGame(String username) throws IOException {
        Optional<User> optionalUser = userService.getUserByName(username);

        if(optionalUser.isEmpty()){
            return null;
        }

        User user = optionalUser.get();

        user.setInGame(true);
        user.setStartTime(Instant.now().toEpochMilli());
        user.setAttempts(user.getAttempts() + 1);

        List<String> words = wordService.generateWords(30);

        user.setWords(words);

        User savedUser = userService.save(user);

        return savedUser.getWords();
    }

    @Transactional
    public Score submit(String username, String[] words){
        Optional<User> optionalUser = userService.getUserByName(username);

        if(optionalUser.isEmpty()){
            return null;
        }

        User user = optionalUser.get();

        if(!user.getInGame())
            return null;

        Score score = new Score();
        Long startTime = user.getStartTime();
        Double timeTaken = (Instant.now().toEpochMilli() - user.getStartTime()) / 1000.0;
        Double WPM = user.getWords().size() / timeTaken * 60;
        Double accuracy = calculateAccuracy(words, user.getWords());

        score.setTimestamp(Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime());
        score.setTimeTaken(timeTaken);
        score.setWPM(WPM);
        score.setAccuracy(accuracy);

        if(WPM >= 200 && accuracy > 0.99){
            user.setCanPlantFlag(true);
        }

        score = scoreRepository.save(score);

        user.getScores().add(score);
        user.setInGame(false);

        userService.save(user);

        return score;
    }
}
