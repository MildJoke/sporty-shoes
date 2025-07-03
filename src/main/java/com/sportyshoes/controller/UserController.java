package com.sportyshoes.controller;

import com.sportyshoes.model.User;
import com.sportyshoes.service.PurchaseService;
import com.sportyshoes.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.sportyshoes.repository.ProductRepository;
import com.sportyshoes.repository.PurchaseRepository;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseService purchaseService;


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "user-register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user, Model model) {
        User registered = userService.registerUser(user);
        if (registered == null) {
            model.addAttribute("error", "Email already exists.");
            return "user-register";
        }
        model.addAttribute("success", "Registration successful! Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "user-login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        User user = userService.loginUser(email, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/user/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password.");
            return "user-login";
        }
    }


    @GetMapping("/user/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "user-products";
    }

    @Autowired
    private PurchaseRepository purchaseRepository;


    @PostMapping("/user/order")
    public String orderProduct(@RequestParam Long productId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        purchaseService.recordPurchase(user, productId);
        return "redirect:/user/orders";
    }

    @GetMapping("/user/orders")
    public String viewOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("orders", purchaseRepository.findByUser(user));
        return "user-orders";
    }


    @PostMapping("/user/buy")
    public String buyProduct(@RequestParam Long productId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        purchaseService.recordPurchase(user, productId);
        return "redirect:/user/orders";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("totalOrders", purchaseService.getTotalOrdersByUser(user));
        model.addAttribute("lastOrder", purchaseService.getLastOrderForUser(user));

        return "user-dashboard";
    }

    //     @GetMapping("/user/dashboard")
    // public String userDashboard(HttpSession session, Model model) {
    //     User user = (User) session.getAttribute("loggedInUser");
    //     if (user == null) return "redirect:/login";

    //     model.addAttribute("user", user);
    //     return "user-dashboard";
    // }



    @PostMapping("/user/update-profile")
    public String updateProfile(@ModelAttribute("user") User updatedUser, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser == null) return "redirect:/login";

        sessionUser.setName(updatedUser.getName());
        sessionUser.setPassword(updatedUser.getPassword());
        userService.saveUser(sessionUser); // assumes .saveUser() handles save
        session.setAttribute("loggedInUser", sessionUser);
        return "redirect:/user/dashboard";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("/user/edit")

    public String editProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "user-profile"; // template filename
    }

}
