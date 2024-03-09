package com.atifimal.demo.couriertrack.domain.courier_track.repository;

import com.atifimal.demo.couriertrack.domain.courier_track.entity.CourierTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierTrackRepository extends JpaRepository<CourierTrack, Long> {
}
