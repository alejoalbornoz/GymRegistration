package com.gymregistration.GymRegistration.dto;

import com.gymregistration.GymRegistration.model.Membership;
import com.gymregistration.GymRegistration.model.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private Membership.MembershipType membershipType;
    private Membership.MembershipStatus membershipStatus;
    private User.Role role;

}
