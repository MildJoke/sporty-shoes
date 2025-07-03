package com.sportyshoes.repository;

import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Exact name match
    Product findByName(String name);

    // Search by partial name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // Find by category
    List<Product> findByCategory(Category category);

    // Optional: Combine name and category filter
    List<Product> findByNameContainingIgnoreCaseAndCategory(String name, Category category);
}
