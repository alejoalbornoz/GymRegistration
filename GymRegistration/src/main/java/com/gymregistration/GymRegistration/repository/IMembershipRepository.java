package com.gymregistration.GymRegistration.repository;

import com.gymregistration.GymRegistration.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMembershipRepository extends JpaRepository<Membership, Long> {

    // Busca todas las membresías de un cliente
    List<Membership> findByClientId(Long clientId);

    // Busca la membresía activa de un cliente
    Optional<Membership> findByClientIdAndStatus(Long clientId, Membership.MembershipStatus status);

    // Busca todas las membresías activas (para el scheduler de vencimiento)
    List<Membership> findByStatus(Membership.MembershipStatus status);
}