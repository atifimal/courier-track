package com.atifimal.demo.couriertrack.service;

import com.atifimal.demo.couriertrack.model.MarketStore;
import com.atifimal.demo.couriertrack.repository.CourierTrackRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierTrackService {
    //private final CourierTrackRepository courierTrackRepository;
    private final ResourceLoader resourceLoader;

    public List<MarketStore> getMarketStores() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:store.json");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }

            Gson gson = new Gson();
            return (List<MarketStore>) gson.fromJson(jsonData.toString(), List.class);
        }
    }
}
