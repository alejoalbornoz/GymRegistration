package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.UpdateUserDTO;
import com.gymregistration.GymRegistration.dto.UserDTO;
import com.gymregistration.GymRegistration.exception.NotFoundException;
import com.gymregistration.GymRegistration.mapper.Mapper;
import com.gymregistration.GymRegistration.model.Client;
import com.gymregistration.GymRegistration.model.User;
import com.gymregistration.GymRegistration.repository.IClientRepository;
import com.gymregistration.GymRegistration.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepo;
    @Autowired
    private IClientRepository clientRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getMyInfo(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return Mapper.toDTO(user);
    }

    @Override
    public UserDTO updateMyInfo(String email, UpdateUserDTO dto) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Client client = user.getClient();
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setBirthDate(dto.getBirthDate());
        clientRepo.save(client);

        // Si cambió el email actualizamos el User también
        user.setEmail(dto.getEmail());
        userRepo.save(user);

        return Mapper.toDTO(user);
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Verificamos que el password viejo sea correcto
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public void deleteMyAccount(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepo.delete(user);
    }
}
