package com.orientek.app.Uis;

import com.orientek.app.Models.Clients;

import java.util.List;

public interface ClientsServiceUI {

    List<Clients> GetClients() throws Exception;

    Boolean SaveClient(Clients clients) throws Exception;

    Clients GetClient(long id) throws Exception;

    Boolean UpdateClient(long id, Clients clients) throws Exception;

    Boolean DeleteClient(long id) throws Exception;
}
