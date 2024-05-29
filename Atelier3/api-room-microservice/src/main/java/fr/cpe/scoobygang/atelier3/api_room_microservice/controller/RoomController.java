package fr.cpe.scoobygang.atelier3.api_room_microservice.controller;

import fr.cpe.scoobygang.atelier3.api_room_microservice.service.RoomService;
import fr.cpe.scoobygang.common.dto.mapper.RoomMapper;
import fr.cpe.scoobygang.common.dto.mapper.UserMapper;
import fr.cpe.scoobygang.common.dto.request.PutCardRoomRequest;
import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.dto.response.RoomResponse;
import fr.cpe.scoobygang.common.jwt.JWT;
import fr.cpe.scoobygang.common.jwt.JWTService;
import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class RoomController {
    private final RoomService roomService;
    private final JWTService jwtService;
    public RoomController(RoomService roomService, JWTService jwtService) {
        this.roomService = roomService;
        this.jwtService = jwtService;
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomResponse>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @PostMapping("/room")
    public ResponseEntity<Room> createRoom(@RequestBody RoomCreateRequest request) {
        return ResponseEntity.ok(roomService.createRoom(request));
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<RoomResponse> joinRoom(@RequestHeader(value = "Authorization") String authorization, @PathVariable ("id") Long id) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            int userID = jwt.get().getJwtInformation().getUserID();

            String requestAuthorization = "Bearer " + jwt.get().getToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", requestAuthorization);
            HttpEntity<User> request = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();

            UserRequest userRequest = restTemplate.exchange(
                    "http://localhost:8085/user/user/" + userID,
                    HttpMethod.GET,
                    request,
                    UserRequest.class).getBody();
            Room room = roomService.joinRoom(UserMapper.INSTANCE.userRequestToUser(userRequest), id);
            return ResponseEntity.ok(RoomMapper.INSTANCE.roomToRoomResponse(room));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<RoomResponse> getRoom(@RequestHeader(value = "Authorization") String authorization, @PathVariable ("id") Long id) {
        return ResponseEntity.ok(RoomMapper.INSTANCE.roomToRoomResponse(roomService.getRoom(id)));
    }

    @PutMapping("/room/card")
    public ResponseEntity<RoomResponse> updateCardChoice(@RequestHeader(value = "Authorization") String authorization, @RequestBody PutCardRoomRequest cardRoomRequest) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            int userID = jwt.get().getJwtInformation().getUserID();

            String requestAuthorization = "Bearer " + jwt.get().getToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", requestAuthorization);
            HttpEntity<User> request = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();

            UserRequest userRequest = restTemplate.exchange(
                    "http://localhost:8085/user/user/" + userID,
                    HttpMethod.GET,
                    request,
                    UserRequest.class).getBody();

            RoomResponse roomResponse = roomService.updateCard(UserMapper.INSTANCE.userRequestToUser(userRequest), cardRoomRequest);
            return ResponseEntity.ok(roomResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
