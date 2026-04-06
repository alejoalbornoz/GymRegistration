package com.gymregistration.GymRegistration.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "client")
    private List<Membership> memberships = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "client")
    private User user;


}
