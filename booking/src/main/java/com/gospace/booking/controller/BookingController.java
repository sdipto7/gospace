package com.gospace.booking.controller;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.domain.BookingStatus;
import com.gospace.booking.dto.BookingDto;
import com.gospace.booking.dto.ValidationResponseDto;
import com.gospace.booking.exception.BookingNotFoundException;
import com.gospace.booking.exception.InvalidBookingStatusException;
import com.gospace.booking.helper.ApiValidationHelper;
import com.gospace.booking.helper.BookingHelper;
import com.gospace.booking.service.BookingService;
import com.gospace.booking.validator.BookingValidator;
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
 * @since 3/29/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService service;

    private final BookingHelper helper;

    private final BookingValidator validator;

    private final ApiValidationHelper apiValidationHelper;

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @ResponseBody
    @GetMapping("/booking/{id}")
    public ResponseEntity<BookingDto> show(@PathVariable int id) {
        Booking booking = service.find(id);

        if (isNull(booking)) {
            log.info("[API:BOOKING:SHOW] Error while processing Booking show with ID: {}", id);

            throw new BookingNotFoundException(String.format("Invalid id! No Booking found for the id: %d", id));
        }

        return new ResponseEntity<>(helper.getDtoFromBooking(booking), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/booking")
    public ResponseEntity<BookingDto> showByReferenceNumber(@RequestParam String referenceNumber) {
        Booking booking = service.findByReferenceNumber(referenceNumber);

        if (isNull(booking)) {
            log.info("[API:BOOKING:SHOW:REFERENCE-NUMBER] Error while processing Booking show with ReferenceNumber: {}", referenceNumber);

            throw new BookingNotFoundException(String.format("Invalid id! No Booking found for the ReferenceNumber: %d", referenceNumber));
        }

        return new ResponseEntity<>(helper.getDtoFromBooking(booking), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/booking/all")
    public ResponseEntity<List<BookingDto>> showAll() {
        List<BookingDto> bookingDtoList = helper.getDtoListFromBookingList(service.findAll());

        return new ResponseEntity<>(bookingDtoList, bookingDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/booking/all/{status}")
    public ResponseEntity<List<BookingDto>> showAllByStatus(@PathVariable String status) {
        List<Booking> bookingList = service.findAllByStatus(BookingStatus.fromLabel(status));

        List<BookingDto> bookingDtoList = helper.getDtoListFromBookingList(bookingList);

        return new ResponseEntity<>(bookingDtoList, bookingDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/booking", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody BookingDto bookingDto, Errors errors) {

        validator.validate(bookingDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:BOOKING:SAVE] Error while processing Booking save, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:BOOKING:SAVE] Successfully validated Booking Data, RequestBody: {}", bookingDto);

        Booking booking = helper.getBookingFromDto(bookingDto);

        service.saveOrUpdate(booking);

        log.info("[API:BOOKING:SAVE] Successfully processed Booking save, Response: {}", booking);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(booking.getId())
                        .toUri()
                ).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/booking", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody BookingDto bookingDto, Errors errors) {

        validator.validate(bookingDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:BOOKING:UPDATE] Error while processing Booking update, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:BOOKING:UPDATE] Successfully validated Booking Data, RequestBody: {}", bookingDto);

        Booking booking = service.find(bookingDto.getId());

        helper.updateEntityFromDto(booking, bookingDto);

        service.saveOrUpdate(booking);

        log.info("[API:BOOKING:UPDATE] Successfully processed Booking update, Response: {}", booking);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(booking.getId())
                        .toUri())
                .build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/booking/{id}")
    public void delete(@PathVariable int id) {
        Booking booking = service.find(id);

        if (isNull(booking)) {
            log.info("[API:BOOKING:DELETE] Error while processing Booking delete with ID: {}", id);

            throw new BookingNotFoundException(String.format("Invalid id! No Booking found to delete for the id: %d", id));
        }

        if (booking.isConfirmedBooking()) {
            log.info("[API:BOOKING:DELETE] Error while processing Booking delete with ID: {}, status: {}", id, booking.getStatus().getLabel());

            throw new InvalidBookingStatusException(String.format("Invalid status! %s Booking cannot be deleted for the id: %d", booking.getStatus().getLabel(), id));
        }

        service.delete(booking);

        log.info("[API:BOOKING:DELETE] Successfully processed Booking delete with ID: {}", id);
    }
}
