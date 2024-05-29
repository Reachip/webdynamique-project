package fr.cpe.scoobygang.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateRequest {
    private String name;
    private Long id;
    private String username;
    private double bet = 0;
}
