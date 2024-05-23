package fr.cpe.scoobygang.atelier2.dao.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreOrderRequest {
    private int cardId;
    private int storeId;
}
