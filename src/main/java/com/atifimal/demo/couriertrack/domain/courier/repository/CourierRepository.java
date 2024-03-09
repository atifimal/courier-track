package com.atifimal.demo.couriertrack.domain.courier.repository;

import com.atifimal.demo.couriertrack.domain.courier.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
}
