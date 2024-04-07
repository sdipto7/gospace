package com.gospace.spacetrip.controller;

import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.dto.SpaceTripDetailsDto;
import com.gospace.spacetrip.dto.SpaceTripDto;
import com.gospace.spacetrip.dto.ValidationResponseDto;
import com.gospace.spacetrip.exception.SpaceTripNotFoundException;
import com.gospace.spacetrip.helper.ApiValidationHelper;
import com.gospace.spacetrip.helper.SpaceTripHelper;
import com.gospace.spacetrip.proxy.ExplorationProxy;
import com.gospace.spacetrip.proxy.dto.DestinationDto;
import com.gospace.spacetrip.service.SpaceTripService;
import com.gospace.spacetrip.validator.SpaceTripValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author rumidipto
 * @since 3/17/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SpaceTripController {

    private final SpaceTripService service;

    private final SpaceTripHelper helper;

    private final SpaceTripValidator validator;

    private final ApiValidationHelper apiValidationHelper;

    private final ExplorationProxy explorationProxy;

    private static final Logger log = LoggerFactory.getLogger(SpaceTripController.class);

    @ResponseBody
    @GetMapping("/spacetrip/{id}")
    public ResponseEntity<SpaceTripDto> show(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        if (isNull(spaceTrip)) {
            log.info("[API:SPACETRIP:SHOW] Error while processing SpaceTrip show with ID: {}", id);

            throw new SpaceTripNotFoundException(String.format("Invalid id! No SpaceTrip found for the id: %d", id));
        }

        return new ResponseEntity<>(helper.getDtoFromSpaceTrip(spaceTrip), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/spacetrip/details/{id}")
    public ResponseEntity<SpaceTripDetailsDto> showDetails(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        if (isNull(spaceTrip)) {
            log.info("[API:SPACETRIP:DETAILS] Error while processing SpaceTrip details with ID: {}", id);

            throw new SpaceTripNotFoundException(String.format("Invalid id! No SpaceTrip details found for the id: %d", id));
        }

        DestinationDto destinationDto = explorationProxy.show(spaceTrip.getDestinationId()).getBody();

        if (isNull(destinationDto)) {
            log.info("[API:SPACETRIP:DETAILS] Error while processing Destination details with ID: {}" +
                    " which is associated with SpaceTrip with ID: {}", spaceTrip.getDestinationId(), spaceTrip.getId());

            throw new SpaceTripNotFoundException(String.format("Invalid Destination! No Destination found for the SpaceTrip with id: %d", id));
        }

        return new ResponseEntity<>(helper.getSpaceTripDetailsDto(spaceTrip, destinationDto), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/spacetrip/available-trips")
    public ResponseEntity<List<SpaceTripDto>> showAll() {
        List<SpaceTripDto> availableSpaceTripDtoList = helper.getDtoListFromSpaceTripList(service.findAvailableSpaceTrips());

        return new ResponseEntity<>(availableSpaceTripDtoList,
                availableSpaceTripDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/spacetrip", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SpaceTripDto spaceTripDto, Errors errors) {

        validator.validate(spaceTripDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:SPACETRIP:SAVE] Error while processing SpaceTrip save, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:SPACETRIP:SAVE] Successfully validated SpaceTrip Data, RequestBody: {}", spaceTripDto);

        SpaceTrip spaceTrip = helper.getSpaceTripFromDto(spaceTripDto);

        service.saveOrUpdate(spaceTrip);

        log.info("[API:SPACETRIP:SAVE] Successfully processed SpaceTrip save, Response: {}", spaceTrip);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(spaceTrip.getId())
                        .toUri()
                ).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/spacetrip", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody SpaceTripDto spaceTripDto, Errors errors) {

        validator.validate(spaceTripDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:SPACETRIP:UPDATE] Error while processing SpaceTrip update, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:SPACETRIP:UPDATE] Successfully validated SpaceTrip Data, RequestBody: {}", spaceTripDto);

        SpaceTrip spaceTrip = service.find(spaceTripDto.getId());

        helper.updateEntityFromDto(spaceTrip, spaceTripDto);

        service.saveOrUpdate(spaceTrip);

        log.info("[API:SPACETRIP:UPDATE] Successfully processed SpaceTrip update, Response: {}", spaceTrip);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(spaceTrip.getId())
                        .toUri())
                .build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/spacetrip/{id}")
    public void delete(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        if (isNull(spaceTrip)) {
            log.info("[API:SPACETRIP:DELETE] Error while processing SpaceTrip delete with ID: {}", id);

            throw new SpaceTripNotFoundException(String.format("Invalid id! No SpaceTrip found to delete for the id: %d", id));
        }

        service.delete(spaceTrip);

        log.info("[API:SPACETRIP:DELETE] Successfully processed SpaceTrip delete with ID: {}", id);
    }
}
