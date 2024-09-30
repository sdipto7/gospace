package com.gospace.spacetrip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * @author rumidipto
 * @since 3/20/24
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidationResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;

    private int globalErrorCount;

    private int fieldErrorCount;

    private List<String> globalErrors;

    private Map<String, List<String>> fieldErrors;

    private String formattedErrorMessage;

    public ValidationResponseDto() {
        this.timestamp = LocalDateTime.now();
        this.globalErrors = new ArrayList<>();
        this.fieldErrors = new LinkedHashMap<>();
    }

    public void addGlobalError(String errorMessage) {
        this.globalErrors.add(errorMessage);
    }

    public void addFieldError(String fieldName, String errorMessage) {
        List<String> fieldErrorList = this.fieldErrors.get(fieldName);

        if (isNull(fieldErrorList)) {
            fieldErrorList = new ArrayList<>();
        }

        fieldErrorList.add(errorMessage);
        this.fieldErrors.put(fieldName, fieldErrorList);
    }
}
