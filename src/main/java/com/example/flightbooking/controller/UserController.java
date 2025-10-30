package com.example.flightbooking.controller;

import com.example.flightbooking.dto.AuthDtos;
import com.example.flightbooking.dto.UserDtos;
import com.example.flightbooking.model.User;
import com.example.flightbooking.security.JwtTokenProvider;
import com.example.flightbooking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper mapper;

    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider, ModelMapper mapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mapper = mapper;
    }

    @Operation(summary = "Register new users")
    @PostMapping("/users")
    public ResponseEntity<UserDtos.UserResponse> register(@Valid @RequestBody UserDtos.RegisterRequest req) {
        return ResponseEntity.ok(userService.register(req));
    }

    @Operation(summary = "Login (JWT authentication)")
    @PostMapping("/login")
    public ResponseEntity<AuthDtos.AuthResponse> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email, req.password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.findByEmail(req.email);
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(new AuthDtos.AuthResponse(token));
    }

    @Operation(summary = "Get profile by id")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDtos.UserResponse> getProfile(@PathVariable Long id) {
        User u = userService.findById(id);
        UserDtos.UserResponse res = mapper.map(u, UserDtos.UserResponse.class);
        res.role = u.getRole().name();
        return ResponseEntity.ok(res);
    }
}
