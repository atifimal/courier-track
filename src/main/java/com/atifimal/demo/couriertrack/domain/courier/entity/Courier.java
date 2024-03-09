package com.atifimal.demo.couriertrack.domain.courier.entity;

import com.atifimal.demo.couriertrack.common.entity.BaseEntity;
import com.atifimal.demo.couriertrack.domain.courier_track.model.enums.VehicleTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courier", schema = "public")
public class Courier extends BaseEntity {

    @Column(name = "first_name")
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 50)
    private String lastName;

    @Column(name = "phone_number")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$")
    private String phoneNumber;

    @Column(name = "vehicle_type")
    @Enumerated(value = EnumType.STRING)
    private VehicleTypeEnum vehicle_type;
}
