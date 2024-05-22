package fr.cpe.scoobygang.atelier2.initializer;

import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.resource.UserResource;
import fr.cpe.scoobygang.atelier2.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserApplicationRunner implements ApplicationRunner {
    private final UserResource userResource;
    private final UserService userService;
    public UserApplicationRunner(UserResource userResource, UserService userService) {
        this.userResource = userResource;
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = new ArrayList<>();

        try {
            File file = userResource.load().getFile();
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = User.toUser(jsonObject);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        userService.addAllUser(users);
    }
}
