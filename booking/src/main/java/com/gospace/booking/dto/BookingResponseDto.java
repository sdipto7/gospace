package com.gospace.booking.dto;

import com.gospace.booking.proxy.dto.SpaceTripDetailsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author rumidipto
 * @since 4/14/24
 */
@Getter
@Builder
@ToString
public class BookingResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private SpaceTripDetailsDto spaceTripDetailsDto;

    private String referenceNumber;

    private String passengerName;

    private String passengerEmail;

    private String passengerPhone;

    private int totalSeats;

    private int totalPrice; //this will be read only field as it will be calculated by unit price later.

    private String status; //status will be updated based on payment service later.
}
