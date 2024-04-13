package com.gospace.exploration.service;

import com.gospace.exploration.domain.CelestialBodyType;
import com.gospace.exploration.domain.Destination;
import com.gospace.exploration.dto.DestinationDto;
import com.gospace.exploration.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author rumidipto
 * @since 3/25/24
 */
@RequiredArgsConstructor
@Service
public class DestinationService {

    private final DestinationRepository repository;

    public Destination find(int id) {
        Optional<Destination> optionalDestination = repository.findById(id);

        return optionalDestination.isPresent() ? optionalDestination.get() : null;
    }

    public List<Destination> findAllByCelestialBodyType(CelestialBodyType celestialBodyType) {
        return repository.findAllByCelestialBodyType(celestialBodyType);
    }

    public List<Destination> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Destination saveOrUpdate(DestinationDto destinationDto) {
        Destination destination = destinationDto.isNew() ? new Destination() : find(destinationDto.getId());

        destination.setName(destinationDto.getName());
        destination.setCelestialBodyType(CelestialBodyType.fromLabel(destinationDto.getCelestialBodyType()));
        destination.setDescription(destinationDto.getDescription());
        destination.setSurfaceFeatures(destinationDto.getSurfaceFeatures());
        destination.setAtmosphere(destinationDto.getAtmosphere());
        destination.setDistanceFromEarth(destinationDto.getDistanceFromEarth());
        destination.setDiameter(destinationDto.getDiameter());
        destination.setMass(destinationDto.getMass());
        destination.setGravity(destinationDto.getGravity());
        destination.setMinimumTemperature(destinationDto.getMinimumTemperature());
        destination.setMaximumTemperature(destinationDto.getMaximumTemperature());

        return repository.save(destination);
    }

    @Transactional
    public void delete(Destination destination) {
        repository.delete(destination);
    }
}
