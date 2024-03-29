package com.gospace.spacetrip.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
public class SpaceCraft extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Manufacturer manufacturer;

    @Temporal(value = TemporalType.DATE)
    private LocalDate manufactureDate;

    private int crewCapacity;

    private int passengerCapacity;
}