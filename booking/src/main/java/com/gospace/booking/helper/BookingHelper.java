package com.gospace.booking.helper;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.domain.BookingStatus;
import com.gospace.booking.domain.PassengerDetails;
import com.gospace.booking.dto.BookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gospace.booking.domain.BookingStatus.NEW;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@RequiredArgsConstructor
@Component
public class BookingHelper {

    public BookingDto getDtoFromBooking(Booking booking) {

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

    public List<BookingDto> getDtoListFromBookingList(List<Booking> bookingList) {

        return bookingList.stream()
                .map(booking -> getDtoFromBooking(booking))
                .collect(Collectors.toList());
    }

    public Booking getBookingFromDto(BookingDto bookingDto) {

        return Booking.builder()
                .referenceNumber(UUID.randomUUID().toString())
                .passengerDetails(PassengerDetails.builder()
                        .name(bookingDto.getPassengerName())
                        .email(bookingDto.getPassengerEmail())
                        .phone(bookingDto.getPassengerPhone())
                        .build())
                .totalSeats(bookingDto.getTotalSeats())
                .totalPrice(bookingDto.getTotalPrice()) //this will be calculated based on unit price
                .status(NEW)
                .build();
    }

    public void updateEntityFromDto(Booking booking, BookingDto bookingDto) {
        booking.getPassengerDetails().setName(bookingDto.getPassengerName());
        booking.getPassengerDetails().setEmail(bookingDto.getPassengerEmail());
        booking.getPassengerDetails().setPhone(bookingDto.getPassengerPhone());
        booking.setTotalSeats(bookingDto.getTotalSeats());
        booking.setTotalPrice(bookingDto.getTotalPrice());
        booking.setStatus(BookingStatus.fromLabel(bookingDto.getStatus()));
        booking.setVersion(bookingDto.getVersion());
    }
}
