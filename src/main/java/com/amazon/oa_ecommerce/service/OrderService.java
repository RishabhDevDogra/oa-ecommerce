package com.amazon.oa_ecommerce.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.amazon.oa_ecommerce.exception.ResourceNotFoundException;
import com.amazon.oa_ecommerce.model.Order;
import com.amazon.oa_ecommerce.model.OrderItem;
import com.amazon.oa_ecommerce.model.OrderStatus;
import com.amazon.oa_ecommerce.model.Product;
import com.amazon.oa_ecommerce.repository.OrderRepository;
import com.amazon.oa_ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Order createOrder(Order order) {
    if (order.getItems() == null || order.getItems().isEmpty()) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Order must have at least one item");
    }
    for (OrderItem item : order.getItems()) {
        if (item.getProductId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Each item must have a valid productId and quantity > 0");
        }
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + item.getProductId()));

        if (product.getStock() < item.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock for product: " + product.getName() + " available: " + product.getStock());
        }

        // Set price and product name for accuracy
        item.setPrice(product.getPrice());
        item.setProductName(product.getName());

        // Deduct stock
        product.setStock(product.getStock() - item.getQuantity());
        productRepository.save(product);
    }

    // Calculate total
    double total = order.getItems().stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();

    order.setTotalAmount(total);
    order.setStatus(OrderStatus.PENDING);

    return orderRepository.save(order);
}

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}