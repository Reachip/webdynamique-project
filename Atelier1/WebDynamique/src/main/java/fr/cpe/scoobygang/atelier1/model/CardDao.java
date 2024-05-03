package fr.cpe.scoobygang.atelier1.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CardDao {

    public CardDao() {
        myCardList=new ArrayList<>();
        randomGenerator = new Random();
        createCardList();
    }

    private void createCardList() {
        Card c1 = new Card("name0", "description0", "family0", "affinity0", "http://medias.3dvf.com/news/sitegrab/gits2045.jpg", "https://cdn.animenewsnetwork.com/thumbnails/fit600x1000/cms/feature/89858/05.jpg", 1, 100, 63.503258, 74.43771, 2.9160202, 4817.1396, 1);
        Card c2 = new Card("name0", "description0", "family0", "affinity0", "http://medias.3dvf.com/news/sitegrab/gits2045.jpg", "https://cdn.animenewsnetwork.com/thumbnails/fit600x1000/cms/feature/89858/05.jpg", 3, 100, 94.67086, 79.005554, 24.894882, 5971.426, 1);
        Card c3 = new Card("name6", "description6", "family6", "affinity6", "http://medias.3dvf.com/news/sitegrab/gits2045.jpg", "https://cdn.animenewsnetwork.com/thumbnails/fit600x1000/cms/feature/89858/05.jpg", 4, 100, 92.69412, 45.615788, 63.227768, 6030.754, 1);
        Card c4 = new Card("name0", "description0", "family0", "affinity0", "http://medias.3dvf.com/news/sitegrab/gits2045.jpg", "https://cdn.animenewsnetwork.com/thumbnails/fit600x1000/cms/feature/89858/05.jpg", 5, 100, 74.20037, 73.58286, 49.75799, 5950.824, 1);
        Card c5 = new Card("string", "string", "string", "string", "string", "string", 6, 0, 0, 0, 0, 0, null);
        Card c6 = new Card("string", "string", "string", "string", "string", "string", 7, 0, 0, 0, 0, 0, null);
        Card c7 = new Card("string", "string", "string", "string", "string", "string", 8, 0, 0, 0, 0, 0, null);

        myCardList.add(c1);
        myCardList.add(c2);
        myCardList.add(c3);
        myCardList.add(c4);
        myCardList.add(c5);
        myCardList.add(c6);
        myCardList.add(c7);
    }

    private List<Card> myCardList;
    private Random randomGenerator;
    public Card getRandomCard(){
        int index=randomGenerator.nextInt(myCardList.size());
        return myCardList.get(index);
    }

    public List<Card> getPoneyList() {
        return this.myCardList;
    }




}
