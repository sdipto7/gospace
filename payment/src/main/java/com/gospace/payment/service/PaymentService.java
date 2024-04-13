package com.gospace.payment.service;

import com.gospace.payment.domain.Payment;
import com.gospace.payment.domain.PaymentMethod;
import com.gospace.payment.dto.PaymentDto;
import com.gospace.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.gospace.payment.domain.PaymentStatus.INITIATED;
import static com.gospace.payment.domain.PaymentStatus.SUCCESSFUL;
import static java.util.Objects.isNull;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository repository;

    public Payment find(int id) {
        Optional<Payment> paymentOptional = repository.findById(id);

        return paymentOptional.isPresent() ? paymentOptional.get() : null;
    }

    public Payment findByReferenceNumber(String referenceNumber) {
        Optional<Payment> paymentOptional = repository.findByReferenceNumberIgnoreCase(referenceNumber);

        return paymentOptional.isPresent() ? paymentOptional.get() : null;
    }

    @Transactional
    public Payment save(PaymentDto paymentDto) {
        Payment payment = findByReferenceNumber(paymentDto.getReferenceNumber());

        if (isNull(payment)) {
            payment = new Payment();

            payment.setReferenceNumber(paymentDto.getReferenceNumber());//decide later when connect with booking service
            payment.setAmount(paymentDto.getAmount());
            payment.setMethod(PaymentMethod.fromLabel(paymentDto.getMethod()));
            payment.setStatus(INITIATED);
            payment.setTransactionTime(LocalDateTime.now());

        } else if (!payment.isSuccessful()) {
            payment.setTransactionTime(LocalDateTime.now());
            payment.setStatus(SUCCESSFUL); //will add logic later before payment successful
        }

        return repository.save(payment);
    }
}
