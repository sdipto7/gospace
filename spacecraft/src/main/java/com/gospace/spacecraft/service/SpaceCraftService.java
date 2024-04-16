package com.gospace.spacecraft.service;

import com.gospace.spacecraft.domain.Manufacturer;
import com.gospace.spacecraft.domain.SpaceCraft;
import com.gospace.spacecraft.dto.SpaceCraftDto;
import com.gospace.spacecraft.repository.SpaceCraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author rumidipto
 * @since 3/19/24
 */
@RequiredArgsConstructor
@Service
public class SpaceCraftService {

    private final SpaceCraftRepository repository;

    public SpaceCraft find(int id) {
        Optional<SpaceCraft> optionalSpaceCraft = repository.findById(id);

        return optionalSpaceCraft.isPresent() ? optionalSpaceCraft.get() : null;
    }

    public List<SpaceCraft> findAll() {
        return repository.findAll();
    }

    @Transactional
    public SpaceCraft saveOrUpdate(SpaceCraftDto spaceCraftDto) {
        SpaceCraft spaceCraft = spaceCraftDto.isNew() ? new SpaceCraft() : find(spaceCraftDto.getId());

        spaceCraft.setName(spaceCraftDto.getName());
        spaceCraft.setManufacturer(Manufacturer.fromLabel(spaceCraftDto.getManufacturer()));
        spaceCraft.setManufactureDate(spaceCraftDto.getManufactureDate());
        spaceCraft.setCrewCapacity(spaceCraftDto.getCrewCapacity());
        spaceCraft.setPassengerCapacity(spaceCraftDto.getPassengerCapacity());

        return repository.save(spaceCraft);
    }

    @Transactional
    public void delete(SpaceCraft spaceCraft) {
        repository.delete(spaceCraft);
    }
}
