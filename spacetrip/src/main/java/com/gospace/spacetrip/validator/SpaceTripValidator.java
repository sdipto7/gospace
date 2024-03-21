package com.gospace.spacetrip.validator;

import com.gospace.spacetrip.domain.SpaceCraft;
import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.dto.SpaceTripDto;
import com.gospace.spacetrip.service.SpaceCraftService;
import com.gospace.spacetrip.service.SpaceTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.gospace.spacetrip.util.ServletUtil.isPutRequest;
import static java.util.Objects.isNull;

/**
 * @author rumidipto
 * @since 3/21/24
 */
@RequiredArgsConstructor
@Component
public class SpaceTripValidator implements Validator {

    private final SpaceTripService service;

    private final SpaceCraftService spaceCraftService;

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

        validateDateRange(spaceTripDto, errors);

        validateSpaceCraft(spaceTripDto, errors);

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

    private void validateSpaceCraft(SpaceTripDto spaceTripDto, Errors errors) {
        if (isNull(spaceTripDto.getSpaceCraftDto())) {
            errors.rejectValue("spaceCraftDto",
                    "valid.spacetrip.invalid.spacecraft.data",
                    "Spacecraft cannot be empty");
            return;
        }

        SpaceCraft spaceCraft = spaceCraftService.find(spaceTripDto.getSpaceCraftDto().getId());
        if (isNull(spaceCraft)) {
            errors.rejectValue("spaceCraftDto.id",
                    "valid.spacecraft.data",
                    "SpaceCraft not found by the given identifier");
            return;
        }

        boolean hasDuplicateSpaceCraft = service.findAll()
                .stream()
                .anyMatch(spaceTrip -> spaceTrip.getId() != spaceTripDto.getId()
                        && spaceTrip.getSpaceCraft().getId() == spaceTripDto.getSpaceCraftDto().getId());

        if (hasDuplicateSpaceCraft) {
            errors.rejectValue("spaceCraftDto",
                    "valid.spacetrip.duplicate.spacecraft",
                    "The given spacecraft is already being used by another space trip");
        }
    }

    private void validateSeatCapacity(SpaceTripDto spaceTripDto, Errors errors) {
        SpaceCraft spaceCraft = spaceCraftService.find(spaceTripDto.getSpaceCraftDto().getId());

        if (isNull(spaceCraft)) {
            return;
        }

        if (spaceTripDto.getSpaceCraftDto().getPassengerCapacity() != spaceCraft.getPassengerCapacity()) {
            errors.rejectValue("spaceCraftDto.passengerCapacity",
                    "valid.spacecraft.passenger.seats.mismatch",
                    "The given space craft's passenger seat count does not match with the actual spacecraft's passenger seat count");
            return;
        }

        if (spaceTripDto.getTotalSeats() != spaceCraft.getPassengerCapacity()) {
            errors.rejectValue("totalSeats", "valid.spacetrip.total.seats",
                    "Total seat of a space trip must be equal to the passenger capacity of the space craft");
            return;
        }

        if (spaceTripDto.getTotalSeats() < spaceTripDto.getAvailableSeats()) {
            errors.reject("valid.spacetrip.invalid.seat.count",
                    "Total seats cannot be less than available seats");
        }
    }
}