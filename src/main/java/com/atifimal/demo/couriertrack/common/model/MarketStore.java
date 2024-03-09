package com.atifimal.demo.couriertrack.common.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MarketStore extends LatLng {
    private String name;
}
