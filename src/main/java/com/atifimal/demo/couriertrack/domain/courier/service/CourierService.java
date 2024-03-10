package com.atifimal.demo.couriertrack.domain.courier.service;

import com.atifimal.demo.couriertrack.domain.courier.entity.Courier;
import com.atifimal.demo.couriertrack.domain.courier.repository.CourierRepository;
import com.atifimal.demo.couriertrack.domain.courier_track.model.enums.VehicleTypeEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository repository;

    @PostConstruct
    public void init() {
        // Initial save method, added couriers for testing
        repository.saveAll(List.of(
                        new Courier("Mehmet", "Demir", "+905325321111", VehicleTypeEnum.MOTORCYCLE),
                        new Courier("Zeynep", "Kaya", "+905325322222", VehicleTypeEnum.MOTORCYCLE),
                        new Courier("Omer", "Yilmaz", "+905325323333", VehicleTypeEnum.CAR)
                )
        );
    }

    public Courier getCourierById(Long id) {
        return repository.findById(id).orElseThrow();
    }

}
