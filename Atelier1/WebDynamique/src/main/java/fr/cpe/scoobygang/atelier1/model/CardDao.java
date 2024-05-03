package fr.cpe.scoobygang.atelier1.model;

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

    private List<Card> cardList;
    private Random randomGenerator;

    public CardDao() {
        cardList = new ArrayList<>();
        randomGenerator = new Random();
        createCardList();
    }

    private void createCardList() {

        try {
            URL url = new URL("http://tp.cpe.fr:8083/cards");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.isNull("name") ? "" : jsonObject.getString("name");
                String description = jsonObject.isNull("description") ?"" : jsonObject.getString("description");
                String family = jsonObject.isNull("family") ? "" :jsonObject.getString("family");
                String affinity = jsonObject.isNull("affinity") ? "" :jsonObject.getString("affinity");
                String imgUrl = jsonObject.isNull("imgUrl") ? "" :jsonObject.getString("imgUrl");
                String smallImgUrl = jsonObject.isNull("smallImgUrl") ?"" : jsonObject.getString("smallImgUrl");
                int id = jsonObject.isNull("id") ? 0 :jsonObject.getInt("id");
                int energy = jsonObject.isNull("energy") ?0: jsonObject.getInt("energy");
                double hp = jsonObject.isNull("hp") ? 0:jsonObject.getDouble("hp");
                double defence = jsonObject.isNull("defence") ?0: jsonObject.getDouble("defence");
                double attack = jsonObject.isNull("attack") ? 0:jsonObject.getDouble("attack");
                double price = jsonObject.isNull("price") ? 0:jsonObject.getDouble("price");
                int userId = jsonObject.isNull("userId") ? 0 : jsonObject.getInt("userId");


                Card card = new Card(name, description, family, affinity, imgUrl, smallImgUrl, id, energy, hp, defence, attack, price, userId);
                cardList.add(card);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Card getRandomCard() {
        int index = randomGenerator.nextInt(cardList.size());
        return cardList.get(index);
    }

}
