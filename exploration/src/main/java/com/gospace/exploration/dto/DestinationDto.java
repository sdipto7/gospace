package com.gospace.exploration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * @author rumidipto
 * @since 3/25/24
 */
@Getter
@Builder
@ToString
public class DestinationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = WRITE_ONLY)
    private int id;

    @NotBlank(message = "{valid.destination.name.notBlank}")
    @Size(min = 2, max = 256, message = "{valid.destination.name.size}")
    private String name;

    @NotBlank
    private String celestialBodyType;

    @Size(max = 2048)
    private String description;

    @Size(max = 2048)
    private String surfaceFeatures;

    @Size(max = 2048)
    private String atmosphere;

    @Digits(integer = 60, fraction = 30)
    @DecimalMin("0")
    private BigDecimal distanceFromEarth;

    @Digits(integer = 60, fraction = 10)
    @DecimalMin("0")
    private BigDecimal diameter;

    @Digits(integer = 60, fraction = 10)
    @DecimalMin("0")
    private BigDecimal mass;

    @Digits(integer = 19, fraction = 10)
    @DecimalMin("0")
    private BigDecimal gravity;

    @Digits(integer = 10, fraction = 2)
    @DecimalMin("-273.15")
    private BigDecimal minimumTemperature;

    @Digits(integer = 10, fraction = 2)
    @DecimalMin("-273.15")
    private BigDecimal maximumTemperature;

    @JsonProperty(access = WRITE_ONLY)
    private int version;

    @JsonIgnore
    public boolean isNew() {
        return this.id == 0;
    }
}
