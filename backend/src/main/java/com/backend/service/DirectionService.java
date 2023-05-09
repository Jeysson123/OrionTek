package com.backend.service;

import com.backend.model.Clients;
import com.backend.model.Directions;

import java.util.List;

public interface DirectionService {

    Boolean SaveDirection(Directions direction) throws Exception;

    List<Directions> GetAllDirectionsByClient(long id) throws Exception;

    Boolean UpdateDirections(long id, Directions directions) throws Exception;

    Boolean DeleteDirections(long id) throws Exception;
}
