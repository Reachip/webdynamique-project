package fr.cpe.scoobygang.atelier3.api_room_microservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cpe.scoobygang.atelier3.api_room_microservice.service.RoomService;
import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.dto.response.RoomResponse;
import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import fr.cpe.scoobygang.common.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomController roomController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getRoomsIsOk() {
        roomRepository.saveAll(List.of(new Room(), new Room()));

        ResponseEntity<List<RoomResponse>> rooms = roomController.getRooms();

        assertEquals(HttpStatusCode.valueOf(200), rooms.getStatusCode());
        assertEquals(7, Objects.requireNonNull(rooms.getBody()).size());
    }

    @Test
    void getRoomIsOk() throws Exception {
        Long id = 1L;
        Room expectedRoom = new Room();
        expectedRoom.setId(id);

        Mockito.when(roomService.getRoom(id)).thenReturn(expectedRoom);

        mockMvc.perform(MockMvcRequestBuilders.get("/room/" + id)
                .header("Authorization", "Bearer token")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));


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

        Mockito.when(roomService.createRoom(request)).thenReturn(expectedRoom);

        mockMvc.perform(MockMvcRequestBuilders.post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
