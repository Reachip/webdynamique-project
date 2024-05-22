package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.initializer.UserInitializer;
import fr.cpe.scoobygang.atelier2.mapper.UserMapper;
import fr.cpe.scoobygang.atelier2.request.LoginRequest;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.request.UserRequest;
import fr.cpe.scoobygang.atelier2.security.JWT;
import fr.cpe.scoobygang.atelier2.security.JWTService;
import fr.cpe.scoobygang.atelier2.service.UserService;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private final JWTService jwtService;
    private final UserService userService;
    private final UserInitializer userInitializer;

    public UserController(JWTService jwtService, UserService userService, UserInitializer userInitializer) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.userInitializer = userInitializer;
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

    @PostConstruct
    public void init() {
        userInitializer.initialize();
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<Optional<JWT>> login(@RequestBody LoginRequest loginRequest) {
²        Optional<JWT> response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
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
    public ResponseEntity<UserRequest> getUser(@RequestHeader(value = "Authorization") String authorization, @RequestParam int id) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);

        if (jwt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserRequest(userService.getUser(id)));
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

    @GetMapping(value = "/currentUser")
    public ResponseEntity<UserRequest> getCurrentUser(@RequestHeader(value = "Authorization") String authorization) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);

        if (jwt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        int userID = jwt.get().getJwtInformation().getUserID();
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserRequest(userService.getUser(userID)));
    }
}
