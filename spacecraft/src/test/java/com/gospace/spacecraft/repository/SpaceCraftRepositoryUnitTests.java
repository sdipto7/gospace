package com.gospace.spacecraft.repository;

import com.gospace.spacecraft.domain.SpaceCraft;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.gospace.spacecraft.domain.Manufacturer.BLUE_ORIGIN;
import static com.gospace.spacecraft.domain.Manufacturer.SIERRA_SPACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author rumidipto
 * @since 9/26/24
 */
@DataJpaTest
@ActiveProfiles("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpaceCraftRepositoryUnitTests {

    @Autowired
    private EntityManager em;

    @Autowired
    private SpaceCraftRepository repository;

    @Test
    @Order(1)
    @Rollback(false)
    @DisplayName("Create a new spacecraft test")
    public void saveSpaceCraftTest() {
        SpaceCraft spaceCraft = repository.save(
                new SpaceCraft(0, "Kepler Express", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 150, 850)
        );

        assertThat(spaceCraft).isNotNull();
        assertThat(spaceCraft.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @DisplayName("Find a spacecraft by id test")
    public void findSpaceCraftByIdTest() {
        Optional<SpaceCraft> spaceCraftOptional = repository.findById(1);
        assertTrue(spaceCraftOptional.isPresent());

        SpaceCraft spaceCraft = spaceCraftOptional.get();
        assertThat(spaceCraft).isNotNull();
        assertThat(spaceCraft.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    @DisplayName("Find all spacecrafts test")
    public void findAllSpaceCraftsTest() {
        List<SpaceCraft> spaceCraftList = repository.findAll();

        assertFalse(spaceCraftList.isEmpty());
        assertThat(spaceCraftList.size()).isEqualTo(1);

        repository.save(
                new SpaceCraft(0, "Andromeda Express", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 100, 800)
        );

        spaceCraftList = repository.findAll();

        assertFalse(spaceCraftList.isEmpty());
        assertThat(spaceCraftList.size()).isEqualTo(2);
    }

    @Test
    @Order(4)
    @Rollback(false)
    @DisplayName("Update an existing spacecraft test")
    public void updateSpaceCraftTest() {
        Optional<SpaceCraft> spaceCraftOptional = repository.findById(1);
        assertTrue(spaceCraftOptional.isPresent());

        SpaceCraft spaceCraft = spaceCraftOptional.get();

        assertThat(spaceCraft.getManufacturer()).isNotEqualTo(BLUE_ORIGIN);
        assertThat(spaceCraft.getPassengerCapacity()).isNotEqualTo(1500);
        assertThat(spaceCraft.getUpdated()).isNull();
        assertThat(spaceCraft.getVersion()).isEqualTo(0);

        spaceCraft.setManufacturer(BLUE_ORIGIN);
        spaceCraft.setPassengerCapacity(1500);

        spaceCraft = repository.save(spaceCraft);
        em.flush();

        assertThat(spaceCraft.getManufacturer()).isEqualTo(BLUE_ORIGIN);
        assertThat(spaceCraft.getPassengerCapacity()).isEqualTo(1500);
        assertThat(spaceCraft.getUpdated()).isNotNull();
        assertThat(spaceCraft.getVersion()).isGreaterThan(0);
    }

    @Test
    @Order(5)
    @Rollback(false)
    @DisplayName("Delete a spacecraft test")
    public void deleteSpaceCraftTest() {
        Optional<SpaceCraft> spaceCraftOptional = repository.findById(1);
        assertTrue(spaceCraftOptional.isPresent());

        SpaceCraft spaceCraft = spaceCraftOptional.get();
        assertThat(spaceCraft).isNotNull();
        assertThat(spaceCraft.getId()).isEqualTo(1);

        repository.delete(spaceCraft);

        spaceCraftOptional = repository.findById(1);
        assertTrue(spaceCraftOptional.isEmpty());
    }
}
