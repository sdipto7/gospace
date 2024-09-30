package com.gospace.exploration.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author rumidipto
 * @since 9/30/24
 */
@FeignClient(name = "spacetrip")
public interface SpaceTripProxy {

    @GetMapping("/api/spacetrip/proxy/v1/exists-by-destination/{destinationId}")
    ResponseEntity<Boolean> hasSpaceTripByDestinationId(@PathVariable int destinationId);
}
