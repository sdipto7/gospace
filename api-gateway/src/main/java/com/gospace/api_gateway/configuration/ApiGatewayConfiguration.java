package com.gospace.api_gateway.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author rumidipto
 * @since 5/24/24
 */
@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(getSpaceCraftApiRouteFunction())
                .route(getExplorationApiRouteFunction())
                .route(getSpaceTripApiRouteFunction())
                .route(getBookingApiRouteFunction())
                .build();
    }

    private Function<PredicateSpec, Buildable<Route>> getSpaceCraftApiRouteFunction() {
        return predicateSpec -> predicateSpec
                .path("/api/spacecraft/**")
                .uri("lb://spacecraft");
    }

    private Function<PredicateSpec, Buildable<Route>> getExplorationApiRouteFunction() {
        return predicateSpec -> predicateSpec
                .path("/api/destination/**")
                .uri("lb://exploration");
    }

    private Function<PredicateSpec, Buildable<Route>> getSpaceTripApiRouteFunction() {
        return predicateSpec -> predicateSpec
                .path("/api/spacetrip/**")
                .uri("lb://spacetrip");
    }

    private Function<PredicateSpec, Buildable<Route>> getBookingApiRouteFunction() {
        return predicateSpec -> predicateSpec
                .path("/api/booking/**")
                .uri("lb://booking");
    }
}
