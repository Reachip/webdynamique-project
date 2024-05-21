package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.controller.request.LoginRequest;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.security.JWT;
import fr.cpe.scoobygang.atelier2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/register")
    public void addHero(@RequestBody User user) {
        userService.addUser(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Optional<JWT>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest.getSurname(), loginRequest.getPassword()));
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader(value = "Authorization") String jwt) {
        if (!JWT.isOk(jwt))
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(userService.getAllUsers());
    }
}
