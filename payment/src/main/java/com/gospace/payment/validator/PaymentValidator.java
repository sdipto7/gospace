package com.gospace.payment.validator;

import com.gospace.payment.domain.Payment;
import com.gospace.payment.domain.PaymentMethod;
import com.gospace.payment.dto.PaymentDto;
import com.gospace.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author rumidipto
 * @since 3/31/24
 */
@RequiredArgsConstructor
@Component
public class PaymentValidator implements Validator {

    private final PaymentService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentDto paymentDto = (PaymentDto) target;

        if (isNull(PaymentMethod.fromLabel(paymentDto.getMethod()))) {
            errors.rejectValue("method", "valid.payment.not.exists.method", "Invalid payment method");
        }

        Payment payment = service.findByReferenceNumber(paymentDto.getReferenceNumber());

        if (nonNull(payment) && payment.isSuccessful()) {
            errors.rejectValue("referenceNumber",
                    "valid.referenceNumber.already.paid",
                    new Object[]{paymentDto.getReferenceNumber()},
                    "Payment is already done for the booking reference number: {0}");
        }
    }
}
