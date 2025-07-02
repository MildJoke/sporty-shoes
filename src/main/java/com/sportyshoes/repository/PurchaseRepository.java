package com.sportyshoes.repository;

import com.sportyshoes.model.Purchase;
import com.sportyshoes.model.Category;
import com.sportyshoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT p FROM Purchase p WHERE " +
            "(:startDate IS NULL OR p.date >= :startDate) AND " +
            "(:endDate IS NULL OR p.date <= :endDate) AND " +
            "(:categoryId IS NULL OR p.product.category.id = :categoryId)")
    List<Purchase> findByFilters(@Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate,
                                 @Param("categoryId") Long categoryId);
}
