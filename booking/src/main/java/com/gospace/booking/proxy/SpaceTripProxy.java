package com.gospace.booking.proxy;

import com.gospace.booking.proxy.dto.SpaceTripDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author rumidipto
 * @since 4/14/24
 */
@FeignClient(name = "spacetrip", url = "localhost:8080")
public interface SpaceTripProxy {

    @ResponseBody
    @GetMapping("/api/spacetrip/details/{id}")
    ResponseEntity<SpaceTripDetailsDto> showDetails(@PathVariable int id);

    @ResponseBody
    @GetMapping("/api/spacetrip/available-seats/{id}")
    ResponseEntity<Integer> getAvailableSeats(@PathVariable int id);

    @ResponseBody
    @GetMapping("/api/spacetrip/price/{id}")
    ResponseEntity<BigDecimal> getPrice(@PathVariable int id);
}
