package com.backend.controller;

import com.backend.componet.ClientComponent;
import com.backend.model.Clients;
import com.backend.model.Directions;
import com.backend.service.ClientService;
import com.backend.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/directions")
public class DirectionController {

    @Autowired
    private ClientComponent clientComponent;

    @Autowired
    private ClientService clientService;

    private DirectionService directionService;

    public DirectionController(DirectionService directionService){

        super();

        this.directionService = directionService;
    }


    //Save Directions
    @PostMapping
    public String InsertDirection(@RequestBody Clients client, @RequestParam String address) throws Exception {

        return Optional.ofNullable(clientService.GetClient(client.getId()))
                .map(c -> {
                    boolean saved = false;
                    try {
                        saved = directionService.SaveDirection(new Directions(address, client));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return saved ? clientComponent.SuccessRegister : clientComponent.NotFound.replace("0", "direccion");
                })
                .orElse(clientComponent.NotFound);

    }

    //Get Directions by client
    @GetMapping("/check{id}")
    public List<Directions> ExtractDirectionsByClientId(@RequestParam long id) throws Exception{

        List<Directions> EmptyList = Collections.singletonList(new Directions("", new Clients(0, clientComponent.NotFound, "")));

        List<Directions> Directions = Optional.ofNullable(clientService.GetClient(id))
                .map(c -> {
                    try {
                        return directionService.GetAllDirectionsByClient(id);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(EmptyList);

        return Directions;

    }

    // Update Directions
    @PutMapping("/refresh/{id}")
    public String RefreshDirections(@PathVariable("id") long id, @RequestBody Directions directions) throws Exception {

        return directionService.UpdateDirections(id, directions) ? clientComponent.Updated.replace("0","Direccion") .replace("1","Actualizada"): clientComponent.NotUpdated.replace("0","Actualizar").replace("1","Direccion");
    }

    // Delete Directions
    @DeleteMapping("/remove/{id}")
    public String RemoveDirections(@PathVariable("id") long id) throws Exception {

        return directionService.DeleteDirections(id) ? clientComponent.Updated.replace("0","Direccion").replace("1","Eliminada") : clientComponent.NotUpdated.replace("0","Eliminar").replace("1","Direccion");
    }


}
