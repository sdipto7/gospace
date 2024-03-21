package com.gospace.spacetrip.service;

import com.gospace.spacetrip.domain.SpaceCraft;
import com.gospace.spacetrip.repository.SpaceCraftRepository;
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
    public SpaceCraft saveOrUpdate(SpaceCraft spaceCraft) {
        return repository.save(spaceCraft);
    }

    @Transactional
    public void delete(SpaceCraft spaceCraft) {
        repository.delete(spaceCraft);
    }
}