package com.gospace.booking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@Getter
@Builder
@ToString
public class BookingRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = WRITE_ONLY)
    private int id;

    private int tripId;

    private String referenceNumber;

    @NotBlank(message = "{valid.booking.passenger.name.notBlank}")
    @Size(min = 2, max = 256, message = "{valid.booking.passenger.name.size}")
    private String passengerName;

    @NotBlank(message = "{valid.booking.passenger.email.notBlank}")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String passengerEmail;

    @NotBlank(message = "{valid.booking.passenger.phone.notBlank}")
    @Pattern(message = "", regexp = "^([+]|[00]{2})([0-9]|[ -])*")
    private String passengerPhone;

    @Min(value = 1, message = "{valid.booking.total.booked.seats.min}")
    private int totalSeats;

    private String status; //status will be updated based on payment service later.

    @JsonProperty(access = WRITE_ONLY)
    private int version;

    @JsonIgnore
    public boolean isNew() {
        return this.id == 0;
    }
}
