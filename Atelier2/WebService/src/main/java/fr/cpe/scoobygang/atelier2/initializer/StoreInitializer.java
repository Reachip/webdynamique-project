package fr.cpe.scoobygang.atelier2.initializer;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Store;
import fr.cpe.scoobygang.atelier2.resource.CardResource;
import fr.cpe.scoobygang.atelier2.resource.StoreResource;
import fr.cpe.scoobygang.atelier2.service.CardService;
import fr.cpe.scoobygang.atelier2.service.StoreService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreInitializer implements Initializer {
    private final StoreResource storeResource;
    private final StoreService storeService;
    public StoreInitializer(StoreResource storeResource, StoreService storeService) {
        this.storeResource = storeResource;
        this.storeService = storeService;
    }

    @Override
    public void initialize() {
        List<Store> stores = new ArrayList<>();

        try {
            File file = storeResource.load().getFile();
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Store store = Store.toStore(jsonObject);
                stores.add(store);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        storeService.saveStores(stores);
    }
}
