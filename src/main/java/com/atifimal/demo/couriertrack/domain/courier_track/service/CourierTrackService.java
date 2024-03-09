package com.atifimal.demo.couriertrack.domain.courier_track.service;

import com.atifimal.demo.couriertrack.common.model.MarketStore;
import com.atifimal.demo.couriertrack.domain.courier_track.entity.CourierTrack;
import com.atifimal.demo.couriertrack.domain.courier_track.repository.CourierTrackRepository;
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
    private final CourierTrackRepository repository;
    private final ResourceLoader resourceLoader;

    public List<CourierTrack> getCourierTracks() {
        return repository.findAll();
    }

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
