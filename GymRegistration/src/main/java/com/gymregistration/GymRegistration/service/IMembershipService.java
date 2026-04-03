package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.MembershipDTO;
import com.gymregistration.GymRegistration.model.Membership;

import java.util.List;

public interface IMembershipService {

    MembershipDTO createMembership(Long clientId, Membership.MembershipType type);

    MembershipDTO renewMembership(Long membershipId);

    MembershipDTO getMembershipByClientId(Long clientId);

    List<MembershipDTO> getAllMemberships();

    void payMembership(Long membershipId);

    void cancelMembership(Long membershipId);
}
