package fest.hack.baylortype.service;

import fest.hack.baylortype.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final UserService userService;
    private final WordService wordService;

    @Autowired
    public GameService(UserService userService, WordService wordService) {
        this.userService = userService;
        this.wordService = wordService;
    }

    public String[] getWords(){

    }

    public List<String> startGame(String username) throws IOException {
        Optional<User> optionalUser = userService.getUserByName(username);

        if(optionalUser.isEmpty()){
            return null
        }

        User user = optionalUser.get();

        user.setInGame(true);
        user.setStartTime(Instant.now().toEpochMilli());
        user.setAttempts(user.getAttempts() + 1);

        userService.save(user);

        return wordService.generateWords(40);
    }
}
