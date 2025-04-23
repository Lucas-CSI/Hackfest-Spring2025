package fest.hack.baylortype.service;

import fest.hack.baylortype.model.User;
import fest.hack.baylortype.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public String createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Error: Username is already in use";
        }
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setAttempts(0);
        user.setInGame(false);

        userRepository.save(user);

        return "User created";
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || !user.getPassword().equals(password)) {
            return "Error: Username or password is wrong";
        }

        return "Successfully logged in";
    }
}
