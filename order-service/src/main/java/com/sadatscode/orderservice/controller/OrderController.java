package com.sadatscode.orderservice.controller;

import com.sadatscode.orderservice.dto.OrderRequestDto;
import com.sadatscode.orderservice.model.Order;
import com.sadatscode.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuthForOrderService")
public class OrderController {
    private final OrderService service;

    @GetMapping("/{customerName}")
    public List<Order> getOrderByCustomerName(@PathVariable String customerName) {
        return service.getOrderByCustomerName(customerName);
    }
    @GetMapping
    public List<Order> getOrders() {
        return service.getOrders();
    }
    @PostMapping("/create-order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto order) {
        return  ResponseEntity.ok(service.createOrder(order));
    }
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        service.deleteOrder(orderId);
    }

}
