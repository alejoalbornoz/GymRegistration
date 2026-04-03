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
public class MembershipDTO {
    private Long id;
    private Membership.MembershipType type;
    private Membership.MembershipStatus status;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private Boolean isPaid;
}
