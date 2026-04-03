package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.UserDTO;

public interface IUserService {

    public UserDTO getUserByDni(String dni);

    public UserDTO getUserByEmail(String email);

    public UserDTO updateUser(Long id, UserDTO userDTO);

    public void deleteUser(Long id);
}
