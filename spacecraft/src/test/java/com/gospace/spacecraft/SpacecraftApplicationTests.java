package com.gospace.spacecraft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gospace.spacecraft.domain.SpaceCraft;
import com.gospace.spacecraft.dto.SpaceCraftDto;
import com.gospace.spacecraft.helper.SpaceCraftHelper;
import com.gospace.spacecraft.repository.SpaceCraftRepository;
import com.gospace.spacecraft.service.SpaceCraftService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class SpacecraftApplicationTests {

    private final String SPACECRAFT_CREATE_METHOD = "shouldCreateSpaceCraft";
    private final String SPACECRAFT_GET_EMPTY_LIST_METHOD = "shouldFindEmptySpaceCraftList";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpaceCraftRepository repository;

    @Autowired
    private SpaceCraftHelper helper;

    @Autowired
    private SpaceCraftService service;

    @Container
    public static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }

    @BeforeAll
    public static void startDatabaseConnection() {
        MY_SQL_CONTAINER.start();
    }

    @AfterAll
    public static void closeDatabaseConnection() {
        MY_SQL_CONTAINER.close();
    }

    @BeforeEach
    public void setupTestData(TestInfo testInfo) {
        List<String> excludedMethods = asList(SPACECRAFT_GET_EMPTY_LIST_METHOD, SPACECRAFT_CREATE_METHOD);
        String testMethod = testInfo.getTestMethod().isPresent() ? testInfo.getTestMethod().get().getName() : null;

        if (!excludedMethods.contains(testMethod)) {
            service.saveOrUpdate(getSpaceCraftDto());
        }
    }

    @AfterEach
    public void clearTestData() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Test to verify that empty Spacecraft list is fetched when no there is no spacecraft")
    public void shouldFindEmptySpaceCraftList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/spacecraft/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test to verify that Spacecraft is created")
    public void shouldCreateSpaceCraft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/spacecraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSpaceCraftDto())))
                .andExpect(status().isCreated());

        assertEquals(1, repository.findAll().size());
    }

    @Test
    @DisplayName("Test to verify that all Spacecraft is fetched")
    public void shouldFindAllSpaceCraft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/spacecraft/all"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test to verify that Spacecraft is updated")
    public void shouldUpdateSpaceCraft() throws Exception {
        List<SpaceCraft> spaceCraftList = repository.findAll();
        assertEquals(1, spaceCraftList.size());

        SpaceCraft spaceCraft = spaceCraftList.stream().findFirst().orElse(null);
        assertNotNull(spaceCraft);

        spaceCraft.setCrewCapacity(200);
        spaceCraft.setPassengerCapacity(800);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/spacecraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(helper.getSpaceCraftDto(spaceCraft))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crewCapacity").value(200))
                .andExpect(jsonPath("$.passengerCapacity").value(800))
                .andExpect(jsonPath("$.version").value(1));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    @DisplayName("Test to verify that Spacecraft is deleted")
    public void shouldDeleteSpaceCraft() throws Exception {
        List<SpaceCraft> spaceCraftList = repository.findAll();
        assertEquals(1, spaceCraftList.size());

        SpaceCraft spaceCraft = spaceCraftList.stream().findFirst().orElse(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/spacecraft/{id}", String.valueOf(nonNull(spaceCraft) ? spaceCraft.getId() : "0")))
                .andExpect(status().isNoContent());

        assertEquals(0, repository.findAll().size());
    }

    private SpaceCraftDto getSpaceCraftDto() {
        return SpaceCraftDto.builder()
                .id(0)
                .name("Kepler Express")
                .manufacturer("Sierra Space")
                .manufactureDate(LocalDate.of(2019, 1, 1))
                .crewCapacity(150)
                .passengerCapacity(850)
                .version(0)
                .build();
    }
}
