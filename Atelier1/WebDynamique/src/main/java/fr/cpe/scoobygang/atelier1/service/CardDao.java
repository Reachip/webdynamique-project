package fr.cpe.scoobygang.atelier1.service;

import fr.cpe.scoobygang.atelier1.model.Card;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class CardDao {
    private final List<Card> cardList = new ArrayList<>();
    private final Random randomGenerator = new Random();
    private final URL ressource = new URL("http://tp.cpe.fr:8083/cards");

    public CardDao() throws MalformedURLException {
        createCardList();
    }

    private void createCardList() {
        try {
            JSONArray jsonArray = getObjects();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Card card = Card.fromJsonObject(jsonObject);
                cardList.add(card);
            }
        } catch (Exception why) {
            why.printStackTrace();
        }
    }

    private JSONArray getObjects() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) ressource.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;
        String response = "";

        while ((output = br.readLine()) != null) {
            response += output;
        }

        conn.disconnect();

        JSONArray jsonArray = new JSONArray(response);
        return jsonArray;
    }

    public Card getRandomCard() {
        int index = randomGenerator.nextInt(cardList.size());
        return cardList.get(index);
    }

}
