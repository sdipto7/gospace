package com.gospace.spacetrip.repository;

import com.gospace.spacetrip.domain.SpaceCraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author rumidipto
 * @since 3/17/24
 */
@Repository
public interface SpaceCraftRepository extends JpaRepository<SpaceCraft, Integer> {
}
