package com.gospace.spacetrip.proxy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author rumidipto
 * @since 3/25/24
 */
@Getter
@Setter
@Builder
@ToString
public class DestinationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String celestialBodyType;

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
