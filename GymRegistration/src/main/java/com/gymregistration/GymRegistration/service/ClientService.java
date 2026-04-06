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
    public ClientDTO createClient(ClientDTO dtoCli) {

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

        return Mapper.toDTO(client);
    }

    @Override
    public void deleteClient(Long id) {
        Client cli = clientRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found for delete"));

        if (cli.getUser() != null) {
            userRepo.delete(cli.getUser());
        }

        clientRepo.deleteById(id);
    }

    @Override
    public ClientDTO editClient(Long id, ClientDTO dtoCli) {
        Client cli = clientRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        cli.setFirstName(dtoCli.getFirstName());
        cli.setLastName(dtoCli.getLastName());
        cli.setDni(dtoCli.getDni());
        cli.setEmail(dtoCli.getEmail());
        cli.setBirthDate(dtoCli.getBirthDate());
        clientRepo.save(cli);


        User user = cli.getUser();
        if (user != null) {
            user.setEmail(dtoCli.getEmail());
            userRepo.save(user);
        }

        return Mapper.toDTO(cli);
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Client cli = clientRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        return Mapper.toDTO(cli);
    }

    @Override
    public ClientDTO getClientByDni(String dni) {
        Client cli = (Client) clientRepo.findByDni(dni)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        return Mapper.toDTO(cli);
    }


}
