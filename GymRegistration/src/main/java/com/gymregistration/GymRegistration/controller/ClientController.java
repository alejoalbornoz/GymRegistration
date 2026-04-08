package com.gymregistration.GymRegistration.controller;

import com.gymregistration.GymRegistration.dto.ClientDTO;
import com.gymregistration.GymRegistration.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClientDTO> getClientByDni(@PathVariable String dni) {
        return ResponseEntity.ok(clientService.getClientByDni(dni));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> editClient(@PathVariable Long id, @RequestBody ClientDTO dto) {
        return ResponseEntity.ok(clientService.editClient(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}