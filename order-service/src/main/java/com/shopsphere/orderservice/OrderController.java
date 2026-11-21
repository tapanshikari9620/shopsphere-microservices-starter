
package com.shopsphere.orderservice;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final AtomicLong ids = new AtomicLong(1000);

    // Create a new order
    @PostMapping
    public Map<String, Object> create(@RequestBody OrderRequest request) {

        long id = ids.incrementAndGet();

        return Map.of(
                "orderId", id,
                "productId", request.productId(),
                "quantity", request.quantity(),
                "status", "CREATED"
        );
    }

    // Get order details by order ID
    @GetMapping("/{id}")
    public Map<String, Object> get(@PathVariable("id") long id) {

        return Map.of(
                "orderId", id,
                "status", "DEMO_ORDER"
        );
    }

    // Order request model
    public record OrderRequest(long productId, int quantity) {
    }
}
