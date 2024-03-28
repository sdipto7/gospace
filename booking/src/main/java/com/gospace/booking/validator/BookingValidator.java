package com.gospace.booking.validator;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.dto.BookingDto;
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

    public final BookingService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return BookingDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingDto bookingDto = (BookingDto) target;

        if (isPutRequest()) {
            validateBooking(bookingDto, errors);
        }

        validatePassengerEmail(bookingDto, errors);

        validatePassengerPhone(bookingDto, errors);
    }

    private void validateBooking(BookingDto bookingDto, Errors errors) {
        Booking booking = service.find(bookingDto.getId());

        if (isNull(booking)) {
            errors.reject("valid.booking.data", "Booking not found by the given identifier");
            return;
        }

        if (!booking.getReferenceNumber().equalsIgnoreCase(bookingDto.getReferenceNumber())) {
            errors.rejectValue("referenceNumber", "valid.referenceNumber", "Booking reference number does not match");
        }

        if (booking.isConfirmedBooking()) {
            errors.rejectValue("status", "valid.status", "Cannot update an already confirmed booking");
        }

        if (booking.getVersion() != bookingDto.getVersion()) {
            errors.rejectValue("version", "valid.version", "The data is already modified");
        }
    }

    private void validatePassengerEmail(BookingDto bookingDto, Errors errors) {
        boolean hasDuplicateEmail = service.findAll()
                .stream()
                .anyMatch(booking -> booking.getId() != bookingDto.getId()
                        && booking.getPassengerDetails().getEmail().equalsIgnoreCase(bookingDto.getPassengerEmail()));

        if (hasDuplicateEmail) {
            errors.rejectValue("passengerEmail",
                    "valid.booking.duplicate.passenger.email",
                    "The given passenger email address is already being used");
        }
    }

    private void validatePassengerPhone(BookingDto bookingDto, Errors errors) {
        boolean hasDuplicatePhone = service.findAll()
                .stream()
                .anyMatch(booking -> booking.getId() != bookingDto.getId()
                        && booking.getPassengerDetails().getPhone().equalsIgnoreCase(bookingDto.getPassengerPhone()));

        if (hasDuplicatePhone) {
            errors.rejectValue("passengerPhone",
                    "valid.booking.duplicate.passenger.phone",
                    "The given passenger phone number is already being used");
        }
    }
}
