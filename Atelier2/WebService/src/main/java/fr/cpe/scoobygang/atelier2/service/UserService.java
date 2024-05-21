package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        Iterable<User> iterable = userRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
