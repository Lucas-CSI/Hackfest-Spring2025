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
    public Score submit(@CookieValue("user") String username, @RequestParam("words") String[] words) {
        return gameService.submit(username, words);
    }

    @PostMapping("/plant")
    public ResponseEntity<String> plantFlag(@CookieValue("user") String user, @RequestParam("flag") String flag){
        String response = gameService.plantFlag(user, flag);

        if(response.contains("Error")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
