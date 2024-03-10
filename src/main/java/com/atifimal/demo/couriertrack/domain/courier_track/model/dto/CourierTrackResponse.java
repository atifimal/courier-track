package com.atifimal.demo.couriertrack.domain.courier_track.model.dto;

import com.atifimal.demo.couriertrack.common.model.LatLng;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierTrackResponse extends LatLng {
    private Long courierId;
}
