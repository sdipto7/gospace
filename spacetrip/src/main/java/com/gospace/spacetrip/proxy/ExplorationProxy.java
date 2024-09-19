package com.gospace.spacetrip.proxy;

import com.gospace.spacetrip.proxy.dto.DestinationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author rumidipto
 * @since 4/7/24
 */
@FeignClient(name = "exploration")
public interface ExplorationProxy {

    @GetMapping("/api/destination/proxy/v1/exists/{id}")
    ResponseEntity<Boolean> hasDestination(@PathVariable int id);

    @GetMapping("/api/destination/proxy/v1/{id}")
    ResponseEntity<DestinationDto> getDestinationDto(@PathVariable int id);

    @GetMapping("/api/destination/proxy/v1/name/{id}")
    ResponseEntity<String> getDestinationName(@PathVariable int id);
}
