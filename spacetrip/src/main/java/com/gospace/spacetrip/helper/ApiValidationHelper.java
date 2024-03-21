package com.gospace.spacetrip.helper;

import com.gospace.spacetrip.dto.ValidationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author rumidipto
 * @since 3/21/24
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

        return getResponseEntity(validationResponseDto);
    }

    private void setupGlobalErrors(ValidationResponseDto validationResponseDto,
                                   List<ObjectError> globalErrors) {

        if (!globalErrors.isEmpty()) {
            validationResponseDto.setGlobalErrorCount(globalErrors.size());

            for (ObjectError ge : globalErrors) {
                validationResponseDto.addGlobalError(getValidationMessage(ge));
            }
        }
    }

    private void setupFieldErrors(ValidationResponseDto validationResponseDto,
                                  List<FieldError> fieldErrors) {

        if (!fieldErrors.isEmpty()) {
            validationResponseDto.setFieldErrorCount(fieldErrors.size());

            for (FieldError fe : fieldErrors) {
                String errorMessage = getValidationMessage(fe);
                String fieldBindPath = fe.getField();

                validationResponseDto.addFieldError(fieldBindPath, errorMessage);
            }
        }
    }

    private String getValidationMessage(ObjectError error) {
        try {
            return msa.getMessage(error.getCode(), error.getArguments());
        } catch (NoSuchMessageException ex) {
            return error.getDefaultMessage();
        }
    }

    private ResponseEntity<ValidationResponseDto> getResponseEntity(ValidationResponseDto validationResponseDto) {
        HttpHeaders jsonResponseHeaders = new HttpHeaders();
        jsonResponseHeaders.add("Content-Type", "application/json");

        return new ResponseEntity<>(validationResponseDto, jsonResponseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}