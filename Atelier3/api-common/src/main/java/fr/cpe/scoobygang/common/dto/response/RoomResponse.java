package fr.cpe.scoobygang.common.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponse {
    Long id;
    String name;
    double bet;
    UserMinimalResponse owner;
    UserMinimalResponse challenger;
}
