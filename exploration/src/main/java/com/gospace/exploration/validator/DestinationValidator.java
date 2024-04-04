package com.gospace.exploration.validator;

import com.gospace.exploration.domain.CelestialBodyType;
import com.gospace.exploration.domain.Destination;
import com.gospace.exploration.dto.DestinationDto;
import com.gospace.exploration.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.gospace.exploration.util.ServletUtil.isPutRequest;
import static java.util.Objects.isNull;

/**
 * @author rumidipto
 * @since 3/26/24
 */
@RequiredArgsConstructor
@Component
public class DestinationValidator implements Validator {

    private final DestinationService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return DestinationDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DestinationDto destinationDto = (DestinationDto) target;

        if (isPutRequest()) {
            validateDestination(destinationDto, errors);
        }

        validateName(destinationDto, errors);

        if (isNull(CelestialBodyType.fromLabel(destinationDto.getCelestialBodyType()))) {
            errors.rejectValue("celestialBodyType",
                    "valid.destination.not.exists.celestial.body.type.name",
                    "No celestial body type exists with the given name");
        }
    }

    private void validateDestination(DestinationDto destinationDto, Errors errors) {
        Destination destination = service.find(destinationDto.getId());

        if (isNull(destination)) {
            errors.reject("valid.destination.data", "Destination not found by the given identifier");
            return;
        }

        if (destination.getVersion() != destinationDto.getVersion()) {
            errors.rejectValue("version", "valid.version", "The data is already modified");
        }
    }

    private void validateName(DestinationDto destinationDto, Errors errors) {
        boolean hasDuplicateName = service.findAll()
                .stream()
                .anyMatch(destination -> destination.getId() != destinationDto.getId()
                        && destination.getName().equalsIgnoreCase(destinationDto.getName()));

        if (hasDuplicateName) {
            errors.rejectValue("name",
                    "valid.destination.duplicate.name",
                    "The given destination name is already being used");
        }
    }
}
