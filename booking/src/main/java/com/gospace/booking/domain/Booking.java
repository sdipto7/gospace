package com.gospace.booking.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

import static com.gospace.booking.domain.BookingStatus.CONFIRMED;

/**
 * @author rumidipto
 * @since 3/28/24
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Booking extends Persistent {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String referenceNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "passenger_name")),
            @AttributeOverride(name = "email", column = @Column(name = "passenger_email")),
            @AttributeOverride(name = "phone", column = @Column(name = "passenger_phone"))
    })
    private PassengerDetails passengerDetails;

    private int totalSeats;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus status;

    public boolean isConfirmedBooking() {
        return status.equals(CONFIRMED);
    }
}
