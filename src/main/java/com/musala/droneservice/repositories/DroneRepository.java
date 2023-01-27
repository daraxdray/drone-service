package com.musala.droneservice.repositories;

import com.musala.droneservice.models.Drone;
import com.musala.droneservice.utils.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByState(DroneState state);

    //It ensures drone battery is higher than 25% required to load a drone
    @Query(value = "SELECT s FROM drone d WHERE d.state = 'IDLE' AND d.battery_capacity >= 25", nativeQuery = true)
    List<Drone> findByStateAndAbleBattery(DroneState state);

    //find the serial number
    Drone findBySerialNumber(String serialNumber);
}
