package fest.hack.baylortype.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/words")
public class WordController {
    @GetMapping
    public String[] getWords() throws IOException {
        return (Files.readString(Paths.get("words"))).split("\n");
    }
}
