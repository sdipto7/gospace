package com.gospace.exploration.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author rumidipto
 * @since 3/25/24
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Destination extends Persistent {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CelestialBodyType celestialBodyType;

    private String description;

    private String surfaceFeatures;

    private String atmosphere;

    private BigDecimal distanceFromEarth;

    private BigDecimal diameter;

    private BigDecimal mass;

    private BigDecimal gravity;

    private BigDecimal minimumTemperature;

    private BigDecimal maximumTemperature;
}
