package com.gospace.spacecraft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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

    private int version;

    @JsonIgnore
    public boolean isNew() {
        return this.id == 0;
    }
}
