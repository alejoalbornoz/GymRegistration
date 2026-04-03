package com.gymregistration.GymRegistration.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "client_id", nullable = false)
        private Client client;

        public enum MembershipType {
            THREE_TIMES_WEEK,
            UNLIMITED
        }

        public enum MembershipStatus {
            ACTIVE,
            EXPIRED
        }

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
         private MembershipType membershipType;

         @Enumerated(EnumType.STRING)
         @Column(nullable = false)
         private MembershipStatus status;

        @Column(nullable = false)
        private LocalDate startDate;

         @Column(nullable = false)
        private LocalDate expirationDate;

         @Column(nullable = false)
        private boolean isPaid;


}
