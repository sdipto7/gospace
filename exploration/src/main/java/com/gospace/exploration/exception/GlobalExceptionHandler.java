package com.gospace.exploration.exception;

import com.gospace.exploration.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

/**
 * @author rumidipto
 * @since 3/26/24
 */
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DestinationNotFoundException.class)
    public final ResponseEntity<ErrorResponseDto> handleNotFoundException(DestinationNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(prepareErrorResponseDto(ex, request), NOT_FOUND);
    }

    @ExceptionHandler(DestinationCannotBeDeletedException.class)
    public final ResponseEntity<ErrorResponseDto> handleCannotBeDeletedException(DestinationCannotBeDeletedException ex, WebRequest request) {
        return new ResponseEntity<>(prepareErrorResponseDto(ex, request), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponseDto> handleAllException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(prepareErrorResponseDto(ex, request), INTERNAL_SERVER_ERROR);
    }

    private ErrorResponseDto prepareErrorResponseDto(Exception ex, WebRequest request) {
        return new ErrorResponseDto(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    }
}
