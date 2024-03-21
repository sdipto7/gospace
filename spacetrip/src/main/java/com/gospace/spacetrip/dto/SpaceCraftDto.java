package com.gospace.spacetrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@Getter
@Builder
@ToString
public class SpaceCraftDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "ID", access = WRITE_ONLY)
    private int id;

    @NotNull
    @Size(min = 1, max = 255, message = "{valid.spacecraft.name.size}")
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Manufacturer")
    private String manufacturer;

    @NotNull
    @Past(message = "{valid.spacecraft.manufacture.date.past}")
    @JsonProperty("Manufacture Date")
    private LocalDate manufactureDate;

    @JsonProperty("Crew Capacity")
    private int crewCapacity;

    @JsonProperty("Passenger Capacity")
    private int passengerCapacity;

    @JsonProperty(value = "Version", access = WRITE_ONLY)
    private int version;
}