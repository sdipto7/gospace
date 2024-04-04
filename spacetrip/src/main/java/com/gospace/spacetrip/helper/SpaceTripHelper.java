package com.gospace.spacetrip.helper;

import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.dto.SpaceTripDto;
import com.gospace.spacetrip.service.SpaceCraftService;
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

    private final SpaceCraftService spaceCraftService;

    public SpaceTripDto getDtoFromSpaceTrip(SpaceTrip spaceTrip) {

        return SpaceTripDto.builder()
                .spaceCraftDto(spaceCraftHelper.getDtoFromSpaceCraft(spaceTrip.getSpaceCraft()))
                .departureTime(spaceTrip.getDepartureTime())
                .estimatedArrivalTime(spaceTrip.getEstimatedArrivalTime())
                .tripDuration(getFormattedDateTimeDifference(spaceTrip.getDepartureTime(), spaceTrip.getEstimatedArrivalTime()))
                .ticketPrice(spaceTrip.getTicketPrice())
                .totalSeats(spaceTrip.getTotalSeats())
                .availableSeats(spaceTrip.getAvailableSeats())
                .build();
    }

    public SpaceTrip getSpaceTripFromDto(SpaceTripDto spaceTripDto) {

        return SpaceTrip.builder()
                .spaceCraft(spaceCraftService.find(spaceTripDto.getSpaceCraftDto().getId()))
                .departureTime(spaceTripDto.getDepartureTime())
                .estimatedArrivalTime(spaceTripDto.getEstimatedArrivalTime())
                .ticketPrice(spaceTripDto.getTicketPrice())
                .totalSeats(spaceTripDto.getTotalSeats())
                .availableSeats(spaceTripDto.getAvailableSeats())
                .build();
    }

    public List<SpaceTripDto> getDtoListFromSpaceTripList(List<SpaceTrip> availableTripList) {

        return availableTripList.stream()
                .map(spaceTrip -> getDtoFromSpaceTrip(spaceTrip))
                .collect(toList());
    }

    public void updateEntityFromDto(SpaceTrip spaceTrip, SpaceTripDto spaceTripDto) {
        spaceTrip.setSpaceCraft(spaceCraftService.find(spaceTripDto.getSpaceCraftDto().getId()));
        spaceTrip.setDepartureTime(spaceTripDto.getDepartureTime());
        spaceTrip.setEstimatedArrivalTime(spaceTripDto.getEstimatedArrivalTime());
        spaceTrip.setTicketPrice(spaceTripDto.getTicketPrice());
        spaceTrip.setTotalSeats(spaceTripDto.getTotalSeats());
        spaceTrip.setAvailableSeats(spaceTripDto.getAvailableSeats());
        spaceTrip.setVersion(spaceTripDto.getVersion());
    }
}
