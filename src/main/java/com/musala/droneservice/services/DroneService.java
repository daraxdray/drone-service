package com.musala.droneservice.services;
import com.musala.droneservice.entities.Drone;
import com.musala.droneservice.enums.DroneState;
import com.musala.droneservice.repositories.DroneRepository;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;

    public Drone createDrone(Drone drone){
        return droneRepository.save(drone);
    }

    public List<Drone> createAllDrones(List<Drone> drones){
        return droneRepository.saveAll(drones);
    }

    public Drone getDroneById(Long id){
        return droneRepository.findById(id).orElse(null) ;
    }

    public Drone getIdleDrone(){
        Drone idleDrone = new Drone();
        idleDrone.setState(DroneState.IDLE);
        return droneRepository.findOne(idleDrone.getExample()).orElse(null);
    }

    public Drone getDroneBySerialNumber(String sn){
        Drone drone = new Drone();
        drone.setSerialNumber(sn);
        return droneRepository.findOne(drone.getExample()).orElse(null);

    }

    public List<Drone> getDrones(){
        return droneRepository.findAll();
    }

    public Drone updateDrone(Drone drone){
        Optional<Drone> foundDrone = droneRepository.findById(drone.getId());
        if(foundDrone.isPresent()){
            Drone oldDrone = foundDrone.get();
            oldDrone.setBatteryCapacity(20);
            droneRepository.save(oldDrone);
        }
        return foundDrone.orElse(null);
    }
    public String deleteDrone(Long id){
        droneRepository.deleteById(id);
        return "Drone deleted";
    }

}
