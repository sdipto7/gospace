package com.gospace.exploration.controller;

import com.gospace.exploration.domain.CelestialBodyType;
import com.gospace.exploration.domain.Destination;
import com.gospace.exploration.dto.DestinationDto;
import com.gospace.exploration.dto.ValidationResponseDto;
import com.gospace.exploration.exception.DestinationNotFoundException;
import com.gospace.exploration.helper.ApiValidationHelper;
import com.gospace.exploration.helper.DestinationHelper;
import com.gospace.exploration.service.DestinationService;
import com.gospace.exploration.validator.DestinationValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author rumidipto
 * @since 3/25/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/destination")
public class DestinationController {

    private final DestinationService service;

    private final DestinationHelper helper;

    private final DestinationValidator validator;

    private final ApiValidationHelper apiValidationHelper;

    private static final Logger log = LoggerFactory.getLogger(DestinationController.class);

    @GetMapping("/{id}")
    public ResponseEntity<DestinationDto> show(@PathVariable int id) {
        Destination destination = service.find(id);

        if (isNull(destination)) {
            log.info("[API:DESTINATION:SHOW] Error while processing Destination show with ID: {}", id);

            throw new DestinationNotFoundException(String.format("Invalid id! No Destination found for the id: %d", id));
        }

        return ResponseEntity.ok(helper.getDestinationDto(destination));
    }

    @GetMapping("/proxy/v1/exists/{id}")
    public ResponseEntity<Boolean> hasDestination(@PathVariable int id) {
        Destination destination = service.find(id);

        log.info("[API:DESTINATION:PROXY:V1:EXISTS] Destination with ID: {}, destination: {}", id, destination);

        return ResponseEntity.ok(nonNull(destination));
    }

    @GetMapping("/proxy/v1/{id}")
    public ResponseEntity<DestinationDto> getDestinationDto(@PathVariable int id) {
        Destination destination = service.find(id);

        log.info("[API:DESTINATION:PROXY:V1] Destination with ID: {}, destination: {}", id, destination);

        return isNull(destination)
                ? new ResponseEntity<>(NO_CONTENT)
                : ResponseEntity.ok(helper.getDestinationDto(destination));
    }

    @GetMapping("/proxy/v1/name/{id}")
    public ResponseEntity<String> getDestinationName(@PathVariable int id) {
        Destination destination = service.find(id);

        log.info("[API:DESTINATION:PROXY:V1:NAME] Destination with ID: {}, destination: {}", id, destination);

        return isNull(destination)
                ? new ResponseEntity<>(NO_CONTENT)
                : ResponseEntity.ok(destination.getName());
    }

    @GetMapping("/all")
    public ResponseEntity<List<DestinationDto>> showAll() {
        List<DestinationDto> destinationDtoList = helper.getDestinationDtoList(service.findAll());

        return destinationDtoList.isEmpty()
                ? new ResponseEntity<>(NO_CONTENT)
                : ResponseEntity.ok(destinationDtoList);
    }

    @GetMapping("/all/{type}")
    public ResponseEntity<List<DestinationDto>> showAllByType(@PathVariable(name = "type") String celestialBodyType) {
        List<Destination> destinationList = service.findAllByCelestialBodyType(CelestialBodyType.fromLabel(celestialBodyType));

        List<DestinationDto> destinationDtoList = helper.getDestinationDtoList(destinationList);

        return destinationDtoList.isEmpty()
                ? new ResponseEntity<>(NO_CONTENT)
                : ResponseEntity.ok(destinationDtoList);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody DestinationDto destinationDto, Errors errors) {

        validator.validate(destinationDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:DESTINATION:SAVE] Error while processing Destination save, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:DESTINATION:SAVE] Successfully validated Destination Data, RequestBody: {}", destinationDto);

        Destination destination = service.saveOrUpdate(destinationDto);

        log.info("[API:DESTINATION:SAVE] Successfully processed Destination save, Response: {}", destination);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(destination.getId())
                        .toUri()
                ).build();
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody DestinationDto destinationDto, Errors errors) {

        validator.validate(destinationDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:DESTINATION:UPDATE] Error while processing Destination update, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:DESTINATION:UPDATE] Successfully validated Destination Data, RequestBody: {}", destinationDto);

        Destination destination = service.saveOrUpdate(destinationDto);

        log.info("[API:DESTINATION:UPDATE] Successfully processed Destination update, Response: {}", destination);

        return ResponseEntity.ok(helper.getDestinationDto(destination));
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        Destination destination = service.find(id);

        if (isNull(destination)) {
            log.info("[API:DESTINATION:DELETE] Error while processing Destination delete with ID: {}", id);

            throw new DestinationNotFoundException(String.format("Invalid id! No Destination found to delete for the id: %d", id));
        }

        service.delete(destination);

        log.info("[API:DESTINATION:DELETE] Successfully processed Destination delete with ID: {}", id);
    }
}
