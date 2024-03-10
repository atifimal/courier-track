package com.atifimal.demo.couriertrack.domain.courier_track.repository;

import com.atifimal.demo.couriertrack.domain.courier_track.entity.CourierTrack;
import com.atifimal.demo.couriertrack.domain.courier_track.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierTrackRepository extends JpaRepository<CourierTrack, Long> {
    Optional<CourierTrack> findFirstByCourierIdAndStatusAndStoreLatAndStoreLngOrderByIdDesc(Long courierId, StatusEnum status, Double storeLat, Double storeLng);

    Optional<CourierTrack> findFirstByCourierIdOrderByIdDesc(Long courierId);

    List<CourierTrack> findAllByCourierId(Long courierId);
}
