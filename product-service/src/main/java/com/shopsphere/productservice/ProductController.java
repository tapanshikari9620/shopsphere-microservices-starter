package com.shopsphere.productservice;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {
  public record Product(long id, String name, double price) {}
  @GetMapping
  public List<Product> all() {
    return List.of(new Product(101, "Laptop", 65000), new Product(102, "Keyboard", 1800),
                   new Product(103, "Monitor", 12500));
  }
  @GetMapping("/{id}")
  public Product byId(@PathVariable long id) {
    return all().stream().filter(p -> p.id() == id).findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
  }
}
