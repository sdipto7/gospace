package com.gospace.spacetrip.proxy;

import com.gospace.spacetrip.proxy.dto.SpaceCraftDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author rumidipto
 * @since 4/16/24
 */
@FeignClient(name = "spacecraft")
public interface SpaceCraftProxy {

    @GetMapping("/api/spacecraft/proxy/v1/exists/{id}")
    ResponseEntity<Boolean> hasSpaceCraft(@PathVariable int id);

    @GetMapping("/api/spacecraft/proxy/v1/{id}")
    ResponseEntity<SpaceCraftDto> getSpaceCraftDto(@PathVariable int id);

    @GetMapping("/api/spacecraft/proxy/v1/name/{id}")
    ResponseEntity<String> getSpaceCraftName(@PathVariable int id);
}
