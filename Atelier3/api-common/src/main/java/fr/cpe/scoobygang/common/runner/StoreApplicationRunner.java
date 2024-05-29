package fr.cpe.scoobygang.common.runner;


import fr.cpe.scoobygang.common.model.Store;
import fr.cpe.scoobygang.common.repository.StoreRepository;
import fr.cpe.scoobygang.common.resource.StoreResource;
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
    private final StoreRepository storeRepository;

    public StoreApplicationRunner(StoreResource storeResource, StoreRepository storeRepository) {
        this.storeResource = storeResource;
        this.storeRepository = storeRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (storeRepository.count() > 0)
            return;

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

        storeRepository.saveAll(stores);
    }
}
