package com.gospace.spacecraft.controller;

import com.gospace.spacecraft.domain.SpaceCraft;
import com.gospace.spacecraft.dto.SpaceCraftDto;
import com.gospace.spacecraft.dto.ValidationResponseDto;
import com.gospace.spacecraft.exception.SpaceCraftNotFoundException;
import com.gospace.spacecraft.helper.ApiValidationHelper;
import com.gospace.spacecraft.helper.SpaceCraftHelper;
import com.gospace.spacecraft.service.SpaceCraftService;
import com.gospace.spacecraft.validator.SpaceCraftValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author rumidipto
 * @since 3/23/24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/spacecraft")
public class SpaceCraftController {

    private final SpaceCraftService service;

    private final SpaceCraftHelper helper;

    private final SpaceCraftValidator validator;

    private final ApiValidationHelper apiValidationHelper;

    private static final Logger log = LoggerFactory.getLogger(SpaceCraftController.class);

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<SpaceCraftDto> show(@PathVariable int id) {
        SpaceCraft spaceCraft = service.find(id);

        if (isNull(spaceCraft)) {
            log.info("[API:SPACECRAFT:SHOW] Error while processing SpaceCraft show with ID: {}", id);

            throw new SpaceCraftNotFoundException(String.format("Invalid id! No SpaceCraft found for the id: %d", id));
        }

        return new ResponseEntity<>(helper.getSpaceCraftDto(spaceCraft), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/proxy/v1/exists/{id}")
    public ResponseEntity<Boolean> hasSpaceCraft(@PathVariable int id) {
        SpaceCraft spaceCraft = service.find(id);

        log.info("[API:SPACECRAFT:PROXY:V1:EXISTS] SpaceCraft with ID: {}, spaceCraft: {}", id, spaceCraft);

        return new ResponseEntity<>(nonNull(spaceCraft), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/proxy/v1/{id}")
    public ResponseEntity<SpaceCraftDto> getSpaceCraftDto(@PathVariable int id) {
        SpaceCraft spaceCraft = service.find(id);

        log.info("[API:SPACECRAFT:PROXY:V1] SpaceCraft with ID: {}, spaceCraft: {}", id, spaceCraft);

        return new ResponseEntity<>(nonNull(spaceCraft) ? helper.getSpaceCraftDto(spaceCraft) : null, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/proxy/v1/name/{id}")
    public ResponseEntity<String> getSpaceCraftName(@PathVariable int id) {
        SpaceCraft spaceCraft = service.find(id);

        log.info("[API:SPACECRAFT:PROXY:V1:NAME] SpaceCraft with ID: {}, spaceCraft: {}", id, spaceCraft);

        return new ResponseEntity<>(nonNull(spaceCraft) ? spaceCraft.getName() : null, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/all")
    public ResponseEntity<List<SpaceCraftDto>> showAll() {
        List<SpaceCraftDto> spaceCraftDtoList = helper.getSpaceCraftDtoList(service.findAll());

        return new ResponseEntity<>(spaceCraftDtoList, spaceCraftDtoList.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody SpaceCraftDto spaceCraftDto, Errors errors) {

        validator.validate(spaceCraftDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:SPACECRAFT:SAVE] Error while processing SpaceCraft save, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:SPACECRAFT:SAVE] Successfully validated SpaceCraft Data, RequestBody: {}", spaceCraftDto);

        SpaceCraft spaceCraft = service.saveOrUpdate(spaceCraftDto);

        log.info("[API:SPACECRAFT:SAVE] Successfully processed SpaceCraft save, Response: {}", spaceCraft);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(spaceCraft.getId())
                        .toUri()
                ).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody SpaceCraftDto spaceCraftDto, Errors errors) {

        validator.validate(spaceCraftDto, errors);

        if (errors.hasErrors()) {
            ResponseEntity<ValidationResponseDto> errorResponse = apiValidationHelper.getValidationResponseDto(errors.getGlobalErrors(), errors.getFieldErrors());
            log.info("[API:SPACECRAFT:UPDATE] Error while processing SpaceCraft update, Response: {}", errorResponse.getBody().getFormattedErrorMessage());

            return errorResponse;
        }

        log.info("[API:SPACECRAFT:UPDATE] Successfully validated SpaceCraft Data, RequestBody: {}", spaceCraftDto);

        SpaceCraft spaceCraft = service.saveOrUpdate(spaceCraftDto);

        log.info("[API:SPACECRAFT:UPDATE] Successfully processed SpaceCraft update, Response: {}", spaceCraft);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(spaceCraft.getId())
                        .toUri())
                .build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        SpaceCraft spaceCraft = service.find(id);

        if (isNull(spaceCraft)) {
            log.info("[API:SPACECRAFT:DELETE] Error while processing SpaceCraft delete with ID: {}", id);

            throw new SpaceCraftNotFoundException(String.format("Invalid id! No SpaceCraft found to delete for the id: %d", id));
        }

        service.delete(spaceCraft);

        log.info("[API:SPACECRAFT:DELETE] Successfully processed SpaceCraft delete with ID: {}", id);
    }
}
