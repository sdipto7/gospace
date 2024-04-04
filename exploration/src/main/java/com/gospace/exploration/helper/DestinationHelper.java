package com.gospace.exploration.helper;

import com.gospace.exploration.domain.CelestialBodyType;
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

    public DestinationDto getDtoFromDestination(Destination destination) {

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

    public Destination getDestinationFromDto(DestinationDto destinationDto) {

        return Destination.builder()
                .name(destinationDto.getName())
                .celestialBodyType(CelestialBodyType.fromLabel(destinationDto.getCelestialBodyType()))
                .description(destinationDto.getDescription())
                .surfaceFeatures(destinationDto.getSurfaceFeatures())
                .atmosphere(destinationDto.getAtmosphere())
                .distanceFromEarth(destinationDto.getDistanceFromEarth())
                .diameter(destinationDto.getDiameter())
                .mass(destinationDto.getMass())
                .gravity(destinationDto.getGravity())
                .minimumTemperature(destinationDto.getMinimumTemperature())
                .maximumTemperature(destinationDto.getMaximumTemperature())
                .build();
    }

    public List<DestinationDto> getDtoListFromDestinationList(List<Destination> destinationList) {

        return destinationList.stream()
                .map(destination -> getDtoFromDestination(destination))
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(Destination destination, DestinationDto destinationDto) {
        destination.setName(destinationDto.getName());
        destination.setCelestialBodyType(CelestialBodyType.fromLabel(destinationDto.getCelestialBodyType()));
        destination.setDescription(destinationDto.getDescription());
        destination.setSurfaceFeatures(destinationDto.getSurfaceFeatures());
        destination.setDistanceFromEarth(destinationDto.getDistanceFromEarth());
        destination.setDiameter(destinationDto.getDiameter());
        destination.setMass(destinationDto.getMass());
        destination.setGravity(destinationDto.getGravity());
        destination.setMinimumTemperature(destinationDto.getMinimumTemperature());
        destination.setMaximumTemperature(destinationDto.getMaximumTemperature());
        destination.setVersion(destinationDto.getVersion());
    }
}
