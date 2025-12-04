package com.example.authorization_server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {

    @PostMapping("/logout")
    public String logout(Authentication authentication) {
        if (authentication != null) {
            // Perform any necessary logout actions here
        }
        return "redirect:/";  // Redirect to home page after logout
    }
}
