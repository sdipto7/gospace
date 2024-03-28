package com.gospace.booking.repository;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findByReferenceNumber(String referenceNumber);

    List<Booking> findAllByStatus(BookingStatus status);
}
