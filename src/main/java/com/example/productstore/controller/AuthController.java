package com.example.productstore.controller;


import com.example.productstore.dto.AuthRequest;
import com.example.productstore.dto.AuthResponse;
import com.example.productstore.dto.RegisterRequest;
import com.example.productstore.enums.Role;
import com.example.productstore.repository.UserRepository;
import com.example.productstore.security.JwtUtil;
import com.example.productstore.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.productstore.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );


        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());


        userDetails.getAuthorities().forEach(authority ->
                System.out.println("ROLE: " + authority.getAuthority())
        );

        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();

        if (request.getRole().equalsIgnoreCase("ADMIN")) {
            roles.add(Role.ADMIN);
        } else {
            roles.add(Role.USER);
        }
        user.setRoles(roles);
        System.out.println("Registering user with roles: " + user.getRoles());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}

