package com.gospace.spacetrip.repository;

import com.gospace.spacetrip.domain.SpaceTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rumidipto
 * @since 3/17/24
 */
@Repository
public interface SpaceTripRepository extends JpaRepository<SpaceTrip, Integer> {

    List<SpaceTrip> findByAvailableSeatsIsGreaterThanEqual(int minimumAvailableSeat);
}
