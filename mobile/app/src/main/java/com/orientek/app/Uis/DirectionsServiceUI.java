package com.orientek.app.Uis;

import com.orientek.app.Models.Clients;
import com.orientek.app.Models.Directions;

import java.util.List;

public interface DirectionsServiceUI {

    Boolean SaveDirection(Directions direction) throws Exception;

    List<Directions> GetAllDirectionsByClient(long id) throws Exception;

    Boolean UpdateDirections(long id, Directions directions) throws Exception;

    Boolean DeleteDirections(long id) throws Exception;
}
