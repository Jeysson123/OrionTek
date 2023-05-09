package com.backend.service;

import com.backend.model.Clients;

import java.util.List;

public interface ClientService {

    List<Clients> GetClients() throws Exception;

    Boolean SaveClient(Clients clients) throws Exception;

    Clients GetClient(long id) throws Exception;

    Boolean UpdateClient(long id, Clients clients) throws Exception;

    Boolean DeleteClient(long id) throws Exception;


}
