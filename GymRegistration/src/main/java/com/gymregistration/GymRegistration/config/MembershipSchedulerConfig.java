package com.gymregistration.GymRegistration.config;

import com.gymregistration.GymRegistration.model.Membership;
import com.gymregistration.GymRegistration.repository.IMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MembershipSchedulerConfig {

    @Autowired
    private IMembershipRepository membershipRepo;

    // Se ejecuta todos los días a medianoche
    @Scheduled(cron = "0 0 0 * * *")
    public void checkExpiredMemberships() {

        List<Membership> activeMemberships = membershipRepo
                .findByStatus(Membership.MembershipStatus.ACTIVE);

        LocalDate today = LocalDate.now();

        activeMemberships.forEach(membership -> {
            if (membership.getExpirationDate().isBefore(today)) {
                membership.setStatus(Membership.MembershipStatus.EXPIRED);
            }
        });

        membershipRepo.saveAll(activeMemberships);
    }
}