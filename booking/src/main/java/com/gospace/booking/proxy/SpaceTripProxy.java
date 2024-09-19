package com.gospace.booking.proxy;

import com.gospace.booking.proxy.dto.SpaceTripDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author rumidipto
 * @since 4/14/24
 */
@FeignClient(name = "spacetrip")
public interface SpaceTripProxy {

    @GetMapping("/api/spacetrip/proxy/v1/exists/{id}")
    ResponseEntity<Boolean> hasSpaceTrip(@PathVariable int id);

    @GetMapping("/api/spacetrip/proxy/v1/details/{id}")
    ResponseEntity<SpaceTripDetailsDto> getSpaceTripDetailsDto(@PathVariable int id);

    @GetMapping("/api/spacetrip/proxy/v1/available-seats/{id}")
    ResponseEntity<Integer> getSpaceTripAvailableSeats(@PathVariable int id);

    @GetMapping("/api/spacetrip/proxy/v1/price/{id}")
    ResponseEntity<BigDecimal> getSpaceTripPrice(@PathVariable int id);

    @PutMapping("/api/spacetrip/proxy/v1/update-available-seats")
    ResponseEntity<String> updateAvailableSeats(@RequestBody Map<String, Integer> spaceTripBookedSeatMap);
}
