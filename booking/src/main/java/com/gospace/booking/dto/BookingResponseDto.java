package com.gospace.booking.dto;

import com.gospace.booking.proxy.dto.SpaceTripDetailsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author rumidipto
 * @since 4/14/24
 */
@Getter
@Setter
@Builder
@ToString
public class BookingResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private SpaceTripDetailsDto spaceTripDetailsDto;

    private String referenceNumber;

    private String passengerName;

    private String passengerEmail;

    private String passengerPhone;

    private int totalSeats;

    private BigDecimal totalPrice;

    private String status; //status will be updated based on payment service later.

    private int version;
}
