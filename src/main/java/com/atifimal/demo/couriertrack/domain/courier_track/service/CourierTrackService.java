package com.atifimal.demo.couriertrack.domain.courier_track.service;

import com.atifimal.demo.couriertrack.common.model.MarketStore;
import com.atifimal.demo.couriertrack.domain.courier.service.CourierService;
import com.atifimal.demo.couriertrack.domain.courier_track.entity.CourierTrack;
import com.atifimal.demo.couriertrack.domain.courier_track.model.LatLng;
import com.atifimal.demo.couriertrack.domain.courier_track.model.dto.CourierTrackRequest;
import com.atifimal.demo.couriertrack.domain.courier_track.model.dto.CourierTrackResponse;
import com.atifimal.demo.couriertrack.domain.courier_track.model.enums.StatusEnum;
import com.atifimal.demo.couriertrack.domain.courier_track.repository.CourierTrackRepository;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierTrackService {
    private final CourierTrackRepository repository;
    private final ResourceLoader resourceLoader;
    private final ModelMapper mapper;
    private final CourierService courierService;

    public static boolean isCoordsDiffLessThan(LatLng latLng1, LatLng latLng2, double distance) {
        int EARTH_RADIUS_KM = 6371;
        double lat1 = latLng1.getLat().doubleValue();
        double lat2 = latLng2.getLat().doubleValue();
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(latLng2.getLng().doubleValue() - latLng1.getLng().doubleValue());

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c * 1000 <= distance;
    }

    @PostConstruct
    private void init() {
        mapper.addMappings(new PropertyMap<CourierTrack, CourierTrackResponse>() {
            @Override
            protected void configure() {
                map().setCourierId(source.getCourier().getId());
            }
        });
    }

    public ResponseEntity<List<CourierTrackResponse>> getCourierTracks() {
        return new ResponseEntity<>(repository.findAll().stream()
                .map(courierTrack -> mapper.map(courierTrack, CourierTrackResponse.class))
                .toList(),
                HttpStatus.OK);
    }

    public ResponseEntity<CourierTrackResponse> saveCourierTrack(CourierTrackRequest request) throws IOException {
        var marketStores = getMarketStores();
        var requestLatLng = mapper.map(request, LatLng.class);
        MarketStore enteredMarketStore = null;

        for (MarketStore marketStore : marketStores) {
            var storeLatLng = mapper.map(marketStore, LatLng.class);
            if (isCoordsDiffLessThan(requestLatLng, storeLatLng, 100)) {
                enteredMarketStore = marketStore;
                break;
            }
        }

        var courierTrack = mapper.map(request, CourierTrack.class);
        courierTrack.setCourier(courierService.getCourierById(request.getCourierId()));
        Optional.ofNullable(enteredMarketStore).ifPresent(marketStore -> {
            courierTrack.setStatus(StatusEnum.INSIDE);
            courierTrack.setStoreLat(marketStore.getLat());
            courierTrack.setLng(marketStore.getLng());
            if (isLastEntranceBiggerThan(courierTrack, 1)) {
                // LOG
            }
        });

        return new ResponseEntity<>(mapper.map(repository.save(courierTrack), CourierTrackResponse.class), HttpStatus.OK);
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
            return Arrays.stream(gson.fromJson(jsonData.toString(), MarketStore[].class)).toList();
        }
    }

    public boolean isLastEntranceBiggerThan(CourierTrack courierTrack, Integer minute) {
        var lastEntranceToStore = repository.findFirstByCourierIdAndStatusAndStoreLatAndStoreLngOrderByIdDesc(
                courierTrack.getCourier().getId(),
                courierTrack.getStatus(), courierTrack.getStoreLat(),
                courierTrack.getStoreLng()
        ).orElse(null);
        if (lastEntranceToStore != null)
            return minute > ChronoUnit.MINUTES.between(courierTrack.getTime(), lastEntranceToStore.getCreationDateTime());
        return false;
    }
}
