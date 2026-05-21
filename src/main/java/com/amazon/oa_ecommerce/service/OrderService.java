package com.amazon.oa_ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
        // Step 1 - validate and deduct stock
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + item.getProductId()));

            // Step 2 - check stock
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName() 
                    + " available: " + product.getStock());
            }

            // Step 3 - deduct stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Step 4 - calculate total
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