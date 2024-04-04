package com.gospace.exploration.repository;

import com.gospace.exploration.domain.CelestialBodyType;
import com.gospace.exploration.domain.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rumidipto
 * @since 3/25/24
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {

    List<Destination> findAllByCelestialBodyType(CelestialBodyType celestialBodyType);
}
