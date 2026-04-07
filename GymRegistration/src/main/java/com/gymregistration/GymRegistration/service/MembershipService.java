package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.MembershipDTO;
import com.gymregistration.GymRegistration.exception.NotFoundException;
import com.gymregistration.GymRegistration.mapper.Mapper;
import com.gymregistration.GymRegistration.model.Client;
import com.gymregistration.GymRegistration.model.Membership;
import com.gymregistration.GymRegistration.repository.IClientRepository;
import com.gymregistration.GymRegistration.repository.IMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembershipService implements IMembershipService {

    @Autowired
    private IMembershipRepository membershipRepo;

    @Autowired
    private IClientRepository clientRepo;

    @Override
    public MembershipDTO createMembership(Long clientId, Membership.MembershipType type) {

        // 1. Verificar que el cliente existe
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        // 2. Verificar que no tenga una membresía activa ya
        boolean hasActiveMembership = membershipRepo
                .findByClientIdAndStatus(clientId, Membership.MembershipStatus.ACTIVE)
                .isPresent();

        if (hasActiveMembership) {
            throw new RuntimeException("Client already has an active membership");
        }

        // 3. Crear la membresía con vencimiento automático de 1 mes
        Membership membership = new Membership();
        membership.setClient(client);
        membership.setMembershipType(type);
        membership.setStatus(Membership.MembershipStatus.ACTIVE);
        membership.setStartDate(LocalDate.now());
        membership.setExpirationDate(LocalDate.now().plusMonths(1));
        membership.setPaid(false);

        return Mapper.toDTO(membershipRepo.save(membership));
    }

    @Override
    public MembershipDTO renewMembership(Long membershipId) {

        // 1. Buscar la membresía
        Membership membership = membershipRepo.findById(membershipId)
                .orElseThrow(() -> new NotFoundException("Membership not found"));

        // 2. Verificar que esté vencida para poder renovarla
        if (membership.getStatus() == Membership.MembershipStatus.ACTIVE) {
            throw new RuntimeException("Membership is still active, cannot renew yet");
        }

        // 3. Renovar por un mes más desde hoy
        membership.setStatus(Membership.MembershipStatus.ACTIVE);
        membership.setStartDate(LocalDate.now());
        membership.setExpirationDate(LocalDate.now().plusMonths(1));
        membership.setPaid(false);

        return Mapper.toDTO(membershipRepo.save(membership));
    }

    @Override
    public MembershipDTO getMembershipByClientId(Long clientId) {

        Membership membership = membershipRepo
                .findByClientIdAndStatus(clientId, Membership.MembershipStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("No active membership found for this client"));

        return Mapper.toDTO(membership);
    }

    @Override
    public List<MembershipDTO> getAllMemberships() {
        return membershipRepo.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public void payMembership(Long membershipId) {

        Membership membership = membershipRepo.findById(membershipId)
                .orElseThrow(() -> new NotFoundException("Membership not found"));

        membership.setPaid(true);
        membershipRepo.save(membership);
    }

    @Override
    public void cancelMembership(Long membershipId) {

        Membership membership = membershipRepo.findById(membershipId)
                .orElseThrow(() -> new NotFoundException("Membership not found"));

        membership.setStatus(Membership.MembershipStatus.EXPIRED);
        membershipRepo.save(membership);
    }
}