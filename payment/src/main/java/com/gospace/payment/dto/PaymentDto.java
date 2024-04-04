package com.gospace.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@Getter
@Builder
@ToString
public class PaymentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = WRITE_ONLY)
    private int id;

    private String referenceNumber;

    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotBlank
    private String method;

    private String status;

    @JsonProperty(access = READ_ONLY)
    private LocalDateTime transactionTime;
}
