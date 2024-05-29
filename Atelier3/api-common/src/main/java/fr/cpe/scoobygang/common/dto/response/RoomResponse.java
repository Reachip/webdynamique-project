package fr.cpe.scoobygang.common.dto.response;

import fr.cpe.scoobygang.common.model.Card;
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
    Card ownerCard;
    Card challengerCard;
}
