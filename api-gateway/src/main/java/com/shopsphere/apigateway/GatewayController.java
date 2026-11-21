
package com.shopsphere.apigateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final RestClient http = RestClient.create();

    // Product Service
    @GetMapping("/products")
    public ResponseEntity<?> products() {
        return forward(() ->
                http.get()
                        .uri(service("PRODUCT_SERVICE_URL",
                                "http://localhost:8081") + "/api/products")
                        .retrieve()
                        .body(Object.class)
        );
    }

    // Inventory Service
    @GetMapping("/inventory/{id}")
    public ResponseEntity<?> inventory(@PathVariable("id") long id) {
        return forward(() ->
                http.get()
                        .uri(service("INVENTORY_SERVICE_URL",
                                "http://localhost:8082") + "/api/inventory/" + id)
                        .retrieve()
                        .body(Object.class)
        );
    }

    // Order Service
    @PostMapping("/orders")
    public ResponseEntity<?> order(@RequestBody Object request) {
        return forward(() ->
                http.post()
                        .uri(service("ORDER_SERVICE_URL",
                                "http://localhost:8084") + "/api/orders")
                        .body(request)
                        .retrieve()
                        .body(Object.class)
        );
    }

    // Backend URL configuration
    private String service(String key, String fallback) {
        return System.getenv().getOrDefault(key, fallback);
    }

    // Common error handling
    private ResponseEntity<?> forward(BackendCall call) {
        try {
            return ResponseEntity.ok(call.execute());
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                    Map.of(
                            "error", "Backend service request failed",
                            "details", ex.getMessage() == null
                                    ? "Unknown backend error"
                                    : ex.getMessage()
                    )
            );
        }
    }

    @FunctionalInterface
    private interface BackendCall {
        Object execute();
    }
}
