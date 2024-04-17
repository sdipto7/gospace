package com.gospace.spacetrip.validator;

import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.dto.SpaceTripDto;
import com.gospace.spacetrip.proxy.ExplorationProxy;
import com.gospace.spacetrip.proxy.SpaceCraftProxy;
import com.gospace.spacetrip.proxy.dto.SpaceCraftDto;
import com.gospace.spacetrip.service.SpaceTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.gospace.spacetrip.util.ServletUtil.isPutRequest;
import static java.lang.Boolean.FALSE;
import static java.util.Objects.isNull;

/**
 * @author rumidipto
 * @since 3/21/24
 */
@RequiredArgsConstructor
@Component
public class SpaceTripValidator implements Validator {

    private final SpaceTripService service;

    private final ExplorationProxy explorationProxy;

    private final SpaceCraftProxy spaceCraftProxy;

    @Override
    public boolean supports(Class<?> clazz) {
        return SpaceTripDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SpaceTripDto spaceTripDto = (SpaceTripDto) target;

        if (isPutRequest()) {
            validateSpaceTrip(spaceTripDto, errors);
        }

        Boolean hasDestination = explorationProxy.hasDestination(spaceTripDto.getDestinationId()).getBody();
        if (spaceTripDto.getDestinationId() == 0 || FALSE.equals(hasDestination)) {
            errors.rejectValue("destinationId",
                    "valid.spacetrip.invalid.destination.id",
                    "Please provide a valid Destination ID! Destination not found by the given identifier");
            return;
        }

        Boolean hasSpaceCraft = spaceCraftProxy.hasSpaceCraft(spaceTripDto.getSpaceCraftId()).getBody();
        if (spaceTripDto.getSpaceCraftId() == 0 || FALSE.equals(hasSpaceCraft)) {
            errors.rejectValue("spaceCraftId",
                    "valid.spacetrip.invalid.spacecraft.id",
                    "Please provide a valid Spacecraft ID! SpaceCraft not found by the given identifier");
            return;
        }

        validateDuplicateSpaceCraft(spaceTripDto, errors);

        validateDateRange(spaceTripDto, errors);

        validateSeatCapacity(spaceTripDto, errors);
    }

    private void validateSpaceTrip(SpaceTripDto spaceTripDto, Errors errors) {
        SpaceTrip spaceTrip = service.find(spaceTripDto.getId());

        if (isNull(spaceTrip)) {
            errors.reject("valid.spacetrip.data", "Space Trip not found by the given identifier");
            return;
        }

        if (spaceTrip.getVersion() != spaceTripDto.getVersion()) {
            errors.rejectValue("version", "valid.version", "The data is already modified");
        }
    }

    private void validateDateRange(SpaceTripDto spaceTripDto, Errors errors) {
        if (errors.hasFieldErrors("departureTime") || errors.hasFieldErrors("estimatedArrivalTime")) {
            return;
        }

        if (!errors.hasFieldErrors("departureTime")
                && !errors.hasFieldErrors("estimatedArrivalTime")
                && !spaceTripDto.getDepartureTime().isBefore(spaceTripDto.getEstimatedArrivalTime())) {

            errors.reject("valid.spacetrip.invalid.date.range",
                    "Estimated Arrival Time cannot be earlier than Departure Time");
        }
    }

    private void validateDuplicateSpaceCraft(SpaceTripDto spaceTripDto, Errors errors) {
        boolean hasDuplicateSpaceCraft = service.findAll()
                .stream()
                .anyMatch(spaceTrip -> spaceTrip.getId() != spaceTripDto.getId()
                        && spaceTrip.getSpaceCraftId() == spaceTripDto.getSpaceCraftId());

        if (hasDuplicateSpaceCraft) {
            errors.rejectValue("spaceCraftId",
                    "valid.spacetrip.duplicate.spacecraft",
                    "The given spacecraft id is already being used by another space trip");
        }
    }

    private void validateSeatCapacity(SpaceTripDto spaceTripDto, Errors errors) {
        SpaceCraftDto spaceCraftDto = spaceCraftProxy.getSpaceCraftDto(spaceTripDto.getSpaceCraftId()).getBody();

        if (spaceTripDto.getTotalSeats() != spaceCraftDto.getPassengerCapacity()) {
            errors.rejectValue("totalSeats", "valid.spacetrip.total.seats",
                    "Total seat of a space trip must be equal to the passenger capacity of the space craft");
        }

        if (spaceTripDto.getTotalSeats() < spaceTripDto.getAvailableSeats()) {
            errors.reject("valid.spacetrip.invalid.seat.count",
                    "Total seats cannot be less than available seats");
        }
    }
}
