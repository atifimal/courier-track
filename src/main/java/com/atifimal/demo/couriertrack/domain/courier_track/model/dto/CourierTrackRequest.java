package com.atifimal.demo.couriertrack.domain.courier_track.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierTrackRequest {
    @NotNull(message = "courier_id_must_not_be_null")
    private Long courierId;
    @NotNull(message = "latitude_must_not_be_null")
    private Double lat;
    @NotNull(message = "longitude_must_not_be_null")
    private Double lng;
}
