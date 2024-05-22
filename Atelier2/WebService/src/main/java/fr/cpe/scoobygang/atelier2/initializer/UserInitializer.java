package fr.cpe.scoobygang.atelier2.initializer;

import fr.cpe.scoobygang.atelier2.model.Store;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.resource.StoreResource;
import fr.cpe.scoobygang.atelier2.resource.UserResource;
import fr.cpe.scoobygang.atelier2.service.StoreService;
import fr.cpe.scoobygang.atelier2.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInitializer implements Initializer {
    private final UserResource userResource;
    private final UserService userService;
    public UserInitializer(UserResource userResource, UserService userService) {
        this.userResource = userResource;
        this.userService = userService;
    }

    @Override
    public void initialize() {
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
