package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.UpdateUserDTO;
import com.gymregistration.GymRegistration.dto.UserDTO;

public interface IUserService {


    public UserDTO getMyInfo(String email);

    public UserDTO updateMyInfo(String email, UpdateUserDTO dto);

    public void changePassword(String email, String oldPassword, String newPassword);

    public void deleteMyAccount(String email);
}
