package com.gymregistration.GymRegistration.repository;

import com.gymregistration.GymRegistration.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMembershipRepository extends JpaRepository <Membership, Long> {
}
