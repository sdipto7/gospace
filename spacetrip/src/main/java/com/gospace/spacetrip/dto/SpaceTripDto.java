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

    @JsonProperty(access = WRITE_ONLY)
    private int id;

    @JsonProperty(access = WRITE_ONLY)
    private int destinationId;

    @JsonProperty(access = READ_ONLY)
    private String destinationName;

    @NotNull
    @Valid
    private SpaceCraftDto spaceCraftDto;

    @NotNull
    @Future
    private LocalDateTime departureTime;

    @NotNull
    @Future
    private LocalDateTime estimatedArrivalTime;

    @JsonProperty(access = READ_ONLY)
    private String tripDuration;

    @DecimalMin("0.0")
    @DecimalMax("1000000000.00")
    private BigDecimal ticketPrice;

    @Min(value = 1, message = "{valid.spacetrip.total.seats.min}")
    private int totalSeats;

    @Min(value = 0, message = "{valid.spacetrip.available.seats.min}")
    private int availableSeats;

    @JsonProperty(access = WRITE_ONLY)
    private int version;
}
