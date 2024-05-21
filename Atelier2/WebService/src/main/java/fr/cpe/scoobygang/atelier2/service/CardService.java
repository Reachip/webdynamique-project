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
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Card> getAllCard(){
        return cardRepository.findNotOwnerCards();
    }

    public boolean buyCard(int cardId, int userId){
        // Recherche de l'utilisateur par ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));

        // Recherche de la carte par ID
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        card.setOwner(user);
        user.getCards().add(card);

        cardRepository.save(card);
        userRepository.save(user);

        return true;
    }

    public List<Card> getAllUserCard(int userId){
        return cardRepository.findByOwnerId(userId);
    }

    public boolean sellUserCard(int cardId, int userId) {
        // Recherche de l'utilisateur par ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));

        // Recherche de la carte par ID
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        // Vérification que la carte appartient bien à l'utilisateur
        if (!user.getCards().contains(card)) {
            throw new IllegalStateException("Card not owned by the user.");
        }

        // Retirer la carte de la liste de l'utilisateur
        user.getCards().remove(card);
        card.setOwner(null); // Mise à jour de la relation à null

        // Sauvegarder les modifications
        userRepository.save(user);
        cardRepository.save(card);

        return true;
    }

    public void saveCards(List<Card> cards) {
        cardRepository.saveAll(cards);
    }
}
