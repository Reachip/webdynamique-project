package fr.cpe.scoobygang.common.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResponse {
    private int id;
    private String name;
    private int cardCount;

}
