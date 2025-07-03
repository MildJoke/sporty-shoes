package com.sportyshoes.service;

import com.sportyshoes.model.Purchase;
import com.sportyshoes.model.User;
import com.sportyshoes.model.Product;
import com.sportyshoes.repository.PurchaseRepository;
import com.sportyshoes.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    public void recordPurchase(User user, Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (user != null && product != null) {
            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setProduct(product);
            purchase.setPurchaseDate(LocalDate.now());
            purchaseRepository.save(purchase);
        }
    }

    public List<Purchase> getUserPurchases(User user) {
        return purchaseRepository.findByUser(user);
    }

    public int getTotalOrdersByUser(User user) {
    return purchaseRepository.findByUser(user).size();
}

    public Purchase getLastOrderForUser(User user) {
        List<Purchase> purchases = purchaseRepository.findByUserOrderByPurchaseDateDesc(user);
        return purchases.isEmpty() ? null : purchases.get(0);
    }

}
