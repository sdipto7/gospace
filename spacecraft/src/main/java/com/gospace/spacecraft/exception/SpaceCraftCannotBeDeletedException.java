package com.gospace.spacecraft.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author rumidipto
 * @since 9/30/24
 */
@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(BAD_REQUEST)
public class SpaceCraftCannotBeDeletedException extends RuntimeException {

    private String message;
}
