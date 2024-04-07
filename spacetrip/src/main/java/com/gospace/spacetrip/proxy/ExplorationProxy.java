package com.gospace.spacetrip.proxy;

import com.gospace.spacetrip.proxy.dto.DestinationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rumidipto
 * @since 4/7/24
 */
@FeignClient(name = "exploration", url = "localhost:8090")
public interface ExplorationProxy {

    @ResponseBody
    @GetMapping("/api/destination/{id}")
    ResponseEntity<DestinationDto> show(@PathVariable int id);

    @ResponseBody
    @GetMapping("/api/destination/name/{id}")
    ResponseEntity<String> getDestinationName(@PathVariable int id);
}
