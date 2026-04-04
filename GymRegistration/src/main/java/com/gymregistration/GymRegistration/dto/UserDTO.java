package com.gymregistration.GymRegistration.dto;

import com.gymregistration.GymRegistration.model.Membership;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
