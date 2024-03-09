package com.atifimal.demo.couriertrack.domain.courier_track.controller;

import com.atifimal.demo.couriertrack.domain.courier_track.service.CourierTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("courier")
@RequiredArgsConstructor
public class CourierTrackController {
    private final CourierTrackService service;

}
