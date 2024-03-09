package com.atifimal.demo.couriertrack.domain.courier_track.entity;

import com.atifimal.demo.couriertrack.common.entity.BaseEntity;
import com.atifimal.demo.couriertrack.domain.courier.entity.Courier;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "courier_track", schema = "public")
public class CourierTrack extends BaseEntity {

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    @Column(name = "lat")
    private BigDecimal lat;

    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    @Column(name = "lng")
    private BigDecimal lng;

    @Column(name = "record_time")
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id", referencedColumnName = "id")
    private Courier courier;
}
