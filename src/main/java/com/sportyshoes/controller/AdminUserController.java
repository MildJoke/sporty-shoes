package com.sportyshoes.controller;

import com.sportyshoes.repository.UserRepository;
import com.sportyshoes.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listUsers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> users;
        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        } else {
            users = userRepository.findAll();
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "admin-users";
    }
}
