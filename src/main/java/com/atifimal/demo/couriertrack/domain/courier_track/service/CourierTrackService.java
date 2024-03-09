package com.atifimal.demo.couriertrack.domain.courier_track.service;

import com.atifimal.demo.couriertrack.common.model.MarketStore;
import com.atifimal.demo.couriertrack.domain.courier.service.CourierService;
import com.atifimal.demo.couriertrack.domain.courier_track.entity.CourierTrack;
import com.atifimal.demo.couriertrack.common.model.LatLng;
import com.atifimal.demo.couriertrack.domain.courier_track.model.dto.CourierTrackRequest;
import com.atifimal.demo.couriertrack.domain.courier_track.model.dto.CourierTrackResponse;
import com.atifimal.demo.couriertrack.domain.courier_track.model.enums.StatusEnum;
import com.atifimal.demo.couriertrack.domain.courier_track.repository.CourierTrackRepository;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourierTrackService {
    private final CourierTrackRepository repository;
    private final ResourceLoader resourceLoader;
    private final ModelMapper mapper;
    private final CourierService courierService;

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
        var courierLastRecord = repository.findFirstByCourierIdOrderByIdDesc(request.getCourierId())
                .map(courierTrack1 -> mapper.map(courierTrack1, LatLng.class))
                .orElse(null);

        courierTrack.setCourier(courierService.getCourierById(request.getCourierId()));
        Optional.ofNullable(courierLastRecord).ifPresent(latLng -> courierTrack.setTravelDistance(coordinateDiffAsKm(latLng, requestLatLng)));
        Optional.ofNullable(enteredMarketStore).ifPresent(marketStore -> {
            courierTrack.setStatus(StatusEnum.INSIDE);
            courierTrack.setStoreLat(marketStore.getLat());
            courierTrack.setStoreLng(marketStore.getLng());
            if (isLastEntranceBiggerThan(courierTrack, 1)) {
                var courier = courierTrack.getCourier();
                log.info("COURIER ENTRANCE - Courier: {} (id: {}), Store: {}, Time: {}",
                        courier.getFirstName().concat(StringUtils.SPACE).concat(courier.getLastName()).trim(),
                        courier.getId(),
                        marketStore.getName(),
                        courierTrack.getTime());
            }
        });

        return new ResponseEntity<>(mapper.map(repository.save(courierTrack), CourierTrackResponse.class), HttpStatus.OK);
    }

    public ResponseEntity<Double> getTotalTravelDistance(Long courierId) {
        var totalDist = repository.findAllByCourierId(courierId).stream().mapToDouble(CourierTrack::getTravelDistance).sum();

        return new ResponseEntity<>(BigDecimal.valueOf(totalDist).setScale(2, RoundingMode.HALF_EVEN).doubleValue(), HttpStatus.OK);
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
                StatusEnum.INSIDE, courierTrack.getStoreLat(),
                courierTrack.getStoreLng()
        ).orElse(null);
        if (lastEntranceToStore != null)
            return minute < Duration.between(courierTrack.getTime(), lastEntranceToStore.getTime()).toMinutes();
        return true;
    }

    public static boolean isCoordsDiffLessThan(LatLng latLng1, LatLng latLng2, double distance) {
        return coordinateDiffAsKm(latLng1, latLng2) <= distance / 1000;
    }

    public static double coordinateDiffAsKm(LatLng latLng1, LatLng latLng2) {
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

        return EARTH_RADIUS_KM * c;
    }
}
