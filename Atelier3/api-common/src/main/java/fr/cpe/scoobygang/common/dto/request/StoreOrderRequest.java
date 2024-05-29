package fr.cpe.scoobygang.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreOrderRequest {
    private int cardId;
    private int storeId;
}
