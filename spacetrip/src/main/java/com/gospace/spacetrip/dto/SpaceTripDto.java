package com.gospace.spacetrip.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@Getter
@Setter
@Builder
@ToString
public class SpaceTripDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private int destinationId;

    private String destinationName;

    private int spaceCraftId;

    private String spaceCraftName;

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

    private int version;

    @JsonIgnore
    public boolean isNew() {
        return this.id == 0;
    }
}
