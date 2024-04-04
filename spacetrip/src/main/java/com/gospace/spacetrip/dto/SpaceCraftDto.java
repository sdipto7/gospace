package com.gospace.spacetrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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

    @JsonProperty(access = WRITE_ONLY)
    private int id;

    @NotBlank
    @Size(min = 2, max = 255, message = "{valid.spacecraft.name.size}")
    private String name;

    @NotBlank
    private String manufacturer;

    @NotNull
    @Past(message = "{valid.spacecraft.manufacture.date.past}")
    private LocalDate manufactureDate;

    private int crewCapacity;

    private int passengerCapacity;

    @JsonProperty(access = WRITE_ONLY)
    private int version;
}
