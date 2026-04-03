package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.MembershipDTO;
import com.gymregistration.GymRegistration.model.Membership;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService implements IMembershipService{
    @Override
    public MembershipDTO createMembership(Long clientId, Membership.MembershipType type) {
        return null;
    }

    @Override
    public MembershipDTO renewMembership(Long membershipId) {
        return null;
    }

    @Override
    public MembershipDTO getMembershipByClientId(Long clientId) {
        return null;
    }

    @Override
    public List<MembershipDTO> getAllMemberships() {
        return List.of();
    }

    @Override
    public void payMembership(Long membershipId) {

    }

    @Override
    public void cancelMembership(Long membershipId) {

    }
}
