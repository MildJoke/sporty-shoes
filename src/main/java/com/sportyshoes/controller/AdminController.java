package com.sportyshoes.controller;

import com.sportyshoes.model.Admin;
import com.sportyshoes.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-login";
    }

    @PostMapping("/login")
        public String processLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model) {
            Admin admin = adminRepository.findByUsername(username);

            if (admin != null && admin.getPassword().equals(password)) {
                return "redirect:/admin/dashboard";
            }

            model.addAttribute("error", "Invalid credentials");
            model.addAttribute("admin", new Admin()); // repopulate the form
            return "admin-login";
    }
    @PostMapping("/logout")
    public String logout() {
        return "redirect:/admin/login";
    }



    @GetMapping("/dashboard")
    public String showDashboard() {
        return "admin-dashboard";
    }
    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "admin-change-password"; 
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {

        Admin admin = adminRepository.findByUsername("admin"); // Assuming only 1 admin

        if (!admin.getPassword().equals(oldPassword)) {
            model.addAttribute("error", "Old password is incorrect.");
            return "admin-change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match.");
            return "admin-change-password";
        }

        admin.setPassword(newPassword);
        adminRepository.save(admin);
        model.addAttribute("success", "Password changed successfully.");
        return "admin-change-password";
    }


}
