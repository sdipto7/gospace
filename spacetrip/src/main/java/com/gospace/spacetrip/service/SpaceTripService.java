package com.gospace.spacetrip.service;

import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.dto.SpaceTripDto;
import com.gospace.spacetrip.proxy.ExplorationProxy;
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

    private final SpaceCraftService spaceCraftService;

    private final ExplorationProxy explorationProxy;

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
    public SpaceTrip saveOrUpdate(SpaceTripDto spaceTripDto) {
        SpaceTrip spaceTrip = spaceTripDto.isNew() ? new SpaceTrip() : find(spaceTripDto.getId());

        String destinationName = explorationProxy.getDestinationName(spaceTripDto.getDestinationId()).getBody();
        spaceTrip.setDestinationName(destinationName);
        spaceTrip.setDestinationId(spaceTripDto.getDestinationId());

        spaceTrip.setSpaceCraft(spaceCraftService.find(spaceTripDto.getSpaceCraftDto().getId()));
        spaceTrip.setDepartureTime(spaceTripDto.getDepartureTime());
        spaceTrip.setEstimatedArrivalTime(spaceTripDto.getEstimatedArrivalTime());
        spaceTrip.setTicketPrice(spaceTripDto.getTicketPrice());
        spaceTrip.setTotalSeats(spaceTripDto.getTotalSeats());
        spaceTrip.setAvailableSeats(spaceTripDto.getAvailableSeats());

        return repository.save(spaceTrip);
    }

    @Transactional
    public void delete(SpaceTrip spaceTrip) {
        repository.delete(spaceTrip);
    }
}
