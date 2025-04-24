package fest.hack.baylortype.controller;

import fest.hack.baylortype.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam String password) {
        String response = userService.createUser(username, password);

        if(response.contains("Error")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletResponse servletResponse) {
        String response = userService.login(username, password);

        if(response.contains("Error")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Cookie cookie = new Cookie("user", username);
        cookie.setMaxAge(60 * 24 * 24 * 60);
        cookie.setPath("/");

        servletResponse.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
