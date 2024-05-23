package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.dao.mapper.UserMapper;
import fr.cpe.scoobygang.atelier2.dao.request.UserPutRequest;
import fr.cpe.scoobygang.atelier2.exceptions.UserChangePasswordException;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import fr.cpe.scoobygang.atelier2.security.JWT;
import fr.cpe.scoobygang.atelier2.security.JWTService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private UserRepository userRepository;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
    public void addAllUser(List<User> users) {
        userRepository.saveAll(users);
    }


    public List<User> getAllUsers() {
        Iterable<User> iterable = userRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public User getUser(int userID) {
        return userRepository.findById(userID).get();
    }

    public User changePassword(int userID, String oldPassword, String newPassword) throws UserChangePasswordException {
        User user = userRepository.findById(userID).get();

        if (!user.getPassword().equals(oldPassword)) {
            throw new UserChangePasswordException("Pas possible de changer le mot de passe de cet utilisateur");
        }

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public Optional<JWT> login(String username, String password) {
        User existingUser = userRepository.findByUsername(username);

        if (existingUser == null || !Objects.equals(existingUser.getPassword(), password)) {
            return Optional.empty();
        }

        JWT jwt = jwtService.fromUser(existingUser);
        return Optional.of(jwt);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public UserPutRequest putUser(JWT jwt, UserPutRequest userPutRequest) {
        User mappedUser = UserMapper.INSTANCE.userPutRequestToUser(userPutRequest);
        User userToUpdate = userRepository.findById(jwt.getJwtInformation().getUserID()).get();

        UserMapper.INSTANCE.userApply(userToUpdate, mappedUser);
        userRepository.save(userToUpdate);

        return userPutRequest;
    }
}
