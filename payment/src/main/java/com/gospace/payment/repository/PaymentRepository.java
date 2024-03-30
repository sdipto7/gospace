package com.gospace.payment.repository;

import com.gospace.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByReferenceNumberIgnoreCase(String referenceNumber);
}
