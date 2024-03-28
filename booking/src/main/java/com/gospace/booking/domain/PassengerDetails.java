package com.gospace.booking.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * @author rumidipto
 * @since 3/28/24
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PassengerDetails {

    private String name;

    private String email;

    private String phone;
}
