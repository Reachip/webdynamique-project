package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.initializer.UserApplicationRunner;
import fr.cpe.scoobygang.atelier2.mapper.UserMapper;
import fr.cpe.scoobygang.atelier2.request.LoginRequest;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.request.UserRequest;
import fr.cpe.scoobygang.atelier2.security.JWT;
import fr.cpe.scoobygang.atelier2.security.JWTService;
import fr.cpe.scoobygang.atelier2.service.CardService;
import fr.cpe.scoobygang.atelier2.service.UserService;
import jakarta.annotation.PostConstruct;
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
    private final CardService cardService;

    public UserController(JWTService jwtService, UserService userService, CardService cardService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.cardService = cardService;
    }

    @PostMapping(value="/register")
    public ResponseEntity<Optional<JWT>> addUser(@RequestBody UserRequest user) {
        User createdUser = userService.addUser(UserMapper.INSTANCE.userRequestToUser(user));
        Optional<JWT> response = userService.login(createdUser.getUsername(), createdUser.getPassword());

        cardService.attachUserToCard(createdUser);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Optional<JWT>> login(@RequestBody LoginRequest loginRequest) {
        Optional<JWT> response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader(value = "Authorization") String authorization) {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserRequest> getUser(@RequestHeader(value = "Authorization") String authorization, @PathVariable int id) {
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserRequest(userService.getUser(id)));
    }

    @PutMapping(value = "/user")
    public ResponseEntity<UserRequest> putUser(@RequestHeader(value = "Authorization") String authorization, @RequestBody User user) {
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserRequest(userService.putUser(user)));
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@RequestHeader(value = "Authorization") String authorization, @PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/user/current")
    public ResponseEntity<UserRequest> getCurrentUser(@RequestHeader(value = "Authorization") String authorization) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        int userID = jwt.get().getJwtInformation().getUserID();
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserRequest(userService.getUser(userID)));
    }
}
