package com.sportyshoes.repository;

import com.sportyshoes.model.Purchase;
import com.sportyshoes.model.Category;
import com.sportyshoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByDateBetween(LocalDate start, LocalDate end);
    List<Purchase> findByUser(User user);
}
