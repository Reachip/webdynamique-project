package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.mapper.UserMapper;
import fr.cpe.scoobygang.atelier2.request.LoginRequest;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.request.UserRequest;
import fr.cpe.scoobygang.atelier2.security.JWT;
import fr.cpe.scoobygang.atelier2.security.JWTService;
import fr.cpe.scoobygang.atelier2.service.UserService;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private final JWTService jwtService;
    private final UserService userService;

    public UserController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping(value="/user")
    public ResponseEntity<Optional<JWT>> addUser(@RequestBody UserRequest user) {
        User createdUser = userService.addUser(UserMapper.INSTANCE.userRequestToUser(user));
        Optional<JWT> response = userService.login(createdUser.getUsername(), createdUser.getPassword());
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(response);
    }

    public Resource loadUsers() {
        return new ClassPathResource("/user.json");
    }
    @PostConstruct
    public void init() {
        List<User> users = new ArrayList<>();
        try {
            File file = loadUsers().getFile();
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = jsonObjectToCard(jsonObject);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userService.addAllUser(users);
    }
    private User jsonObjectToCard(JSONObject jsonObject) {
        User user = new User();
        user.setUsername(jsonObject.getString("username"));
        user.setPassword(jsonObject.getString("password"));
        user.setAccount(jsonObject.getDouble("account"));
        return user;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<Optional<JWT>> login(@RequestBody LoginRequest loginRequest) {
        Optional<JWT> response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader(value = "Authorization") String authorization) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);

        if (jwt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserRequest> getUser(@RequestHeader(value = "Authorization") String authorization, @RequestParam int userId) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);

        if (jwt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserRequest(userService.getUser(userId)));
    }

    @PutMapping(value = "/user")
    public ResponseEntity<UserRequest> putUser(@RequestHeader(value = "Authorization") String authorization) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);

        if (jwt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        int userID = jwt.get().getJwtInformation().getUserID();
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserRequest(userService.getUser(userID)));
    }

}
