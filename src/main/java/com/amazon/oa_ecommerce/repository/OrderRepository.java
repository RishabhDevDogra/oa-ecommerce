package com.amazon.oa_ecommerce.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.amazon.oa_ecommerce.model.Order;
import com.amazon.oa_ecommerce.model.OrderStatus;

@Repository
public class OrderRepository {

    private final List<Order> orders = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Order> findAll() {
        return orders;
    }

    public List<Order> findByUserId(Long userId) {
        return orders.stream().filter(o -> o.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orders.stream().filter(o -> o.getStatus().equals(status)).collect(Collectors.toList());
    }

    public Optional<Order> findById(Long id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(counter.incrementAndGet());
        }
        orders.add(order);
        return order;
    }
}