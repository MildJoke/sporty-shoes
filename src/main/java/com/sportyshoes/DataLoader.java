package com.sportyshoes;

import com.sportyshoes.model.*;
import com.sportyshoes.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("!test")
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
    public void run(String... args) {
        // ✅ Admin (check by username only)
        if (adminRepository.findByUsername("admin") == null) {

            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            adminRepository.save(admin);
            System.out.println("✅ Admin created");
        }

        // ✅ Users (check by email only)
        User user1 = userRepository.findByEmail("alice@example.com").orElse(null);
        if (user1 == null) {
            user1 = new User();
            user1.setName("Alice");
            user1.setEmail("alice@example.com");
            user1.setPassword("alicepass");
            userRepository.save(user1);
            System.out.println("✅ User Alice created");
        }

        User user2 = userRepository.findByEmail("bob@example.com").orElse(null);
        if (user2 == null) {
            user2 = new User();
            user2.setName("Bob");
            user2.setEmail("bob@example.com");
            user2.setPassword("bobpass");
            userRepository.save(user2);
            System.out.println("✅ User Bob created");
        }

        // ✅ Categories
        Category running = categoryRepository.findByName("Running");
        if (running == null) {
            running = new Category();
            running.setName("Running");
            categoryRepository.save(running);
            System.out.println("✅ Category Running created");
        }

        Category casual = categoryRepository.findByName("Casual");
        if (casual == null) {
            casual = new Category();
            casual.setName("Casual");
            categoryRepository.save(casual);
            System.out.println("✅ Category Casual created");
        }

        // ✅ Products
        Product shoe1 = productRepository.findByName("Nike Runner");
        if (shoe1 == null) {
            shoe1 = new Product();
            shoe1.setName("Nike Runner");
            shoe1.setPrice(4500.0);
            shoe1.setCategory(running);
            productRepository.save(shoe1);
            System.out.println("✅ Product Nike Runner created");
        }

        Product shoe2 = productRepository.findByName("Adidas Everyday");
        if (shoe2 == null) {
            shoe2 = new Product();
            shoe2.setName("Adidas Everyday");
            shoe2.setPrice(3000.0);
            shoe2.setCategory(casual);
            productRepository.save(shoe2);
            System.out.println("✅ Product Adidas Everyday created");
        }

        // ✅ Purchases
        if (purchaseRepository.count() == 0) {
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

            System.out.println("✅ Sample purchases created");
        }

        System.out.println("✅ Data loading complete.");
    }
}
