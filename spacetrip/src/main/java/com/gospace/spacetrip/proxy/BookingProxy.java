package com.gospace.spacetrip.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author rumidipto
 * @since 9/30/24
 */
@FeignClient(name = "booking")
public interface BookingProxy {

    @GetMapping("/api/booking/proxy/v1/exists-by-spacetrip/{spaceTripId}")
    ResponseEntity<Boolean> hasBookingBySpaceTripId(@PathVariable int spaceTripId);
}
