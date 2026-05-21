package com.amazon.oa_ecommerce.repository;

import com.amazon.oa_ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}