package com.gospace.spacetrip.dto;

import com.gospace.spacetrip.proxy.dto.DestinationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author rumidipto
 * @since 4/7/24
 */
@Getter
@Builder
@ToString
public class SpaceTripDetailsDto implements Serializable {

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
