package fest.hack.baylortype.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/api/flag")
public class FlagController {
    @GetMapping("/get")
    public String get() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("flag.txt"));
        return br.readLine();
    }
}
