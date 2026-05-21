package com.amazon.oa_ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amazon.oa_ecommerce.model.Order;
import com.amazon.oa_ecommerce.model.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.userId = (SELECT u.id FROM User u WHERE u.username = :username)")
    List<Order> findOrdersByUsername(@Param("username") String username);
}