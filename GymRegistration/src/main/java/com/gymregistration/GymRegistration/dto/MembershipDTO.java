package com.gymregistration.GymRegistration.dto;

import com.gymregistration.GymRegistration.model.Membership;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipDTO {
    private Long id;
    private Membership.MembershipType type;
    private Membership.MembershipStatus status;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private Boolean isPaid;
}
