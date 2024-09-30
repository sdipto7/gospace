package com.gospace.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;

    private String errorMsg;

    private String details;
}
