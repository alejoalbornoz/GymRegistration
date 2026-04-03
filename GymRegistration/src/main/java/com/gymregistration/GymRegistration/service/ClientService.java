package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.ClientDTO;
import com.gymregistration.GymRegistration.model.Client;
import com.gymregistration.GymRegistration.repository.IClientRepository;
import com.gymregistration.GymRegistration.repository.IMembershipRepository;
import com.gymregistration.GymRegistration.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public List<ClientDTO> getClients() {

        List <Client> allClients = clientRepo.findAll();
        List <ClientDTO> allClientsDto = new ArrayList<>();

        ClientDTO dto;
        for(Client c : allClients){
            dto = Mapper.toDTO(c);
            allClientsDto.add (dto);
        }

        return allClientsDto;
    }

    @Override
    public void createClient(ClientDTO dtoCli) {

    }

    @Override
    public void deleteClient(Long id) {

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
