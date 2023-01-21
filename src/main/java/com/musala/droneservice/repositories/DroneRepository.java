package com.musala.droneservice.repositories;

import com.musala.droneservice.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
