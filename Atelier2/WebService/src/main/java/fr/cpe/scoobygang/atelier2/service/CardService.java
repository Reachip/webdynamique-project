package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.mapper.CardMapper;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import fr.cpe.scoobygang.atelier2.request.CardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCard(){
        return cardRepository.findNotOwnerCards();
    }
    public List<Card> getAllUserCard(int userId){
        return cardRepository.findByOwnerId(userId);
    }
    public void saveCards(List<Card> cards) {
        cardRepository.saveAll(cards);
    }
    public Card saveCard(CardRequest cardRequest) {
        return cardRepository.save(CardMapper.INSTANCE.cardRequestToCard(cardRequest));
    }

    public Optional<Card> updateCard(int id, CardRequest cardRequest) {
        Optional<Card> optionalCard = cardRepository.findById(id);

        if (optionalCard.isPresent()) {
            Card cardToUpdate = optionalCard.get();
            CardMapper.INSTANCE.updateCard(cardToUpdate, cardRequest);
            cardRepository.save(cardToUpdate);
        }

        return Optional.empty();
    }

    public Optional<Card> getCard(int id) {
        return cardRepository.findById(id);
    }

    public Iterable<Card> getCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> deleteCard(int id) {
        Optional<Card> optionalCard = cardRepository.findById(id);

        if (optionalCard.isPresent()) {
            cardRepository.delete(optionalCard.get());
            return optionalCard;
        }

        return Optional.empty();
    }
}
