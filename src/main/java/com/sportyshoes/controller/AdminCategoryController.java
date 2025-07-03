package com.sportyshoes.controller;

import com.sportyshoes.model.Category;
import com.sportyshoes.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("newCategory", new Category());
        return "admin-categories";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("newCategory") Category category, Model model) {
        if (categoryRepository.findByName(category.getName()) != null) {
            model.addAttribute("error", "Category already exists.");
        } else {
            categoryRepository.save(category);
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }
}
