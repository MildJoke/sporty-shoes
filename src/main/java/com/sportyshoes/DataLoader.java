package com.sportyshoes;

import com.sportyshoes.model.*;
import com.sportyshoes.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    public DataLoader(AdminRepository adminRepository,
                      UserRepository userRepository,
                      CategoryRepository categoryRepository,
                      ProductRepository productRepository,
                      PurchaseRepository purchaseRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Admin
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        adminRepository.save(admin);

        // Users
        User user1 = new User();
        user1.setName("Alice");
        user1.setEmail("alice@example.com");
        user1.setPassword("alicepass");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Bob");
        user2.setEmail("bob@example.com");
        user2.setPassword("bobpass");
        userRepository.save(user2);

        // Categories
        Category running = new Category();
        running.setName("Running");
        categoryRepository.save(running);

        Category casual = new Category();
        casual.setName("Casual");
        categoryRepository.save(casual);

        // Products
        Product shoe1 = new Product();
        shoe1.setName("Nike Runner");
        shoe1.setPrice(4500.0);
        shoe1.setCategory(running);
        productRepository.save(shoe1);

        Product shoe2 = new Product();
        shoe2.setName("Adidas Everyday");
        shoe2.setPrice(3000.0);
        shoe2.setCategory(casual);
        productRepository.save(shoe2);

        // Purchases
        Purchase purchase1 = new Purchase();
        purchase1.setDate(LocalDate.now());
        purchase1.setUser(user1);
        purchase1.setProduct(shoe1);
        purchaseRepository.save(purchase1);

        Purchase purchase2 = new Purchase();
        purchase2.setDate(LocalDate.now());
        purchase2.setUser(user2);
        purchase2.setProduct(shoe2);
        purchaseRepository.save(purchase2);

        System.out.println("✅ Sample data loaded.");
    }
}
