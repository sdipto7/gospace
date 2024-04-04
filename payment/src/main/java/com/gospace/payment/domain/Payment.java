package com.gospace.payment.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.gospace.payment.domain.PaymentStatus.SUCCESSFUL;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Payment extends Persistent {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String referenceNumber;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionTime;

    public boolean isSuccessful() {
        return this.status.equals(SUCCESSFUL);
    }
}
