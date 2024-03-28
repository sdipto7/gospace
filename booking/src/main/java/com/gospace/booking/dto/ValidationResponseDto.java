package com.gospace.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidationResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("Global Error Count")
    private int globalErrorCount;

    @JsonProperty("Field Error Count")
    private int fieldErrorCount;

    @JsonProperty("Global Errors")
    private List<String> globalErrors;

    @JsonProperty("Field Errors")
    private Map<String, List<String>> fieldErrors;

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

    public String getFormattedErrorMessage() {
        StringBuilder sb = new StringBuilder();

        for (String error : getGlobalErrors()) {
            sb.append(error).append("; ");
        }

        for (Map.Entry<String, List<String>> entry : getFieldErrors().entrySet()) {
            String fieldName = entry.getKey();
            sb.append(fieldName).append(": ");

            for (String error : entry.getValue()) {
                sb.append(error).append("; ");
            }
        }

        return sb.toString()
                .trim()
                .replaceAll(";$", "");
    }
}
