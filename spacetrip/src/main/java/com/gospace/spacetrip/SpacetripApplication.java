package com.gospace.spacetrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpacetripApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpacetripApplication.class, args);
    }

}
