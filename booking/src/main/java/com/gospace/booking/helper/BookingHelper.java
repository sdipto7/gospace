package com.gospace.booking.helper;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.dto.BookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@RequiredArgsConstructor
@Component
public class BookingHelper {

    public BookingDto getBookingDto(Booking booking) {

        return BookingDto.builder()
                .referenceNumber(booking.getReferenceNumber())
                .passengerName(booking.getPassengerDetails().getName())
                .passengerEmail(booking.getPassengerDetails().getEmail())
                .passengerPhone(booking.getPassengerDetails().getPhone())
                .totalSeats(booking.getTotalSeats())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus().getLabel())
                .build();
    }

    public List<BookingDto> getBookingDtoList(List<Booking> bookingList) {

        return bookingList.stream()
                .map(booking -> getBookingDto(booking))
                .collect(Collectors.toList());
    }
}
