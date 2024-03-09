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
        repository.saveAll(List.of(
                        new Courier("Mehmet", "Demir", "+905325322222", VehicleTypeEnum.MOTORCYCLE),
                        new Courier("Zeynep", "Kaya", "+905325322222", VehicleTypeEnum.CAR)
                )
        );
    }

    public List<Courier> getCouriers() {
        return repository.findAll();
    }

    public Courier getCourierById(Long id) {
        return repository.findById(id).orElseThrow();
    }

}
