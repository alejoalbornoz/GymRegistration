package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{
    @Override
    public UserDTO getUserByDni(String dni) {
        return null;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
