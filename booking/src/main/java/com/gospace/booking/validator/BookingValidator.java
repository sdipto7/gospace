package com.gospace.booking.validator;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.dto.BookingRequestDto;
import com.gospace.booking.proxy.SpaceTripProxy;
import com.gospace.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.gospace.booking.util.ServletUtil.isPutRequest;
import static java.util.Objects.isNull;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@RequiredArgsConstructor
@Component
public class BookingValidator implements Validator {

    private final BookingService service;

    private final SpaceTripProxy spaceTripProxy;

    @Override
    public boolean supports(Class<?> clazz) {
        return BookingRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingRequestDto bookingRequestDto = (BookingRequestDto) target;

        if (isPutRequest()) {
            validateBooking(bookingRequestDto, errors);
        }

        validatePassengerEmail(bookingRequestDto, errors);

        validatePassengerPhone(bookingRequestDto, errors);

        validateTotalSeats(bookingRequestDto, errors);
    }

    private void validateBooking(BookingRequestDto bookingRequestDto, Errors errors) {
        Booking booking = service.find(bookingRequestDto.getId());

        if (isNull(booking)) {
            errors.reject("valid.booking.data", "Booking not found by the given identifier");
            return;
        }

        if (!booking.getReferenceNumber().equalsIgnoreCase(bookingRequestDto.getReferenceNumber())) {
            errors.rejectValue("referenceNumber", "valid.referenceNumber", "Booking reference number does not match");
        }

        if (booking.getTripId() != bookingRequestDto.getTripId()) {
            errors.rejectValue("tripId", "valid.trip", "Cannot change spacetrip for a particular booking");
        }

        if (booking.isConfirmedBooking()) {
            errors.rejectValue("status", "valid.status", "Cannot update an already confirmed booking");
        }

        if (booking.getVersion() != bookingRequestDto.getVersion()) {
            errors.rejectValue("version", "valid.version", "The data is already modified");
        }
    }

    private void validatePassengerEmail(BookingRequestDto bookingRequestDto, Errors errors) {
        boolean hasDuplicateEmail = service.findAll()
                .stream()
                .anyMatch(booking -> booking.getId() != bookingRequestDto.getId()
                        && booking.getPassengerDetails().getEmail().equalsIgnoreCase(bookingRequestDto.getPassengerEmail()));

        if (hasDuplicateEmail) {
            errors.rejectValue("passengerEmail",
                    "valid.booking.duplicate.passenger.email",
                    "The given passenger email address is already being used");
        }
    }

    private void validatePassengerPhone(BookingRequestDto bookingRequestDto, Errors errors) {
        boolean hasDuplicatePhone = service.findAll()
                .stream()
                .anyMatch(booking -> booking.getId() != bookingRequestDto.getId()
                        && booking.getPassengerDetails().getPhone().equalsIgnoreCase(bookingRequestDto.getPassengerPhone()));

        if (hasDuplicatePhone) {
            errors.rejectValue("passengerPhone",
                    "valid.booking.duplicate.passenger.phone",
                    "The given passenger phone number is already being used");
        }
    }

    private void validateTotalSeats(BookingRequestDto bookingRequestDto, Errors errors) {
        Integer availableSeats = spaceTripProxy.getAvailableSeats(bookingRequestDto.getTripId()).getBody();

        if (isNull(availableSeats) || availableSeats == 0) {
            errors.reject("valid.spacetrip.availableSeats", "No seats are available in the selected spacetrip");
            return;
        }

        if (availableSeats < bookingRequestDto.getTotalSeats()) {
            errors.rejectValue("totalSeats",
                    "valid.totalSeats",
                    new Object[]{bookingRequestDto.getTotalSeats(), availableSeats},
                    "Cannot book {0} seats. Only {1} seats are available in the spacetrip");
        }
    }
}
