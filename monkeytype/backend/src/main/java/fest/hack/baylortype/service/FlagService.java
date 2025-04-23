package fest.hack.baylortype.service;

import fest.hack.baylortype.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Service
public class FlagService {
    private final UserService userService;

    @Autowired
    public FlagService(UserService userService) {
        this.userService = userService;
    }

    public String getCurrentFlag() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("flag.txt"));
        return br.readLine();
    }

    public String plantFlag(String username, String flag) {
        Optional<User> optionalUser = userService.getUserByName(username);

        if(optionalUser.isEmpty()){
            return "Error: User not found";
        }

        User user = optionalUser.get();

        if(user.getCanPlantFlag()){
            user.setCanPlantFlag(false);
            try (FileWriter fileWriter = new FileWriter("flag.txt", false)) {
                fileWriter.write(flag);
                System.out.println("overwritten");
            } catch (IOException e) {
                System.err.println("An error occurred while writing to the file: " + e.getMessage());
                user.setCanPlantFlag(true);
            }
        }else{
            return "Error: User not can plant flag";
        }

        userService.save(user);

        return "Flag set to: " + flag;
    }
}
