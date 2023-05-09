package com.backend.service.impl;


import com.backend.model.Clients;
import com.backend.repository.ClientRepository;
import com.backend.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){

        super();

        this.clientRepository = clientRepository;
    }

    @Override
    public List<Clients> GetClients() throws Exception {

        return clientRepository.findAll();
    }

    @Override
    public Boolean SaveClient(Clients clients) throws Exception {

        Clients savedClients = clientRepository.save(clients);

        return savedClients != null;
    }

    @Override
    public Clients GetClient(long id) throws Exception {

        Optional<Clients> clientsExisted = clientRepository.findById(id);

        return clientsExisted.isPresent() ? clientsExisted.get() : null;
    }

    @Override
    public Boolean UpdateClient(long id, Clients clients) throws Exception {

        Optional<Clients> clientsExisted = clientRepository.findById(id);

        if (clientsExisted.isPresent()) {

            clientsExisted.get().setName(clients.getName());

            clientsExisted.get().setEnterprise(clients.getEnterprise());

            clientRepository.save(clientsExisted.get());
        }


        return clientsExisted.isPresent() ? true : false;
    }

    @Override
    public Boolean DeleteClient(long id) throws Exception {

        Optional<Clients> clientOptional = clientRepository.findById(id);

        if (clientOptional.isPresent()) {

            clientRepository.deleteById(id);

            return !clientRepository.existsById(id);
        }

        return false;
    }
}
