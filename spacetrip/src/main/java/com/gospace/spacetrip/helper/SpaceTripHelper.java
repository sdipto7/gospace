package com.gospace.spacetrip.helper;

import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.dto.SpaceTripDetailsDto;
import com.gospace.spacetrip.dto.SpaceTripDto;
import com.gospace.spacetrip.proxy.ExplorationProxy;
import com.gospace.spacetrip.proxy.dto.DestinationDto;
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

    private final SpaceCraftHelper spaceCraftHelper;

    private final ExplorationProxy explorationProxy;

    public SpaceTripDto getSpaceTripDto(SpaceTrip spaceTrip) {

        return SpaceTripDto.builder()
                .destinationName(spaceTrip.getDestinationName())
                .spaceCraftDto(spaceCraftHelper.getSpaceCraftDto(spaceTrip.getSpaceCraft()))
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
        DestinationDto destinationDto = explorationProxy.show(spaceTrip.getDestinationId()).getBody();

        return SpaceTripDetailsDto.builder()
                .spaceCraftDto(spaceCraftHelper.getSpaceCraftDto(spaceTrip.getSpaceCraft()))
                .destinationDto(destinationDto)
                .departureTime(spaceTrip.getDepartureTime())
                .estimatedArrivalTime(spaceTrip.getEstimatedArrivalTime())
                .tripDuration(getFormattedDateTimeDifference(spaceTrip.getDepartureTime(), spaceTrip.getEstimatedArrivalTime()))
                .ticketPrice(spaceTrip.getTicketPrice())
                .totalSeats(spaceTrip.getTotalSeats())
                .availableSeats(spaceTrip.getAvailableSeats())
                .build();
    }
}
