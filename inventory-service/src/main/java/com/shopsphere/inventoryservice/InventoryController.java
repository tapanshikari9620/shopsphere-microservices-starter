package com.shopsphere.inventoryservice;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @GetMapping("/{productId}")
    public Map<String, Object> availability(
            @PathVariable("productId") long productId) {

        return Map.of(
            "productId", productId,
            "available", true,
            "quantity", 25
        );
    }
}