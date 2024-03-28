package com.gospace.booking.service;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.domain.BookingStatus;
import com.gospace.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<Booking> optionalBooking = repository.findByReferenceNumber(referenceNumber);

        return optionalBooking.isPresent() ? optionalBooking.get() : null;
    }

    public List<Booking> findAll() {
        return repository.findAll();
    }

    public List<Booking> findAllByStatus(BookingStatus status) {
        return repository.findAllByStatus(status);
    }

    @Transactional
    public Booking saveOrUpdate(Booking booking) {
        return repository.save(booking);
    }

    @Transactional
    public void delete(Booking booking) {
        repository.delete(booking);
    }
}
