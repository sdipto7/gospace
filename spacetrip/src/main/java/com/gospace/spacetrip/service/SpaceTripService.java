package com.gospace.spacetrip.service;

import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.repository.SpaceTripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@RequiredArgsConstructor
@Service
public class SpaceTripService {

    private final int MINIMUM_SEAT_COUNT_FOR_AVAILABLE_TRIP = 1;

    private final SpaceTripRepository repository;

    public SpaceTrip find(int id) {
        Optional<SpaceTrip> optionalSpaceTrip = repository.findById(id);

        return optionalSpaceTrip.isPresent() ? optionalSpaceTrip.get() : null;
    }

    public List<SpaceTrip> findAvailableSpaceTrips() {
        return repository.findByAvailableSeatsIsGreaterThanEqual(MINIMUM_SEAT_COUNT_FOR_AVAILABLE_TRIP);
    }

    public List<SpaceTrip> findAll() {
        return repository.findAll();
    }

    @Transactional
    public SpaceTrip saveOrUpdate(SpaceTrip spaceTrip) {
        return repository.save(spaceTrip);
    }

    @Transactional
    public void delete(SpaceTrip spaceTrip) {
        repository.delete(spaceTrip);
    }
}