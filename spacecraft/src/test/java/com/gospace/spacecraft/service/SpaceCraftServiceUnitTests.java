package com.gospace.spacecraft.service;

import com.gospace.spacecraft.domain.SpaceCraft;
import com.gospace.spacecraft.dto.SpaceCraftDto;
import com.gospace.spacecraft.repository.SpaceCraftRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.gospace.spacecraft.domain.Manufacturer.SIERRA_SPACE;
import static com.gospace.spacecraft.domain.Manufacturer.SPACE_X;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author rumidipto
 * @since 9/25/24
 */
@ExtendWith(MockitoExtension.class)
public class SpaceCraftServiceUnitTests {

    @Mock
    private SpaceCraftRepository repository;

    @InjectMocks
    private SpaceCraftService service;

    @Test
    public void findSpaceCraftTest() {
        SpaceCraft spaceCraft = new SpaceCraft(1, "Kepler Express", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 150, 850);

        when(repository.findById(1)).thenReturn(Optional.of(spaceCraft));

        SpaceCraft existingSpaceCraft = service.find(1);

        assertNotNull(existingSpaceCraft);
        assertEquals(existingSpaceCraft.getId(), spaceCraft.getId());
        assertEquals(existingSpaceCraft.getName(), spaceCraft.getName());

        verify(repository, times(1)).findById(spaceCraft.getId());
    }

    @Test
    public void findAllSpaceCraftTest() {
        List<SpaceCraft> spaceCraftList = List.of(
                new SpaceCraft(1, "Kepler Express", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 150, 850),
                new SpaceCraft(2, "Andromeda Express", SPACE_X, LocalDate.of(2020, 1, 1), 200, 800)
        );

        when(repository.findAll()).thenReturn(spaceCraftList);

        List<SpaceCraft> existingSpaceCraftList = service.findAll();

        assertEquals(existingSpaceCraftList.size(), spaceCraftList.size());
        assertTrue(existingSpaceCraftList.containsAll(spaceCraftList));

        verify(repository, times(1)).findAll();
    }

    @Test
    public void saveSpaceCraftTest() {
        SpaceCraft newSpaceCraft = new SpaceCraft(0, "Kepler Express", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 150, 850);
        SpaceCraft expectedSpaceCraft = new SpaceCraft(1, "Kepler Express", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 150, 850);

        when(repository.save(newSpaceCraft)).thenReturn(expectedSpaceCraft);

        SpaceCraft savedSpaceCraft = service.saveOrUpdate(getSpaceCraftDto(newSpaceCraft));

        assertNotNull(savedSpaceCraft);
        assertEquals(savedSpaceCraft.getId(), expectedSpaceCraft.getId());
        assertEquals(savedSpaceCraft.getName(), expectedSpaceCraft.getName());

        verify(repository, times(1)).save(newSpaceCraft);
    }

    @Test
    public void updateSpaceCraftTest() {
        SpaceCraft oldSpaceCraft = new SpaceCraft(1, "Kepler Express", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 150, 850);
        SpaceCraft expectedSpaceCraft = new SpaceCraft(1, "Voyager 7", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 200, 800);

        when(repository.findById(1)).thenReturn(Optional.of(oldSpaceCraft));
        when(repository.save(oldSpaceCraft)).thenReturn(expectedSpaceCraft);

        SpaceCraft savedSpaceCraft = service.saveOrUpdate(getSpaceCraftDto(oldSpaceCraft));

        assertNotNull(savedSpaceCraft);
        assertEquals(savedSpaceCraft.getId(), expectedSpaceCraft.getId());
        assertEquals(savedSpaceCraft.getName(), expectedSpaceCraft.getName());

        verify(repository, times(1)).findById(oldSpaceCraft.getId());
        verify(repository, times(1)).save(oldSpaceCraft);
    }

    @Test
    public void deleteSpaceCraftTest() {
        SpaceCraft spaceCraft = new SpaceCraft(1, "Kepler Express", SIERRA_SPACE, LocalDate.of(2019, 1, 1), 150, 850);

        doNothing().when(repository).delete(spaceCraft);

        service.delete(spaceCraft);

        verify(repository, times(1)).delete(spaceCraft);
    }

    private SpaceCraftDto getSpaceCraftDto(SpaceCraft spaceCraft) {
        return SpaceCraftDto.builder()
                .id(spaceCraft.getId())
                .name(spaceCraft.getName())
                .manufacturer(spaceCraft.getManufacturer().getLabel())
                .manufactureDate(spaceCraft.getManufactureDate())
                .crewCapacity(spaceCraft.getCrewCapacity())
                .passengerCapacity(spaceCraft.getPassengerCapacity())
                .version(spaceCraft.getVersion())
                .build();
    }
}
