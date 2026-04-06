package com.gymregistration.GymRegistration.service;

import com.gymregistration.GymRegistration.dto.ClientDTO;
import com.gymregistration.GymRegistration.model.Client;


import java.util.List;


public interface IClientService {

    public List <ClientDTO> getClients();

    public ClientDTO createClient (ClientDTO dtoCli);

    public void deleteClient(Long id);

    public ClientDTO getClientById (Long id);

    public ClientDTO getClientByDni (String dni);

    public ClientDTO editClient (Long id, ClientDTO dtoCli);

}
