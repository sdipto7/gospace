package com.gospace.booking.helper;

import com.gospace.booking.dto.ValidationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author rumidipto
 * @since 3/29/24
 */
@RequiredArgsConstructor
@Component
public class ApiValidationHelper {

    private final MessageSourceAccessor msa;

    public ResponseEntity<ValidationResponseDto> getValidationResponseDto(List<ObjectError> globalErrors,
                                                                          List<FieldError> fieldErrors) {

        ValidationResponseDto validationResponseDto = new ValidationResponseDto();

        setupGlobalErrors(validationResponseDto, globalErrors);

        setupFieldErrors(validationResponseDto, fieldErrors);

        prepareFormattedErrorMessage(validationResponseDto);

        return getResponseEntity(validationResponseDto);
    }

    private void setupGlobalErrors(ValidationResponseDto dto, List<ObjectError> globalErrors) {
        if (!globalErrors.isEmpty()) {
            dto.setGlobalErrorCount(globalErrors.size());

            for (ObjectError ge : globalErrors) {
                dto.addGlobalError(getValidationMessage(ge));
            }
        }
    }

    private void setupFieldErrors(ValidationResponseDto dto, List<FieldError> fieldErrors) {
        if (!fieldErrors.isEmpty()) {
            dto.setFieldErrorCount(fieldErrors.size());

            for (FieldError fe : fieldErrors) {
                String errorMessage = getValidationMessage(fe);
                String fieldBindPath = fe.getField();

                dto.addFieldError(fieldBindPath, errorMessage);
            }
        }
    }

    public void prepareFormattedErrorMessage(ValidationResponseDto dto) {
        StringBuilder sb = new StringBuilder();

        for (String error : dto.getGlobalErrors()) {
            sb.append(error).append("; ");
        }

        for (Map.Entry<String, List<String>> entry : dto.getFieldErrors().entrySet()) {
            String fieldName = entry.getKey();
            sb.append(fieldName).append(": ");

            for (String error : entry.getValue()) {
                sb.append(error).append("; ");
            }
        }

        dto.setFormattedErrorMessage(sb.toString().trim().replaceAll(";$", ""));
    }

    private String getValidationMessage(ObjectError error) {
        try {
            return msa.getMessage(requireNonNull(error.getCode()), error.getArguments());
        } catch (NoSuchMessageException ex) {
            return error.getDefaultMessage();
        }
    }

    private ResponseEntity<ValidationResponseDto> getResponseEntity(ValidationResponseDto dto) {
        HttpHeaders jsonResponseHeaders = new HttpHeaders();
        jsonResponseHeaders.add("Content-Type", "application/json");

        return new ResponseEntity<>(dto, jsonResponseHeaders, BAD_REQUEST);
    }
}
