package com.gymregistration.GymRegistration.controller;

import com.gymregistration.GymRegistration.dto.LoginRequestDTO;
import com.gymregistration.GymRegistration.dto.LoginResponseDTO;
import com.gymregistration.GymRegistration.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // saca el "Bearer "
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }
}