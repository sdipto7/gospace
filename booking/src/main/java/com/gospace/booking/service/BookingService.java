package com.gospace.booking.service;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.domain.BookingStatus;
import com.gospace.booking.domain.PassengerDetails;
import com.gospace.booking.dto.BookingDto;
import com.gospace.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Booking saveOrUpdate(BookingDto bookingDto) {
        Booking booking;

        if (bookingDto.isNew()) {
            booking = new Booking();

            booking.setReferenceNumber(UUID.randomUUID().toString());
            booking.setPassengerDetails(new PassengerDetails());
            booking.setStatus(NEW);

        } else {
            booking = find(bookingDto.getId());
            booking.setStatus(BookingStatus.fromLabel(bookingDto.getStatus())); //this will be set based on payment status
        }

        booking.getPassengerDetails().setName(bookingDto.getPassengerName());
        booking.getPassengerDetails().setEmail(bookingDto.getPassengerEmail());
        booking.getPassengerDetails().setPhone(bookingDto.getPassengerPhone());
        booking.setTotalSeats(bookingDto.getTotalSeats());
        booking.setTotalPrice(bookingDto.getTotalPrice());  //this will be calculated based on unit price

        return repository.save(booking);
    }

    @Transactional
    public void delete(Booking booking) {
        repository.delete(booking);
    }
}
