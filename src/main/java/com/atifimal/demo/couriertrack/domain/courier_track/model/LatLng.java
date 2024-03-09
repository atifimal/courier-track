package com.atifimal.demo.couriertrack.domain.courier_track.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatLng {
    private BigDecimal lat;
    private BigDecimal lng;
}
