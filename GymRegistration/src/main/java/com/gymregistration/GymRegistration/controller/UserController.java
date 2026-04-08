package com.gymregistration.GymRegistration.controller;

import com.gymregistration.GymRegistration.dto.UpdateUserDTO;
import com.gymregistration.GymRegistration.dto.UserDTO;
import com.gymregistration.GymRegistration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    // El usuario ve su propia info
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyInfo(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(userService.getMyInfo(email));
    }

    // El usuario actualiza sus propios datos
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateMyInfo(
            @AuthenticationPrincipal String email,
            @RequestBody UpdateUserDTO dto) {
        return ResponseEntity.ok(userService.updateMyInfo(email, dto));
    }

    // El usuario cambia su contraseña
    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.changePassword(email, oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

    // El usuario elimina su propia cuenta
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal String email) {
        userService.deleteMyAccount(email);
        return ResponseEntity.noContent().build();
    }
}