package com.gymregistration.GymRegistration.repository;

import com.gymregistration.GymRegistration.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository <Client, Long> {
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
}
