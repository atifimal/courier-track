package com.atifimal.demo.couriertrack.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MarketStore extends LatLng {
    private String name;
}
