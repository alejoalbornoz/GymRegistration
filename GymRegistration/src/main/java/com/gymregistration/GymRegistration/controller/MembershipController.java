package com.gymregistration.GymRegistration.controller;

import com.gymregistration.GymRegistration.dto.MembershipDTO;
import com.gymregistration.GymRegistration.model.Membership;
import com.gymregistration.GymRegistration.service.IMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    @Autowired
    private IMembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<MembershipDTO>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<MembershipDTO> getMembershipByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(membershipService.getMembershipByClientId(clientId));
    }

    @PostMapping("/client/{clientId}")
    public ResponseEntity<MembershipDTO> createMembership(
            @PathVariable Long clientId,
            @RequestParam Membership.MembershipType type) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membershipService.createMembership(clientId, type));
    }

    @PutMapping("/{membershipId}/renew")
    public ResponseEntity<MembershipDTO> renewMembership(@PathVariable Long membershipId) {
        return ResponseEntity.ok(membershipService.renewMembership(membershipId));
    }

    @PutMapping("/{membershipId}/pay")
    public ResponseEntity<Void> payMembership(@PathVariable Long membershipId) {
        membershipService.payMembership(membershipId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{membershipId}/cancel")
    public ResponseEntity<Void> cancelMembership(@PathVariable Long membershipId) {
        membershipService.cancelMembership(membershipId);
        return ResponseEntity.noContent().build();
    }
}