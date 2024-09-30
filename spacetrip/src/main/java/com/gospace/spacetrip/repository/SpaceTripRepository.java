package com.gospace.spacetrip.repository;

import com.gospace.spacetrip.domain.SpaceTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author rumidipto
 * @since 3/17/24
 */
@Repository
public interface SpaceTripRepository extends JpaRepository<SpaceTrip, Integer> {

    Optional<SpaceTrip> findFirstBySpaceCraftId(int spaceCraftId);

    List<SpaceTrip> findByAvailableSeatsIsGreaterThanEqual(int minimumAvailableSeat);
}
