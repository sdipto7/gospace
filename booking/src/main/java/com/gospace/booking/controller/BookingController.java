package com.gospace.booking.controller;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.domain.BookingStatus;
import com.gospace.booking.dto.BookingRequestDto;
import com.gospace.booking.dto.BookingResponseDto;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.Objects.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService service;

    private final BookingHelper helper;

    private final BookingValidator validator;

    private final ApiValidationHelper apiValidationHelper;

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> show(@PathVariable int id) {
        Booking booking = service.find(id);

        if (isNull(booking)) {
            log.info("[API:BOOKING:SHOW] Error while processing Booking show with ID: {}", id);

            throw new BookingNotFoundException(String.format("Invalid id! No Booking found for the id: %d", id));
        }

        return ResponseEntity.ok(helper.getBookingResponseDto(booking));
    }

    @GetMapping
    public ResponseEntity<BookingResponseDto> showByReferenceNumber(@RequestParam String referenceNumber) {
        Booking booking = service.findByReferenceNumber(referenceNumber);

        if (isNull(booking)) {
            log.info("[API:BOOKING:SHOW:REFERENCE-NUMBER] Error while processing Booking show with ReferenceNumber: {}", referenceNumber);

            throw new BookingNotFoundException(String.format("Invalid reference number! No Booking found for the ReferenceNumber: %s", referenceNumber));
        }

        return ResponseEntity.ok(helper.getBookingResponseDto(booking));
    }

    @GetMapping("/proxy/v1/exists-by-spacetrip/{spaceTripId}")
    public ResponseEntity<Boolean> hasBookingBySpaceTripId(@PathVariable int spaceTripId) {
        Booking booking = service.findBySpaceTripId(spaceTripId);

        log.info("[API:BOOKING:PROXY:V1:EXISTS-BY-SPACETRIP] SpaceTrip with ID: {}, booking: {}", spaceTripId, booking);

        return ResponseEntity.ok(nonNull(booking));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingResponseDto>> showAll() {
        List<BookingResponseDto> bookingDtoList = helper.getBookingResponseDtoList(service.findAll());

        return bookingDtoList.isEmpty()
                ? new ResponseEntity<>(NO_CONTENT)
                : ResponseEntity.ok(bookingDtoList);
    }

    @GetMapping("/all/{status}")
    public ResponseEntity<List<BookingResponseDto>> showAllByStatus(@PathVariable String status) {
        List<Booking> bookingList = service.findAllByStatus(BookingStatus.fromLabel(status));

        List<BookingResponseDto> bookingDtoList = helper.getBookingResponseDtoList(bookingList);

        return bookingDtoList.isEmpty()
                ? new ResponseEntity<>(NO_CONTENT)
                : ResponseEntity.ok(bookingDtoList);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody BookingRequestDto bookingRequestDto, Errors errors) {

        validator.validate(bookingRequestDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:BOOKING:SAVE] Error while processing Booking save, Response: {}", requireNonNull(errorResponse.getBody()).getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:BOOKING:SAVE] Successfully validated Booking Data, RequestBody: {}", bookingRequestDto);

        Booking booking = service.saveOrUpdate(bookingRequestDto);

        log.info("[API:BOOKING:SAVE] Successfully processed Booking save, Response: {}", booking);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(booking.getId())
                        .toUri()
                ).build();
    }

    @PostMapping(value = "/payment", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> makePayment(@RequestBody Map<String, String> paymentDto) {
        String referenceNumber = paymentDto.get("referenceNumber");
        BigDecimal totalPrice = new BigDecimal(paymentDto.get("totalPrice"));

        //call third-party payment-gateway api from here and get HTTP response status OK or ACCEPTED if payment is successful
        HttpStatus paymentResponse = HttpStatus.OK;

        Booking booking = service.updateStatus(referenceNumber, paymentResponse);

        return ResponseEntity.ok(booking);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody BookingRequestDto bookingRequestDto, Errors errors) {

        validator.validate(bookingRequestDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:BOOKING:UPDATE] Error while processing Booking update, Response: {}", requireNonNull(errorResponse.getBody()).getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:BOOKING:UPDATE] Successfully validated Booking Data, RequestBody: {}", bookingRequestDto);

        Booking booking = service.saveOrUpdate(bookingRequestDto);

        log.info("[API:BOOKING:UPDATE] Successfully processed Booking update, Response: {}", booking);

        return ResponseEntity.ok(helper.getBookingResponseDto(booking));
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
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
