package com.musala.droneservice.services;
import com.musala.droneservice.models.Drone;
import com.musala.droneservice.utils.DroneState;
import com.musala.droneservice.repositories.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Configuration
@EnableScheduling
@Service
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;

    public Drone createDrone(Drone drone){
        return droneRepository.save(drone);
    }

    //creates multiple drones
    public List<Drone> createAllDrones(List<Drone> drones){
        return droneRepository.saveAll(drones);
    }

    //returns a drone by its given ID
    public Drone getDroneById(Long id){
        return droneRepository.findById(id).orElse(null) ;
    }

    //returns a drone by its given ID
    public boolean droneExist(Long id){
        return droneRepository.existsById(id);
    }

    //returns a drone by its given State
    public List<Drone> getDronesByState(DroneState state){
        return droneRepository.findByState(DroneState.IDLE);
    }


//Returns a drone that is IDLE and battery capacity is above 25%
    public Drone getBestAvailableDrone(){
        List<Drone> availableDrones = droneRepository.findByStateAndAbleBattery(DroneState.IDLE);
        if(availableDrones.size() > 0){
        return availableDrones.get(0);
        }
        return null;
    }



    public Drone getDroneBySerialNumber(String sn){
        Drone drone = new Drone();
        return droneRepository.findBySerialNumber(sn);

    }

    public List<Drone> getDrones(){
        return droneRepository.findAll();
    }

    public Map<String,String> getDroneBattery(Long droneId){
        Map<String,String> result = new HashMap<>();
        Drone droneExist = getDroneById(droneId);
        if(droneExist != null) {

            String message = "";
            int batteryLevel = droneExist.getBatteryCapacity();

            if (batteryLevel < 25) {
                message = String.format("%d%s: battery is critically low. Please charge as soon as possible", batteryLevel,'%');;
            }
            else if (batteryLevel > 25 && batteryLevel < 50) {
                message = String.format("%d%s:  battery is running low. It's recommended to charge", batteryLevel,'%');
            }
            else if (batteryLevel > 50 && batteryLevel < 80) {
                message = String.format("%d%s battery is halfway. Consider charging your device before it runs out of power.", batteryLevel,'%');
            }
            else{
                message = String.format("%d%s battery is fully charged. No need to charge it for now.", batteryLevel,'%');
            }
            result.put("message",message);
            result.put("battery_percentage", ""+ droneExist.getBatteryCapacity()+ "%");
            return result;
        }
        return  result;
    }

    public Drone updateDrone(Drone drone){
        System.out.println(drone.getModel());
            droneRepository.save(drone);
        return drone;
    }

    public String deleteDrone(Long id){
        droneRepository.deleteById(id);
        return "Drone deleted";
    }

    @Scheduled(fixedRate = 20000)
    public void scheduleFixedRateTask() {
        List<Drone> drones = getDrones();
        for(Drone drone: drones){
            System.out.println(getDroneBattery(drone.getId()));
        }
    }

}
