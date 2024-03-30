package com.gospace.payment.helper;

import com.gospace.payment.domain.Payment;
import com.gospace.payment.domain.PaymentMethod;
import com.gospace.payment.domain.PaymentStatus;
import com.gospace.payment.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@RequiredArgsConstructor
@Component
public class PaymentHelper {

    public PaymentDto getDtoFromPayment(Payment payment) {

        return PaymentDto.builder()
                .referenceNumber(payment.getReferenceNumber())
                .amount(payment.getAmount())
                .method(payment.getMethod().getLabel())
                .status(payment.getStatus().getLabel())
                .transactionTime(payment.getTransactionTime())
                .build();
    }

    public List<PaymentDto> getDtoListFromPaymentList(List<Payment> paymentList) {

        return paymentList.stream()
                .map(payment -> getDtoFromPayment(payment))
                .collect(Collectors.toList());
    }

    public Payment getPaymentFromDto(PaymentDto paymentDto) {

        return Payment.builder()
                .referenceNumber(paymentDto.getReferenceNumber()) //decide later when connect with booking service
                .amount(paymentDto.getAmount())
                .method(PaymentMethod.fromLabel(paymentDto.getMethod()))
                .status(PaymentStatus.INITIATED)
                .transactionTime(LocalDateTime.now())
                .build();
    }
}
