package com.gospace.booking.service;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.domain.BookingStatus;
import com.gospace.booking.domain.PassengerDetails;
import com.gospace.booking.dto.BookingRequestDto;
import com.gospace.booking.proxy.SpaceTripProxy;
import com.gospace.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.gospace.booking.domain.BookingStatus.NEW;

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
            booking.setStatus(NEW);

        } else {
            booking = find(bookingRequestDto.getId());
            booking.setStatus(BookingStatus.fromLabel(bookingRequestDto.getStatus())); //this will be set based on payment status
        }

        booking.getPassengerDetails().setName(bookingRequestDto.getPassengerName());
        booking.getPassengerDetails().setEmail(bookingRequestDto.getPassengerEmail());
        booking.getPassengerDetails().setPhone(bookingRequestDto.getPassengerPhone());
        booking.setTotalSeats(bookingRequestDto.getTotalSeats());

        BigDecimal ticketPrice = spaceTripProxy.getPrice(bookingRequestDto.getTripId()).getBody();
        booking.setTotalPrice(ticketPrice.multiply(BigDecimal.valueOf(bookingRequestDto.getTotalSeats())));

        return repository.save(booking);
    }

    @Transactional
    public void delete(Booking booking) {
        repository.delete(booking);
    }
}
