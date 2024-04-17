package com.gospace.spacetrip.proxy;

import com.gospace.spacetrip.proxy.dto.SpaceCraftDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rumidipto
 * @since 4/16/24
 */
@FeignClient(name = "spacecraft", url = "localhost:8070")
public interface SpaceCraftProxy {

    @ResponseBody
    @GetMapping("/api/spacecraft/proxy/v1/exists/{id}")
    ResponseEntity<Boolean> hasSpaceCraft(@PathVariable int id);

    @ResponseBody
    @GetMapping("/api/spacecraft/proxy/v1/{id}")
    ResponseEntity<SpaceCraftDto> getSpaceCraftDto(@PathVariable int id);

    @ResponseBody
    @GetMapping("/api/spacecraft/proxy/v1/name/{id}")
    ResponseEntity<String> getSpaceCraftName(@PathVariable int id);
}
