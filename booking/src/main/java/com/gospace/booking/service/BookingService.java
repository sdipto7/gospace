package com.gospace.booking.service;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.domain.BookingStatus;
import com.gospace.booking.domain.PassengerDetails;
import com.gospace.booking.dto.BookingRequestDto;
import com.gospace.booking.proxy.SpaceTripProxy;
import com.gospace.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.gospace.booking.domain.BookingStatus.*;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository repository;

    private final SpaceTripProxy spaceTripProxy;

    public Booking find(int id) {
        Optional<Booking> optionalBooking = repository.findById(id);

        return optionalBooking.isPresent() ? optionalBooking.get() : null;
    }

    public Booking findByReferenceNumber(String referenceNumber) {
        Optional<Booking> optionalBooking = repository.findByReferenceNumberIgnoreCase(referenceNumber);

        return optionalBooking.isPresent() ? optionalBooking.get() : null;
    }

    public Booking findBySpaceTripId(int spaceTripId) {
        Optional<Booking> optionalBooking = repository.findFirstByTripId(spaceTripId);

        return optionalBooking.isPresent() ? optionalBooking.get() : null;
    }

    public List<Booking> findAll() {
        return repository.findAll();
    }

    public List<Booking> findAllByStatus(BookingStatus status) {
        return repository.findAllByStatus(status);
    }

    @Transactional
    public Booking saveOrUpdate(BookingRequestDto bookingRequestDto) {
        Booking booking;

        if (bookingRequestDto.isNew()) {
            booking = new Booking();

            booking.setTripId(bookingRequestDto.getTripId());
            booking.setReferenceNumber(UUID.randomUUID().toString());
            booking.setPassengerDetails(new PassengerDetails());

        } else {
            booking = find(bookingRequestDto.getId());
        }

        booking.getPassengerDetails().setName(bookingRequestDto.getPassengerName());
        booking.getPassengerDetails().setEmail(bookingRequestDto.getPassengerEmail());
        booking.getPassengerDetails().setPhone(bookingRequestDto.getPassengerPhone());
        booking.setTotalSeats(bookingRequestDto.getTotalSeats());
        booking.setStatus(PROCESSING_PAYMENT);

        BigDecimal ticketPrice = spaceTripProxy.getSpaceTripPrice(bookingRequestDto.getTripId()).getBody();
        booking.setTotalPrice(
                nonNull(ticketPrice)
                        ? ticketPrice.multiply(BigDecimal.valueOf(bookingRequestDto.getTotalSeats()))
                        : new BigDecimal("0.0")
        );

        return repository.save(booking);
    }

    @Transactional
    public Booking updateStatus(String referenceNumber, HttpStatus paymentResponse) {
        Booking booking = findByReferenceNumber(referenceNumber);

        if (asList(OK, ACCEPTED).contains(paymentResponse)) {
            booking.setStatus(CONFIRMED);

            Map<String, Integer> spaceTripBookedSeatMap = new HashMap<>();
            spaceTripBookedSeatMap.put("spaceTripId", booking.getTripId());
            spaceTripBookedSeatMap.put("bookedSeats", booking.getTotalSeats());

            spaceTripProxy.updateAvailableSeats(spaceTripBookedSeatMap);

        } else {
            booking.setStatus(PAYMENT_FAILED);
        }

        return repository.save(booking);
    }

    @Transactional
    public void delete(Booking booking) {
        repository.delete(booking);
    }
}
