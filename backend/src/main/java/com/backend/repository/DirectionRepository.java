package com.backend.repository;

import com.backend.model.Directions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Directions, Long> {
}
