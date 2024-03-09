package com.atifimal.demo.couriertrack.domain.courier_track.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierTrackResponse {
    private Long courierId;
    private BigDecimal lat;
    private BigDecimal lng;
}
