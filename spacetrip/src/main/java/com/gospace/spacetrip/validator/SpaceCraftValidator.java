package com.gospace.spacetrip.validator;

import com.gospace.spacetrip.domain.Manufacturer;
import com.gospace.spacetrip.domain.SpaceCraft;
import com.gospace.spacetrip.dto.SpaceCraftDto;
import com.gospace.spacetrip.service.SpaceCraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.gospace.spacetrip.util.ServletUtil.isPutRequest;
import static java.util.Objects.isNull;

/**
 * @author rumidipto
 * @since 3/23/24
 */
@RequiredArgsConstructor
@Component
public class SpaceCraftValidator implements Validator {

    private final SpaceCraftService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return SpaceCraftDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SpaceCraftDto spaceCraftDto = (SpaceCraftDto) target;

        if (isPutRequest()) {
            validateSpaceCraft(spaceCraftDto, errors);
        }

        validateName(spaceCraftDto, errors);

        if (isNull(Manufacturer.fromLabel(spaceCraftDto.getManufacturer()))) {
            errors.rejectValue("manufacturer",
                    "valid.spacecraft.not.exists.manufacture.name",
                    "No manufacturer exists with the given name");
        }
    }

    private void validateSpaceCraft(SpaceCraftDto spaceCraftDto, Errors errors) {
        SpaceCraft spaceCraft = service.find(spaceCraftDto.getId());

        if (isNull(spaceCraft)) {
            errors.reject("valid.spacecraft.data", "Space Craft not found by the given identifier");
            return;
        }

        if (spaceCraft.getVersion() != spaceCraftDto.getVersion()) {
            errors.rejectValue("version", "valid.version", "The data is already modified");
        }
    }

    private void validateName(SpaceCraftDto spaceCraftDto, Errors errors) {
        boolean hasDuplicateName = service.findAll()
                .stream()
                .anyMatch(spaceCraft -> spaceCraft.getId() != spaceCraftDto.getId()
                        && spaceCraft.getName().equalsIgnoreCase(spaceCraftDto.getName()));

        if (hasDuplicateName) {
            errors.rejectValue("name",
                    "valid.spacecraft.duplicate.name",
                    "The given spacecraft name is already being used");
        }
    }
}
