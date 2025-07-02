package com.sportyshoes.controller;
import com.sportyshoes.model.Purchase;
import com.sportyshoes.repository.CategoryRepository;
import com.sportyshoes.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/purchases")
public class AdminPurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String viewPurchaseReports(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long categoryId,
            Model model) {

        List<Purchase> purchases;

        if (startDate != null || endDate != null || categoryId != null) {
            purchases = purchaseRepository.findByFilters(startDate, endDate, categoryId);
        } else {
            purchases = purchaseRepository.findAll();
        }

        model.addAttribute("purchases", purchases);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin-purchases";
    }
}
