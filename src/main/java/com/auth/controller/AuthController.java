package com.auth.controller;

import com.auth.model.User;
import com.auth.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // ─────────────────────────────────────────
    // Home → redirect to login
    // ─────────────────────────────────────────
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // ─────────────────────────────────────────
    // LOGIN - GET (show form)
    // ─────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(required = false) String registered,
                            @RequestParam(required = false) String error) {
        if (registered != null) {
            model.addAttribute("success", "Account created! Please login.");
        }
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        return "login";
    }

    // ─────────────────────────────────────────
    // LOGIN - POST (handle form submit)
    // ─────────────────────────────────────────
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        boolean valid = userService.loginUser(username, password);

        if (valid) {
            // Store username in session
            session.setAttribute("loggedInUser", username);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
    }

    // ─────────────────────────────────────────
    // SIGNUP - GET (show form)
    // ─────────────────────────────────────────
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // ─────────────────────────────────────────
    // SIGNUP - POST (handle form submit)
    // ─────────────────────────────────────────
    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute User user, Model model) {
        // Basic validation
        if (user.getPassword().length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters!");
            return "signup";
        }

        String result = userService.registerUser(user);

        if (result.equals("success")) {
            return "redirect:/login?registered=true";
        } else {
            model.addAttribute("error", result);
            return "signup";
        }
    }

    // ─────────────────────────────────────────
    // DASHBOARD (after successful login)
    // ─────────────────────────────────────────
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        // If not logged in, redirect to login
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", loggedInUser);
        return "dashboard";
    }

    // ─────────────────────────────────────────
    // LOGOUT
    // ─────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
