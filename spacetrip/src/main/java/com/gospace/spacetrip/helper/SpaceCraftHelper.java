package com.gospace.spacetrip.helper;

import com.gospace.spacetrip.domain.Manufacturer;
import com.gospace.spacetrip.domain.SpaceCraft;
import com.gospace.spacetrip.dto.SpaceCraftDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@Component
public class SpaceCraftHelper {

    public SpaceCraftDto getDtoFromSpaceCraft(SpaceCraft spaceCraft) {

        return SpaceCraftDto.builder()
                .name(spaceCraft.getName())
                .manufacturer(spaceCraft.getManufacturer().getLabel())
                .manufactureDate(spaceCraft.getManufactureDate())
                .crewCapacity(spaceCraft.getCrewCapacity())
                .passengerCapacity(spaceCraft.getPassengerCapacity())
                .build();
    }

    public SpaceCraft getSpaceCraftFromDto(SpaceCraftDto spaceCraftDto) {

        return SpaceCraft.builder()
                .name(spaceCraftDto.getName())
                .manufacturer(Manufacturer.fromLabel(spaceCraftDto.getManufacturer()))
                .manufactureDate(spaceCraftDto.getManufactureDate())
                .crewCapacity(spaceCraftDto.getCrewCapacity())
                .passengerCapacity(spaceCraftDto.getPassengerCapacity())
                .build();
    }

    public List<SpaceCraftDto> getDtoListFromSpaceCraftList(List<SpaceCraft> spaceCraftList) {

        return spaceCraftList.stream()
                .map(spaceCraft -> getDtoFromSpaceCraft(spaceCraft))
                .collect(toList());
    }

    public void updateEntityFromDto(SpaceCraft spaceCraft, SpaceCraftDto spaceCraftDto) {
        spaceCraft.setName(spaceCraftDto.getName());
        spaceCraft.setManufacturer(Manufacturer.fromLabel(spaceCraftDto.getManufacturer()));
        spaceCraft.setManufactureDate(spaceCraftDto.getManufactureDate());
        spaceCraft.setCrewCapacity(spaceCraftDto.getCrewCapacity());
        spaceCraft.setPassengerCapacity(spaceCraftDto.getPassengerCapacity());
        spaceCraft.setVersion(spaceCraftDto.getVersion());
    }
}