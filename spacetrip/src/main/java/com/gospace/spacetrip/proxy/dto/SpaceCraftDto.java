package com.gospace.spacetrip.proxy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@Getter
@Setter
@Builder
@ToString
public class SpaceCraftDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String manufacturer;

    private LocalDate manufactureDate;

    private int crewCapacity;

    private int passengerCapacity;
}
