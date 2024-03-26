package com.gospace.exploration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rumidipto
 * @since 3/26/24
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("Error Message")
    private String errorMsg;

    @JsonProperty("Details")
    private String details;
}