package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.mapper.CardMapper;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.request.CardRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardService {
    private final CardRepository cardRepository;
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCard(){
        return cardRepository.findOnSaleCards();
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

    public void attachUserToCard(User user)
    {
        // Récupère les cartes qui n'ont pas de propriétaire
        List<Card> cards = cardRepository.findNotOwnerCards();

        List<Card> shuffled = new ArrayList<>(cards);
        // Mélange les cartes
        Collections.shuffle(shuffled, new Random());

        List<Card> userCards = shuffled.subList(0, Math.min(5, shuffled.size()));
        // Save pour chaque carte
        userCards.forEach(card -> {
            card.setOwner(user);
        });

        cardRepository.saveAll(userCards);
    }
}

