package fr.cpe.scoobygang.common.dto.mapper;

import fr.cpe.scoobygang.common.dto.response.*;
import fr.cpe.scoobygang.common.model.*;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper( TransactionMapper.class );

    @Mapping(source = "owner", target = "ownerId", qualifiedByName = "ownerToOwnerId")
    @Mapping(source = "store", target = "storeId", qualifiedByName = "storeToStoreId")
    @Mapping(source = "card", target = "cardId", qualifiedByName = "cardToCardId")
    TransactionResponse transactionToTransactionResponse(Transaction transaction);

    @Named("ownerToOwnerId")
    static int ownerToOwnerId(User user) {
        return user.getId();
    }

    @Named("storeToStoreId")
    static int storeToStoreId(Store store) {
        return store.getId();
    }

    @Named("cardToCardId")
    static int cardToCardId(Card card) {
        return card.getId();
    }
}
