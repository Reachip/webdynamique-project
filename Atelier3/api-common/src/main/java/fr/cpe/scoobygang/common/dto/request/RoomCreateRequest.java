package fr.cpe.scoobygang.common.dto.request;

import fr.cpe.scoobygang.common.model.enumeration.ROOM_STATUS;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateRequest {
    private String name;
    private int userId;
    private double bet = 0;
}
