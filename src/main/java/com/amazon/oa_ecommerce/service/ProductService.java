package com.amazon.oa_ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amazon.oa_ecommerce.exception.ResourceNotFoundException;
import com.amazon.oa_ecommerce.model.Product;
import com.amazon.oa_ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
    public Product createProduct(Product product) {
        System.out.println("Creating product: " + product);
        Product saved = productRepository.save(product);
        System.out.println("Saved product: " + saved);
        return saved;
}
    
    public Product updateProduct(Long id, Product updated) {
        System.out.println("Updating product id: " + id);
        System.out.println("New data: " + updated);
        
        Product existing = getProductById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        
        Product saved = productRepository.save(existing); // ✅ save fix
        System.out.println("Updated product: " + saved);
        return saved;
}

    public void deleteProduct(Long id) {
        System.out.println("Deleting product id: " + id);
        getProductById(id);
        productRepository.deleteById(id);
        System.out.println("Deleted successfully");
    }
}