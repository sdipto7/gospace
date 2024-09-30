package com.gospace.booking.proxy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author rumidipto
 * @since 4/7/24
 */
@Getter
@Setter
@Builder
@ToString
public class SpaceTripDetailsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private SpaceCraftDto spaceCraftDto;

    private DestinationDto destinationDto;

    private LocalDateTime departureTime;

    private LocalDateTime estimatedArrivalTime;

    private String tripDuration;

    private BigDecimal ticketPrice;

    private int totalSeats;

    private int availableSeats;
}
