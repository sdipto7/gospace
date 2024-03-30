package com.gospace.payment.controller;

import com.gospace.payment.domain.Payment;
import com.gospace.payment.dto.PaymentDto;
import com.gospace.payment.dto.ValidationResponseDto;
import com.gospace.payment.exception.PaymentNotFoundException;
import com.gospace.payment.helper.ApiValidationHelper;
import com.gospace.payment.helper.PaymentHelper;
import com.gospace.payment.service.PaymentService;
import com.gospace.payment.validator.PaymentValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService service;

    private final PaymentHelper helper;

    private final PaymentValidator validator;

    private final ApiValidationHelper apiValidationHelper;

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @ResponseBody
    @GetMapping("/payment/{id}")
    public ResponseEntity<PaymentDto> show(@PathVariable int id) {
        Payment payment = service.find(id);

        if (isNull(payment)) {
            log.info("[API:PAYMENT:SHOW] Error while processing Payment show with ID: {}", id);

            throw new PaymentNotFoundException(String.format("Invalid id! No Payment found for the id: %d", id));
        }

        return new ResponseEntity<>(helper.getDtoFromPayment(payment), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/payment")
    public ResponseEntity<PaymentDto> showByReferenceNumber(@RequestParam String referenceNumber) {
        Payment payment = service.findByReferenceNumber(referenceNumber);

        if (isNull(payment)) {
            log.info("[API:PAYMENT:SHOW:REFERENCE-NUMBER] Error while processing Payment show with ReferenceNumber: {}", referenceNumber);

            throw new PaymentNotFoundException(
                    String.format("Invalid reference number! No Payment found for the ReferenceNumber: %d", referenceNumber)
            );
        }

        return new ResponseEntity<>(helper.getDtoFromPayment(payment), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/payment", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody PaymentDto paymentDto, Errors errors) {

        validator.validate(paymentDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:PAYMENT:SAVE] Error while processing Payment save, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:PAYMENT:SAVE] Successfully validated Payment Data, RequestBody: {}", paymentDto);

        Payment payment = service.findByReferenceNumber(paymentDto.getReferenceNumber());

        payment = service.save((nonNull(payment) && !payment.isSuccessful())
                ? payment
                : helper.getPaymentFromDto(paymentDto));

        log.info("[API:PAYMENT:SAVE] Successfully processed Payment save, Response: {}", payment);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(payment.getId())
                        .toUri()
                ).build();
    }
}
