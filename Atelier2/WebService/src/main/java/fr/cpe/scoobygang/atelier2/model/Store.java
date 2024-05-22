package fr.cpe.scoobygang.atelier2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static Store toStore(JSONObject jsonObject) {
        Store store = new Store();

        store.setName(jsonObject.getString("name"));

        return store;
    }
}
