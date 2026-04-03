package com.gymregistration.GymRegistration.dto;

import com.gymregistration.GymRegistration.model.Membership;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private Membership.MembershipType membershipType;
    private Membership.MembershipStatus membershipStatus;
    private LocalDate birthDate;
    private LocalDate expirationDate;
}
