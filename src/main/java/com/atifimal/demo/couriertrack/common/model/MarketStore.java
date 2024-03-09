package com.atifimal.demo.couriertrack.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class MarketStore {
    private String name;
    private BigDecimal lat;
    private BigDecimal lng;
}
