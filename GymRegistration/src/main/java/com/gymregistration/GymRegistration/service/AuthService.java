package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.LoginRequestDTO;
import com.gymregistration.GymRegistration.dto.LoginResponseDTO;
import com.gymregistration.GymRegistration.model.User;
import com.gymregistration.GymRegistration.repository.IUserRepository;
import com.gymregistration.GymRegistration.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        // 1. Buscar el usuario por email
        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // 2. Verificar que el password sea correcto
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        // 3. Generar el token con el email y el rol
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        // 4. Devolver el token y el rol
        return LoginResponseDTO.builder()
                .token(token)
                .role(user.getRole().name())
                .build();
    }
}