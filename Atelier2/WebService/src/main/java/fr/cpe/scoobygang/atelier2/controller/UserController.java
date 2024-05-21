package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.controller.request.LoginRequest;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.security.JWT;
import fr.cpe.scoobygang.atelier2.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(method= RequestMethod.POST,value="/register")
    public void addHero(@RequestBody User user) {
        userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public Optional<JWT> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getSurname(), loginRequest.getPassword());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> getAllUsers(@RequestHeader(value = "Authorization", required = false) String jwt) {
        if (jwt == null || !isValid(jwt)) {
            throw new RuntimeException("Unauthorized");
        }
        return userService.getAllUsers();
    }

    private boolean isValid(String jwt) {
        try {
            Jwts.parser().setSigningKey("secret_key").parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
