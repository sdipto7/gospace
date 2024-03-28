package com.gospace.booking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(NOT_ACCEPTABLE)
public class InvalidBookingStatusException extends RuntimeException {

    private String message;
}
