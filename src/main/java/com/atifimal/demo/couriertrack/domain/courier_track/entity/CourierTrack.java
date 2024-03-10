package com.atifimal.demo.couriertrack.domain.courier_track.entity;

import com.atifimal.demo.couriertrack.common.entity.BaseEntity;
import com.atifimal.demo.couriertrack.domain.courier.entity.Courier;
import com.atifimal.demo.couriertrack.domain.courier_track.model.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courier_track", schema = "public")
public class CourierTrack extends BaseEntity {

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    @Column(name = "lat", columnDefinition = "decimal(10,7)")
    private Double lat;

    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    @Column(name = "lng", columnDefinition = "decimal(10,7)")
    private Double lng;

    @Column(name = "record_time")
    private LocalDateTime time = LocalDateTime.now();

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status = StatusEnum.OUTSIDE;

    @Column(name = "travel_distance")
    private Double travelDistance = (double) 0;

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    @Column(name = "store_lat", columnDefinition = "decimal(10,7)")
    private Double storeLat;

    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    @Column(name = "store_lng", columnDefinition = "decimal(10,7)")
    private Double storeLng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id", referencedColumnName = "id")
    private Courier courier;
}
