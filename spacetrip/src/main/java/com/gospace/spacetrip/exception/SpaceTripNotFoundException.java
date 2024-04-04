package com.gospace.spacetrip.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author rumidipto
 * @since 3/19/24
 */
@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(NOT_FOUND)
public class SpaceTripNotFoundException extends RuntimeException {

    private String message;
}
