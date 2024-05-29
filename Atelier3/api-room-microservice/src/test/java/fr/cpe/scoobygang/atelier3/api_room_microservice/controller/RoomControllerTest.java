package fr.cpe.scoobygang.atelier3.api_room_microservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cpe.scoobygang.atelier3.api_room_microservice.service.RoomService;
import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.dto.response.RoomResponse;
import fr.cpe.scoobygang.common.jwt.JWT;
import fr.cpe.scoobygang.common.jwt.JWTService;
import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import fr.cpe.scoobygang.common.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RoomController roomController;
    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getRoomsIsOk() {
        roomRepository.saveAll(List.of(new Room(), new Room()));

        when(roomService.getRooms()).thenReturn(List.of(new Room(), new Room()));
        ResponseEntity<List<RoomResponse>> rooms = roomController.getRooms();


        assertEquals(HttpStatusCode.valueOf(200), rooms.getStatusCode());
        assertEquals(7, Objects.requireNonNull(rooms.getBody()).size());
    }

    @Test
    void getRoomIsOk() throws Exception {
        Long id = 1L;
        Room expectedRoom = new Room();
        expectedRoom.setId(id);

        when(roomService.getRoom(id)).thenReturn(expectedRoom);

        mockMvc.perform(get("/room/" + id)
                .header("Authorization", "Bearer token")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }

    @Test
    void createRoomIsOk() {
        assertEquals(roomRepository.findAll().stream().findFirst().get().getId(), 1);
    }


    @Test
    void testCreateRoom() throws Exception {
        User user = new User();
        user.setId(1);
        user.setBalance(0);
        user.setUsername("user");

        RoomCreateRequest request = new RoomCreateRequest();
        request.setBet(0);
        request.setName("room test");
        request.setUsername(user.getUsername());
        request.setId(1L);

        Room expectedRoom = new Room();
        expectedRoom.setChallenger(null);
        expectedRoom.setId(1L);
        expectedRoom.setOwner(user);
        expectedRoom.setName("room test");

        when(roomService.createRoom(request)).thenReturn(expectedRoom);

        mockMvc.perform(post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void joinRoom_ShouldReturnOk_WhenJwtIsValid() throws Exception {
        Long roomId = 1L;
        String authorization = "Bearer token";

        JWT jwt = new JWT("token");;

        UserRequest userRequest = new UserRequest();
        User user = new User();
        user.setId(123);

        Room room = new Room();
        room.setId(roomId);

        when(jwtService.fromAuthorization(authorization)).thenReturn(Optional.of(jwt));
        when(restTemplate.exchange(
                eq("http://localhost:8085/user/user/123"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UserRequest.class))).thenReturn(new ResponseEntity<>(userRequest, HttpStatus.OK));
        when(roomService.joinRoom(any(User.class), eq(roomId))).thenReturn(room);

        mockMvc.perform(put("/room/{id}", roomId)
                        .header("Authorization", authorization))
                .andExpect(status().isOk());
    }

    @Test
    void joinRoom_ShouldReturnOk_WhenJwtIsNotValid() throws Exception {
        Long roomId = 1L;
        String authorization = "Bearer invalid.token.here";

        when(jwtService.fromAuthorization(authorization)).thenReturn(Optional.empty());

        mockMvc.perform(put("/room/{id}", roomId)
                        .header("Authorization", authorization))
                .andExpect(status().isOk());
    }

}

