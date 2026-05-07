package com.ws101.varela.unay.EcommerceApi.controller;

import com.ws101.varela.unay.EcommerceApi.dto.RegisterRequest;
import com.ws101.varela.unay.EcommerceApi.model.User;
import com.ws101.varela.unay.EcommerceApi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        
        // 1. Validate kung existing na username
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        // 2. Create new user + hash password
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash dito
        user.setRoles(request.getRole());

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
