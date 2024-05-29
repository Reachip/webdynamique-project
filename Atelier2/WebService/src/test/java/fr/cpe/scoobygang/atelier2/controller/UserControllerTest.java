package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.dao.request.LoginRequest;
import fr.cpe.scoobygang.atelier2.dao.request.UserRequest;
import fr.cpe.scoobygang.atelier2.dao.response.UserResponse;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import fr.cpe.scoobygang.atelier2.security.JWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;
    private String authorization;

    @BeforeEach
    public void setup() {
        LoginRequest userRequest = new LoginRequest();
        userRequest.setUsername("admin");
        userRequest.setPassword("admin");

        String jwt = userController.login(userRequest).getBody().get().getToken();
        this.authorization = "Bearer " + jwt;
    }

    @Test
    void testRegister() {
        UserRequest userRequest = new UserRequest();
        userRequest.setBalance(10);
        userRequest.setPassword("123");
        userRequest.setName("Boris");
        userRequest.setUsername("boris.lancelon");
        userRequest.setSurname("Lancelon");

        ResponseEntity<Optional<JWT>> jwt = userController.addUser(userRequest);

        assertEquals(200, jwt.getStatusCode().value());
        assertTrue(Objects.requireNonNull(jwt.getBody()).isPresent());

        assertNotNull(userRepository.findByUsername(userRequest.getUsername()));
    }

    @Test
    void testLogin() {
        LoginRequest userRequest = new LoginRequest();
        userRequest.setUsername("admin");
        userRequest.setPassword("admin");

        ResponseEntity<Optional<JWT>> jwt = userController.login(userRequest);

        assertEquals(200, jwt.getStatusCode().value());
        assertTrue(Objects.requireNonNull(jwt.getBody()).isPresent());
    }

    @Test
    void testGetAllUsers() {
        ResponseEntity<List<UserResponse>> usersResponse = userController.getAllUsers(this.authorization);
        assertEquals(200, usersResponse.getStatusCode().value());
        assertEquals(8, usersResponse.getBody().size());

    }

    // @Test
    void testGetUser() {
        User userInDB = userRepository.findAll().iterator().next();

        ResponseEntity<UserRequest> user = userController.getUser(this.authorization, userInDB.getId());
        assertEquals(userInDB.getEmail(), user.getBody().getEmail());
    }

    public void testPutUser() {
    }

    public void testDeleteUser() {
    }

    public void testGetCurrentUser() {
    }

    public void testResetPassword() {
    }
}