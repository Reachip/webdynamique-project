package fr.cpe.scoobygang.atelier2.model;

import com.github.javafaker.Faker;
import fr.cpe.scoobygang.atelier2.utils.StringUtils;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    private String surname;
    private String name;
    private String email;
    private String password;
    private double balance = 0;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cardList = new ArrayList<>();

    public static User toUser(JSONObject jsonObject) {
        User user = new User();

        user.setUsername(jsonObject.getString("username"));
        user.setPassword(jsonObject.getString("password"));
        user.setBalance(jsonObject.getDouble("balance"));
        user.setSurname(jsonObject.getString("surname"));
        user.setName(jsonObject.getString("name"));
        user.setEmail(jsonObject.getString("email"));

        return user;
    }

    public static User toUser(Faker faker) {
        User user = new User();

        user.setName(faker.name().firstName());
        user.setSurname(faker.name().lastName());
        user.setPassword("123");

        String username = (user.getName() + "." + user.getSurname()).toLowerCase();

        user.setUsername(username);
        user.setEmail(username + "@cpe.fr");

        return user;
    }

    public boolean canBuy(double price){
        return this.getBalance() >= price;
    }
}
