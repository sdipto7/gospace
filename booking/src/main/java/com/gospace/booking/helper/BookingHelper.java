package com.gospace.booking.helper;

import com.gospace.booking.domain.Booking;
import com.gospace.booking.dto.BookingResponseDto;
import com.gospace.booking.proxy.SpaceTripProxy;
import com.gospace.booking.proxy.dto.SpaceTripDetailsDto;
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

    private final SpaceTripProxy spaceTripProxy;

    public BookingResponseDto getBookingResponseDto(Booking booking) {
        SpaceTripDetailsDto spaceTripDetailsDto = spaceTripProxy.showDetails(booking.getTripId()).getBody();

        return BookingResponseDto.builder()
                .spaceTripDetailsDto(spaceTripDetailsDto)
                .referenceNumber(booking.getReferenceNumber())
                .passengerName(booking.getPassengerDetails().getName())
                .passengerEmail(booking.getPassengerDetails().getEmail())
                .passengerPhone(booking.getPassengerDetails().getPhone())
                .totalSeats(booking.getTotalSeats())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus().getLabel())
                .build();
    }

    public List<BookingResponseDto> getBookingResponseDtoList(List<Booking> bookingList) {

        return bookingList.stream()
                .map(booking -> getBookingResponseDto(booking))
                .collect(Collectors.toList());
    }
}
