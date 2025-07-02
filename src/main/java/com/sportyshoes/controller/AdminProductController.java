package com.sportyshoes.controller;

import com.sportyshoes.model.Product;
import com.sportyshoes.repository.CategoryRepository;
import com.sportyshoes.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // ✅ List all products
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin-products";
    }

    // ✅ Show Add Product form
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin-add-product";
    }

    // ✅ Handle form submit
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/products";
    }


        // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin-edit-product";
    }

    // Handle edit
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());

        productRepository.save(existingProduct);
        return "redirect:/admin/products";
    }

    // Handle delete
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

}
