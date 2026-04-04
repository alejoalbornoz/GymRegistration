package com.gymregistration.GymRegistration.mapper;

import com.gymregistration.GymRegistration.dto.ClientDTO;
import com.gymregistration.GymRegistration.dto.MembershipDTO;
import com.gymregistration.GymRegistration.dto.UserDTO;
import com.gymregistration.GymRegistration.model.Client;
import com.gymregistration.GymRegistration.model.Membership;
import com.gymregistration.GymRegistration.model.User;

public class Mapper {

    // Client → ClientDTO
    public static ClientDTO toDTO(Client c) {
        if (c == null) return null;

        Membership activeMembership = c.getMemberships()
                .stream()
                .filter(m -> m.getStatus() == Membership.MembershipStatus.ACTIVE)
                .findFirst()
                .orElse(null);

        return ClientDTO.builder()
                .id(c.getId())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .dni(c.getDni())
                .email(c.getEmail())
                .birthDate(c.getBirthDate())
                .createdAt(c.getCreatedAt())
                .membershipType(activeMembership != null ? activeMembership.getMembershipType() : null)
                .membershipStatus(activeMembership != null ? activeMembership.getStatus() : null)
                .role(c.getUser() != null ? c.getUser().getRole() : null)
                .build();
    }

    // User → UserDTO
    public static UserDTO toDTO(User u) {
        if (u == null) return null;

        Client client = u.getClient();

        Membership activeMembership = (client != null) ? client.getMemberships()
                .stream()
                .filter(m -> m.getStatus() == Membership.MembershipStatus.ACTIVE)
                .findFirst()
                .orElse(null) : null;

        return UserDTO.builder()
                .firstName(client != null ? client.getFirstName() : null)
                .lastName(client != null ? client.getLastName() : null)
                .dni(client != null ? client.getDni() : null)
                .email(u.getEmail())
                .birthDate(client != null ? client.getBirthDate() : null)
                .membershipType(activeMembership != null ? activeMembership.getMembershipType() : null)
                .membershipStatus(activeMembership != null ? activeMembership.getStatus() : null)
                .expirationDate(activeMembership != null ? activeMembership.getExpirationDate() : null)
                .build();
    }

    // Membership → MembershipDTO
    public static MembershipDTO toDTO(Membership m) {
        if (m == null) return null;

        return MembershipDTO.builder()
                .id(m.getId())
                .type(m.getMembershipType())
                .status(m.getStatus())
                .startDate(m.getStartDate())
                .expirationDate(m.getExpirationDate())
                .isPaid(m.isPaid())
                .build();
    }
}