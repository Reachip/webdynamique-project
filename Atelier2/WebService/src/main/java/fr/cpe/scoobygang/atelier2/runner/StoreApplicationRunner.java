package fr.cpe.scoobygang.atelier2.runner;

import fr.cpe.scoobygang.atelier2.model.Store;
import fr.cpe.scoobygang.atelier2.resource.StoreResource;
import fr.cpe.scoobygang.atelier2.service.StoreService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
public class StoreApplicationRunner implements ApplicationRunner {
    private final StoreResource storeResource;
    private final StoreService storeService;

    public StoreApplicationRunner(StoreResource storeResource, StoreService storeService) {
        this.storeResource = storeResource;
        this.storeService = storeService;
    }

    @Override
    public void run(ApplicationArguments args) {
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
