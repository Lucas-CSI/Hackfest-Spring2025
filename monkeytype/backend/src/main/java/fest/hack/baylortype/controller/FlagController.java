package fest.hack.baylortype.controller;

import fest.hack.baylortype.service.FlagService;
import fest.hack.baylortype.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/api/flag")
public class FlagController {
    private final FlagService flagService;

    @Autowired
    public FlagController(FlagService flagService) {
        this.flagService = flagService;
    }

    @GetMapping("/get")
    public String get() throws IOException {
        return flagService.getCurrentFlag();
    }

    @PostMapping("/plant")
    public ResponseEntity<String> plantFlag(@CookieValue("user") String user, @RequestParam("flag") String flag){
        String response = flagService.plantFlag(user, flag);

        if(response.contains("Error")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
