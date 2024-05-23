package fr.cpe.scoobygang.atelier2.mapper;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Store;
import fr.cpe.scoobygang.atelier2.model.Transaction;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.request.TransactionResponse;
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
