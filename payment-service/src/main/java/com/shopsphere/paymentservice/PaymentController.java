package com.shopsphere.paymentservice;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
  public record PaymentRequest(long orderId, double amount) {}
  @PostMapping
  public Map<String, Object> pay(@RequestBody PaymentRequest request) {
    return Map.of("orderId", request.orderId(), "amount", request.amount(),
                  "status", "PAYMENT_APPROVED", "reference", "PAY-" + request.orderId());
  }
}
