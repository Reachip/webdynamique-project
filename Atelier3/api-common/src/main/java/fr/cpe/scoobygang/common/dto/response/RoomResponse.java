package fr.cpe.scoobygang.common.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponse {
    UserResponse owner;
    UserResponse challenger;
    Long id;
}
