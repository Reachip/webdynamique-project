package fr.cpe.scoobygang.atelier3.api_user_microservice.controller;

import fr.cpe.scoobygang.atelier3.api_user_microservice.service.UserService;
import fr.cpe.scoobygang.common.dto.mapper.UserMapper;
import fr.cpe.scoobygang.common.dto.request.*;
import fr.cpe.scoobygang.common.dto.response.UserResponse;
import fr.cpe.scoobygang.common.exceptions.UserChangePasswordException;
import fr.cpe.scoobygang.common.jwt.JWT;
import fr.cpe.scoobygang.common.jwt.JWTService;
import fr.cpe.scoobygang.common.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final JWTService jwtService;
    private final UserService userService;

    public UserController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping(value="/register")
    public ResponseEntity<Optional<JWT>> addUser(@RequestBody UserRequest user) {
        User createdUser = userService.addUser(UserMapper.INSTANCE.userRequestToUser(user));
        Optional<JWT> response = userService.login(createdUser.getUsername(), createdUser.getPassword());

        String authorization = "Bearer " + response.get().getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<User> request = new HttpEntity<>(createdUser, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8086/card/cards/attach/user", request, Void.class);

        if (response == null) {
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
    public ResponseEntity<List<UserResponse>> getAllUsers(@RequestHeader(value = "Authorization") String authorization) {
        return ResponseEntity.ok(UserMapper.INSTANCE.usersToUserResponses(userService.getAllUsers()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserRequest> getUser(@RequestHeader(value = "Authorization") String authorization, @PathVariable int id) {
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserRequest(userService.getUser(id)));
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<UserPutRequest> putUser(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserPutRequest userPutRequest) {
        JWT jwt = jwtService.fromAuthorization(authorization).get();
        UserPutRequest body = userService.putUser(jwt, userPutRequest);

        return ResponseEntity.ok(body);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@RequestHeader(value = "Authorization") String authorization, @PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/current")
    public ResponseEntity<UserResponse> getCurrentUser(@RequestHeader(value = "Authorization") String authorization) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            int userID = jwt.get().getJwtInformation().getUserID();
            return ResponseEntity.ok(UserMapper.INSTANCE.userToUserResponse(userService.getUser(userID)));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/edit/password")
    public ResponseEntity<UserResponse> resetPassword(@RequestHeader(value = "Authorization") String authorization, @RequestBody ChangePasswordRequest changePasswordRequest) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            int userID = jwt.get().getJwtInformation().getUserID();

            try {
                User user = userService.changePassword(userID, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
                return ResponseEntity.ok(UserMapper.INSTANCE.userToUserResponse(user));
            } catch (UserChangePasswordException why) {
                return ResponseEntity.status(401).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Void> saveUser(@RequestHeader(value = "Authorization") String authorization, @RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest);
        return ResponseEntity.noContent().build();
    }
}
