package com.gospace.exploration.service;

import com.gospace.exploration.domain.CelestialBodyType;
import com.gospace.exploration.domain.Destination;
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
    public Destination saveOrUpdate(Destination destination) {
        return repository.save(destination);
    }

    @Transactional
    public void delete(Destination destination) {
        repository.delete(destination);
    }
}
