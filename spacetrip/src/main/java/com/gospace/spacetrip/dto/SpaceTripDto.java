package com.gospace.spacetrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@Getter
@Builder
@ToString
public class SpaceTripDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "ID", access = WRITE_ONLY)
    private int id;

    @NotNull
    @Valid
    @JsonProperty("Space Craft")
    private SpaceCraftDto spaceCraftDto;

    @NotNull
    @Future
    @JsonProperty("Departure Time")
    private LocalDateTime departureTime;

    @NotNull
    @Future
    @JsonProperty("Estimated Arrival Time")
    private LocalDateTime estimatedArrivalTime;

    @JsonProperty(value = "Trip Duration", access = READ_ONLY)
    private String tripDuration;

    @DecimalMin(value = "1000000.00")
    @DecimalMax(value = "1000000000.00")
    @JsonProperty("Ticket Price")
    private BigDecimal ticketPrice;

    @JsonProperty("Total Seats")
    @Min(value = 1, message = "{valid.spacetrip.total.seats.min}")
    private int totalSeats;

    @JsonProperty("Available Seats")
    @Min(value = 0, message = "{valid.spacetrip.available.seats.min}")
    private int availableSeats;

    @JsonProperty(value = "Version", access = WRITE_ONLY)
    private int version;
}