package com.example.animerating.controllers;

import com.example.animerating.dtos.RegisterDTO;
import com.example.animerating.models.User;
import com.example.animerating.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "loginPage";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/anime_rate/";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "registerPage";
    }

    @PostMapping("/register")
    public String register(RegisterDTO registerDTO, RedirectAttributes ra) {
        Optional<User> temp = userService.findByName(registerDTO.username());

        if(temp.isPresent()) {
            ra.addAttribute("userExists", true);
            return "redirect:/register";
        }
        else {
            User user = new User();
            user.setUsername(registerDTO.username());
            user.setPassword(passwordEncoder.encode(registerDTO.password()));
            userService.save(user);
        }

        return "redirect:/login";
    }
}
