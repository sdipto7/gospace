package com.gospace.spacetrip.controller;

import com.gospace.spacetrip.domain.SpaceTrip;
import com.gospace.spacetrip.dto.SpaceTripDetailsDto;
import com.gospace.spacetrip.dto.SpaceTripDto;
import com.gospace.spacetrip.dto.ValidationResponseDto;
import com.gospace.spacetrip.exception.SpaceTripNotFoundException;
import com.gospace.spacetrip.helper.ApiValidationHelper;
import com.gospace.spacetrip.helper.SpaceTripHelper;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author rumidipto
 * @since 3/17/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/spacetrip")
public class SpaceTripController {

    private final SpaceTripService service;

    private final SpaceTripHelper helper;

    private final SpaceTripValidator validator;

    private final ApiValidationHelper apiValidationHelper;

    private static final Logger log = LoggerFactory.getLogger(SpaceTripController.class);

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<SpaceTripDto> show(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        if (isNull(spaceTrip)) {
            log.info("[API:SPACETRIP:SHOW] Error while processing SpaceTrip show with ID: {}", id);

            throw new SpaceTripNotFoundException(String.format("Invalid id! No SpaceTrip found for the id: %d", id));
        }

        return new ResponseEntity<>(helper.getSpaceTripDto(spaceTrip), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/details/{id}")
    public ResponseEntity<SpaceTripDetailsDto> showDetails(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        if (isNull(spaceTrip)) {
            log.info("[API:SPACETRIP:DETAILS] Error while processing SpaceTrip details with ID: {}", id);

            throw new SpaceTripNotFoundException(String.format("Invalid id! No SpaceTrip details found for the id: %d", id));
        }

        return new ResponseEntity<>(helper.getSpaceTripDetailsDto(spaceTrip), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/proxy/v1/exists/{id}")
    public ResponseEntity<Boolean> hasSpaceTrip(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        log.info("[API:SPACETRIP:PROXY:V1:EXISTS] SpaceTrip with ID: {}, spaceTrip: {}", id, spaceTrip);

        return new ResponseEntity<>(nonNull(spaceTrip), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/proxy/v1/details/{id}")
    public ResponseEntity<SpaceTripDetailsDto> getSpaceTripDetailsDto(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        log.info("[API:SPACETRIP:PROXY:V1:DETAILS] SpaceTrip with ID: {}, spaceTrip: {}", id, spaceTrip);

        return new ResponseEntity<>(nonNull(spaceTrip) ? helper.getSpaceTripDetailsDto(spaceTrip) : null, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/proxy/v1/available-seats/{id}")
    public ResponseEntity<Integer> getSpaceTripAvailableSeats(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        log.info("[API:SPACETRIP:PROXY:V1:AVAILABLE-SEATS] SpaceTrip with ID: {}, spaceTrip: {}", id, spaceTrip);

        return new ResponseEntity<>(nonNull(spaceTrip) ? spaceTrip.getAvailableSeats() : null, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/proxy/v1/price/{id}")
    public ResponseEntity<BigDecimal> getSpaceTripPrice(@PathVariable int id) {
        SpaceTrip spaceTrip = service.find(id);

        log.info("[API:SPACETRIP:PROXY:V1:PRICE] SpaceTrip with ID: {}, spaceTrip: {}", id, spaceTrip);

        return new ResponseEntity<>(nonNull(spaceTrip) ? spaceTrip.getTicketPrice() : null, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/available-trips")
    public ResponseEntity<List<SpaceTripDto>> showAll() {
        List<SpaceTripDto> availableSpaceTripDtoList = helper.getSpaceTripDtoList(service.findAvailableSpaceTrips());

        return new ResponseEntity<>(availableSpaceTripDtoList,
                availableSpaceTripDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SpaceTripDto spaceTripDto, Errors errors) {

        validator.validate(spaceTripDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:SPACETRIP:SAVE] Error while processing SpaceTrip save, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:SPACETRIP:SAVE] Successfully validated SpaceTrip Data, RequestBody: {}", spaceTripDto);

        SpaceTrip spaceTrip = service.saveOrUpdate(spaceTripDto);

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
    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody SpaceTripDto spaceTripDto, Errors errors) {

        validator.validate(spaceTripDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:SPACETRIP:UPDATE] Error while processing SpaceTrip update, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:SPACETRIP:UPDATE] Successfully validated SpaceTrip Data, RequestBody: {}", spaceTripDto);

        SpaceTrip spaceTrip = service.saveOrUpdate(spaceTripDto);

        log.info("[API:SPACETRIP:UPDATE] Successfully processed SpaceTrip update, Response: {}", spaceTrip);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(spaceTrip.getId())
                        .toUri())
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/proxy/v1/update-available-seats", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAvailableSeats(@RequestBody Map<String, Integer> spaceTripBookedSeatMap) {
        int spaceTripId = spaceTripBookedSeatMap.get("spaceTripId");
        int bookedSeats = spaceTripBookedSeatMap.get("bookedSeats");

        log.info("[API:SPACETRIP:PROXY:V1:UPDATE-AVAILABLE-SEATS] SpaceTrip with ID: {}, Booked seats: {}", spaceTripId, bookedSeats);

        SpaceTrip spaceTrip = service.updateAvailableSeats(spaceTripId, bookedSeats);

        log.info("[API:SPACETRIP:PROXY:V1:UPDATE-AVAILABLE-SEATS] Successfully processed SpaceTrip available seats update, Response: {}", spaceTrip);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(spaceTrip.getId())
                        .toUri())
                .build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
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
