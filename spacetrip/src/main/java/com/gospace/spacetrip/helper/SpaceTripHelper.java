package com.gospace.spacetrip.helper;

import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.dto.SpaceTripDetailsDto;
import com.gospace.spacetrip.dto.SpaceTripDto;
import com.gospace.spacetrip.proxy.ExplorationProxy;
import com.gospace.spacetrip.proxy.SpaceCraftProxy;
import com.gospace.spacetrip.proxy.dto.DestinationDto;
import com.gospace.spacetrip.proxy.dto.SpaceCraftDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gospace.spacetrip.util.LocalDateTimeUtil.getFormattedDateTimeDifference;
import static java.util.stream.Collectors.toList;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@RequiredArgsConstructor
@Component
public class SpaceTripHelper {

    private final ExplorationProxy explorationProxy;

    private final SpaceCraftProxy spaceCraftProxy;

    public SpaceTripDto getSpaceTripDto(SpaceTrip spaceTrip) {

        return SpaceTripDto.builder()
                .destinationName(spaceTrip.getDestinationName())
                .spaceCraftName(spaceTrip.getSpaceCraftName())
                .departureTime(spaceTrip.getDepartureTime())
                .estimatedArrivalTime(spaceTrip.getEstimatedArrivalTime())
                .tripDuration(getFormattedDateTimeDifference(spaceTrip.getDepartureTime(), spaceTrip.getEstimatedArrivalTime()))
                .ticketPrice(spaceTrip.getTicketPrice())
                .totalSeats(spaceTrip.getTotalSeats())
                .availableSeats(spaceTrip.getAvailableSeats())
                .build();
    }

    public List<SpaceTripDto> getSpaceTripDtoList(List<SpaceTrip> availableTripList) {

        return availableTripList.stream()
                .map(spaceTrip -> getSpaceTripDto(spaceTrip))
                .collect(toList());
    }

    public SpaceTripDetailsDto getSpaceTripDetailsDto(SpaceTrip spaceTrip) {
        DestinationDto destinationDto = explorationProxy.getDestinationDto(spaceTrip.getDestinationId()).getBody();
        SpaceCraftDto spaceCraftDto = spaceCraftProxy.getSpaceCraftDto(spaceTrip.getSpaceCraftId()).getBody();

        return SpaceTripDetailsDto.builder()
                .destinationDto(destinationDto)
                .spaceCraftDto(spaceCraftDto)
                .departureTime(spaceTrip.getDepartureTime())
                .estimatedArrivalTime(spaceTrip.getEstimatedArrivalTime())
                .tripDuration(getFormattedDateTimeDifference(spaceTrip.getDepartureTime(), spaceTrip.getEstimatedArrivalTime()))
                .ticketPrice(spaceTrip.getTicketPrice())
                .totalSeats(spaceTrip.getTotalSeats())
                .availableSeats(spaceTrip.getAvailableSeats())
                .build();
    }
}
