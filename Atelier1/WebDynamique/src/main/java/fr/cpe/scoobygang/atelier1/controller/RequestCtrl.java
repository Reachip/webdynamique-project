package fr.cpe.scoobygang.atelier1.controller;

import fr.cpe.scoobygang.atelier1.model.Card;
import fr.cpe.scoobygang.atelier1.model.CardDao;
import fr.cpe.scoobygang.atelier1.model.CardFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RequestCtrl {
    @Autowired
    CardDao cardDao;

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String viewCard(Model model) {
        model.addAttribute("myCard", cardDao.getRandomCard());
        return "cardView";
    }

    // formular to add a card
    @RequestMapping(value = {"/addCard"}, method = RequestMethod.GET)
    public String addCard(Model model) {
        model.addAttribute("cardForm", new CardFormDto());
        return "addCardView";
    }

    // Add a card action
    @RequestMapping(value = {"/addCard"}, method = RequestMethod.POST)
    public String addCard(Model model, @ModelAttribute("cardForm") CardFormDto cardFormDto) {
        Card card = cardDao.addCard(cardFormDto.getName(), cardFormDto.getDescription(), cardFormDto.getImgUrl(), cardFormDto.getFamily(), cardFormDto.getAffinity(), cardFormDto.getHp(), cardFormDto.getEnergy(), cardFormDto.getAttack(), cardFormDto.getDefence());
        model.addAttribute("card", card);
        return "cardView";
    }

}
