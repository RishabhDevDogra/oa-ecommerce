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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.oa_ecommerce.dto.OrderItemResponse;
import com.amazon.oa_ecommerce.dto.OrderRequest;
import com.amazon.oa_ecommerce.dto.OrderResponse;
import com.amazon.oa_ecommerce.model.Order;
import com.amazon.oa_ecommerce.model.OrderItem;
import com.amazon.oa_ecommerce.model.OrderStatus;
import com.amazon.oa_ecommerce.security.JwtUtil;
import com.amazon.oa_ecommerce.service.OrderService;
import com.amazon.oa_ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody OrderRequest orderRequest) {
        // Extract JWT token from header
        String token = authHeader.replace("Bearer ", "");
        String username = jwtUtil.getUsername(token);
        Long userId = userService.findByUsername(username).getId();

        Order order = new Order();
        order.setUserId(userId);
        System.out.println("[OrderController] Creating order for userId: " + userId + ", items: " + orderRequest.getItems());
        order.setItems(orderRequest.getItems().stream().map(req -> {
            OrderItem item = new OrderItem();
            System.out.println("[OrderController] Setting productId: " + req.getProductId() + ", quantity: " + req.getQuantity());
            item.setProductId(req.getProductId());
            item.setQuantity(req.getQuantity());
            // Product name and price will be set in service layer for accuracy
            return item;
        }).collect(Collectors.toList()));
        
        Order saved = orderService.createOrder(order);
        System.out.println("[OrderController] Order created: " + saved);
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
        if (orders == null) orders = List.of();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.getOrdersByUser(userId).stream()
            .map(this::toOrderResponse)
            .collect(Collectors.toList());
        if (orders == null) orders = List.of();
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