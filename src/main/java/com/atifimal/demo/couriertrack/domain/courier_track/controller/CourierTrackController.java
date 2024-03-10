package com.atifimal.demo.couriertrack.domain.courier_track.controller;

import com.atifimal.demo.couriertrack.domain.courier_track.model.dto.CourierTrackRequest;
import com.atifimal.demo.couriertrack.domain.courier_track.model.dto.CourierTrackResponse;
import com.atifimal.demo.couriertrack.domain.courier_track.service.CourierTrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("courier-track")
@RequiredArgsConstructor
public class CourierTrackController {
    private final CourierTrackService service;

    @GetMapping("/{courierId}")
    private ResponseEntity<List<CourierTrackResponse>> getCourierTracks(@PathVariable Long courierId) throws IOException {
        return service.getCourierTracks(courierId);
    }

    @GetMapping("total-distance/{courierId}")
    private ResponseEntity<Double> getTotalTravelDistance(@PathVariable Long courierId) throws IOException {
        return service.getTotalTravelDistance(courierId);
    }

    @PostMapping
    private ResponseEntity<CourierTrackResponse> saveCourierTrack(@RequestBody @Valid CourierTrackRequest request) throws IOException {
        return service.saveCourierTrack(request);
    }
}
