package com.gospace.payment.helper;

import com.gospace.payment.domain.Payment;
import com.gospace.payment.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@RequiredArgsConstructor
@Component
public class PaymentHelper {

    public PaymentDto getPaymentDto(Payment payment) {

        return PaymentDto.builder()
                .referenceNumber(payment.getReferenceNumber())
                .amount(payment.getAmount())
                .method(payment.getMethod().getLabel())
                .status(payment.getStatus().getLabel())
                .transactionTime(payment.getTransactionTime())
                .build();
    }

    public List<PaymentDto> getPaymentDtoList(List<Payment> paymentList) {

        return paymentList.stream()
                .map(payment -> getPaymentDto(payment))
                .collect(Collectors.toList());
    }
}
