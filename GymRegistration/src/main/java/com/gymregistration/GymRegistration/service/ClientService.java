package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.ClientDTO;
import com.gymregistration.GymRegistration.exception.NotFoundException;
import com.gymregistration.GymRegistration.mapper.Mapper;
import com.gymregistration.GymRegistration.model.Client;
import com.gymregistration.GymRegistration.model.User;
import com.gymregistration.GymRegistration.repository.IClientRepository;
import com.gymregistration.GymRegistration.repository.IMembershipRepository;
import com.gymregistration.GymRegistration.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientService{

    @Autowired
    private IClientRepository clientRepo;
    @Autowired
    private IUserRepository userRepo;
    @Autowired
    private IMembershipRepository memberRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<ClientDTO> getClients() {
        return clientRepo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public void createClient(ClientDTO dtoCli) {

        if (clientRepo.existsByDni(dtoCli.getDni())) {
            throw new RuntimeException("Client with DNI " + dtoCli.getDni() + " already exists");
        }
        if (clientRepo.existsByEmail(dtoCli.getEmail())) {
            throw new RuntimeException("Client with email " + dtoCli.getEmail() + " already exists");
        }

        Client client = Client.builder()
                .firstName(dtoCli.getFirstName())
                .lastName(dtoCli.getLastName())
                .dni(dtoCli.getDni())
                .email(dtoCli.getEmail())
                .birthDate(dtoCli.getBirthDate())
                .build();
        clientRepo.save(client);

        User user = new User();
        user.setEmail(dtoCli.getEmail());
        user.setPassword(passwordEncoder.encode(dtoCli.getDni()));
        user.setRole(User.Role.CLIENT);
        user.setClient(client);
        userRepo.save(user);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepo.existsById(id)){
            throw new NotFoundException("Client no encontrado para eliminar");
        }
        clientRepo.deleteById(id);
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return null;
    }

    @Override
    public ClientDTO getClientByDni(String dni) {
        return null;
    }

    @Override
    public void editClient(ClientDTO dtoCli) {

    }
}
