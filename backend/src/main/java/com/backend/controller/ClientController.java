package com.backend.controller;

import com.backend.componet.ClientComponent;
import com.backend.model.Clients;
import com.backend.model.Directions;
import com.backend.service.ClientService;
import com.backend.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientComponent clientComponent;

    private ClientService clientService;

    public ClientController(ClientService clientService){

        super();

        this.clientService = clientService;
    }

    //Get All Clients
    @GetMapping
    public List<Clients> GetAllClients() throws Exception {

        return this.clientService.GetClients();
    }

    //Save Clients
    @PostMapping
    public String InsertClient(@RequestBody Clients clients) throws Exception {

        return clientService.SaveClient(clients) ? clientComponent.SuccessRegister : clientComponent.ErrorRegister.replace("0","cliente");
    }

    //Get Especific Clients
    @GetMapping("/check/{id}")
    public Clients CheckClient(@PathVariable("id") long id) throws Exception{

        return clientService.GetClient(id)  != null ? clientService.GetClient(id) : new Clients(0, clientComponent.NotFound, "");

    }

    // Update Clients
    @PutMapping("/refresh/{id}")
    public String RefreshClient(@PathVariable("id") long id, @RequestBody Clients clients) throws Exception {

        return clientService.UpdateClient(id, clients) ? clientComponent.Updated.replace("0","Cliente").replace("1","Actualizado") : clientComponent.NotUpdated.replace("0","Actualizar").replace("1","Cliente");
    }

    // Delete Clients
    @DeleteMapping("/remove/{id}")
    public String RemoveClient(@PathVariable("id") long id) throws Exception {

        return clientService.DeleteClient(id) ? clientComponent.Updated.replace("0","Cliente").replace("1", "Eliminado") : clientComponent.NotUpdated.replace("0","Eliminar").replace("1","Eliminar");
    }
}
