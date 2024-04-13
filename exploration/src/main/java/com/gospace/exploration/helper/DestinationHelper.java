package com.gospace.exploration.helper;

import com.gospace.exploration.domain.Destination;
import com.gospace.exploration.dto.DestinationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rumidipto
 * @since 3/26/24
 */
@RequiredArgsConstructor
@Component
public class DestinationHelper {

    public DestinationDto getDestinationDto(Destination destination) {

        return DestinationDto.builder()
                .name(destination.getName())
                .celestialBodyType(destination.getCelestialBodyType().getLabel())
                .description(destination.getDescription())
                .surfaceFeatures(destination.getSurfaceFeatures())
                .atmosphere(destination.getAtmosphere())
                .distanceFromEarth(destination.getDistanceFromEarth().stripTrailingZeros())
                .diameter(destination.getDiameter().stripTrailingZeros())
                .mass(destination.getMass().stripTrailingZeros())
                .gravity(destination.getGravity().stripTrailingZeros())
                .minimumTemperature(destination.getMinimumTemperature().stripTrailingZeros())
                .maximumTemperature(destination.getMaximumTemperature().stripTrailingZeros())
                .build();
    }

    public List<DestinationDto> getDestinationDtoList(List<Destination> destinationList) {

        return destinationList.stream()
                .map(destination -> getDestinationDto(destination))
                .collect(Collectors.toList());
    }
}
