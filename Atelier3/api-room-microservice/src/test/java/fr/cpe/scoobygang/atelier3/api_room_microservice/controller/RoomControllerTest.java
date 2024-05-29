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

        List<RoomResponse> roomResponses = new ArrayList<>();
        roomResponses.add(new RoomResponse());
        roomResponses.add(new RoomResponse());

        when(roomService.getRooms()).thenReturn(roomResponses);
        ResponseEntity<List<RoomResponse>> rooms = roomController.getRooms();

        assertEquals(HttpStatusCode.valueOf(200), rooms.getStatusCode());
        assertEquals(2, Objects.requireNonNull(rooms.getBody()).size());
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
}

