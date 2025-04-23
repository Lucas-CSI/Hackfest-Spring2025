package fest.hack.baylortype.controller;

import fest.hack.baylortype.model.Score;
import fest.hack.baylortype.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<List<String>> startGame(@CookieValue("user") String user) throws IOException {
        List<String> list = gameService.startGame(user);

        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @PostMapping("/submit")
    public ResponseEntity<Score> submit(@CookieValue("user") String username, @RequestParam("words") String[] words) {
        Score score = gameService.submit(username, words);
        if(score == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(score);
    }
}
