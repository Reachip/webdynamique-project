package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
