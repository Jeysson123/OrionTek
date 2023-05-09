package com.backend.service.impl;

import com.backend.model.Clients;
import com.backend.model.Directions;
import com.backend.repository.DirectionRepository;
import com.backend.service.ClientService;
import com.backend.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DirectionServiceImpl implements DirectionService {

    private DirectionRepository directionRepository;

    @Autowired
    private ClientService clientService;

    public DirectionServiceImpl(DirectionRepository directionRepository){

        super();

        this.directionRepository = directionRepository;
    }

    @Override
    public Boolean SaveDirection(Directions direction) throws Exception {

        return Optional.ofNullable(directionRepository.save(direction)).isPresent();

    }

    @Override
    public List<Directions> GetAllDirectionsByClient(long id) throws Exception {

        return Optional.ofNullable(clientService.GetClient(id))
                .map(Clients::getListDirections)
                .orElse(Collections.emptyList());

    }

    @Override
    public Boolean UpdateDirections(long id, Directions directions) throws Exception {

        Optional<Directions> directionsExisted = directionRepository.findById(id);

        if (directionsExisted.isPresent()) {

            directionsExisted.get().setAddress(directions.getAddress());

            directionRepository.save(directionsExisted.get());
        }


        return directionsExisted.isPresent() ? true : false;
    }

    @Override
    public Boolean DeleteDirections(long id) throws Exception {

        Optional<Directions> directionsOptional = directionRepository.findById(id);

        if (directionsOptional.isPresent()) {

            directionRepository.deleteById(id);

            return !directionRepository.existsById(id);
        }

        return false;
    }
}
