package com.gymregistration.GymRegistration.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
}
