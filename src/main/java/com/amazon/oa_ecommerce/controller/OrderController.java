package com.amazon.oa_ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.oa_ecommerce.dto.OrderItemResponse;
import com.amazon.oa_ecommerce.dto.OrderResponse;
import com.amazon.oa_ecommerce.model.Order;
import com.amazon.oa_ecommerce.model.OrderStatus;
import com.amazon.oa_ecommerce.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody Order order) {
        Order saved = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(toOrderResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(toOrderResponse(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders().stream()
            .map(this::toOrderResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.getOrdersByUser(userId).stream()
            .map(this::toOrderResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Order updated = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(toOrderResponse(updated));
    }

    private OrderResponse toOrderResponse(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setStatus(order.getStatus().name());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());
        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream().map(item -> {
                OrderItemResponse itemDto = new OrderItemResponse();
                itemDto.setProductId(item.getProductId());
                itemDto.setProductName(item.getProductName());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setPrice(item.getPrice());
                return itemDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }
}