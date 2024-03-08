package com.atifimal.demo.couriertrack.controller;

import com.atifimal.demo.couriertrack.model.MarketStore;
import com.atifimal.demo.couriertrack.service.CourierTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("courier")
@RequiredArgsConstructor
public class CourierController {
    private final CourierTrackService courierTrackService;

}
