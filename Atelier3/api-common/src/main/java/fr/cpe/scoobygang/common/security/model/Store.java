package fr.cpe.scoobygang.common.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cardList;

    public static Store toStore(JSONObject jsonObject) throws JSONException {
        Store store = new Store();

        store.setName(jsonObject.getString("name"));

        return store;
    }
}
