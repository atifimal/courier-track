package com.atifimal.demo.couriertrack.domain.courier_track.model.dto;

import com.atifimal.demo.couriertrack.common.model.LatLng;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierTrackResponse extends LatLng {
    private Long courierId;
}
