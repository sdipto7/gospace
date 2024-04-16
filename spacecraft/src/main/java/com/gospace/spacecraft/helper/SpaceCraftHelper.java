package com.gospace.spacecraft.helper;

import com.gospace.spacecraft.domain.SpaceCraft;
import com.gospace.spacecraft.dto.SpaceCraftDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@Component
public class SpaceCraftHelper {

    public SpaceCraftDto getSpaceCraftDto(SpaceCraft spaceCraft) {

        return SpaceCraftDto.builder()
                .name(spaceCraft.getName())
                .manufacturer(spaceCraft.getManufacturer().getLabel())
                .manufactureDate(spaceCraft.getManufactureDate())
                .crewCapacity(spaceCraft.getCrewCapacity())
                .passengerCapacity(spaceCraft.getPassengerCapacity())
                .build();
    }

    public List<SpaceCraftDto> getSpaceCraftDtoList(List<SpaceCraft> spaceCraftList) {

        return spaceCraftList.stream()
                .map(spaceCraft -> getSpaceCraftDto(spaceCraft))
                .collect(toList());
    }
}
