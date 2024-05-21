package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method= RequestMethod.POST,value="/register")
    public void addHero(@RequestBody User user) {
        userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
