package com.sportyshoes.repository;

import com.sportyshoes.model.Purchase;
import com.sportyshoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // Custom dynamic filtering
    @Query("SELECT p FROM Purchase p WHERE " +
            "(:startDate IS NULL OR p.purchaseDate >= :startDate) AND " +
            "(:endDate IS NULL OR p.purchaseDate <= :endDate) AND " +
            "(:categoryId IS NULL OR p.product.category.id = :categoryId)")
    List<Purchase> findByFilters(@Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate,
                                 @Param("categoryId") Long categoryId);

    // Purchases made by user
    List<Purchase> findByUser(User user);

    // Sorted purchases for user
    List<Purchase> findByUserOrderByPurchaseDateDesc(User user);

    // Filter by date range and category (used for reports)
    List<Purchase> findByPurchaseDateBetweenAndProductCategoryId(LocalDate start, LocalDate end, Long categoryId);
}
