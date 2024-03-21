package com.gospace.spacetrip.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author rumidipto
 * @since 3/17/24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
public class SpaceTrip extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private SpaceCraft spaceCraft;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime departureTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime estimatedArrivalTime;

    private BigDecimal ticketPrice;

    private int totalSeats;

    private int availableSeats;
}