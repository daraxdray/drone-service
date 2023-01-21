package com.musala.droneservice.controllers;

import com.musala.droneservice.entities.ApiResponse;
import com.musala.droneservice.entities.Drone;
import com.musala.droneservice.enums.DroneModel;
import com.musala.droneservice.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DroneController {

    @Autowired
    DroneService droneService;

    @GetMapping("/welcome")
    public String getWelcom(){
        return "Welcome the Drone Works";
    }

    @PostMapping("/register-drone")
    public ApiResponse<Object> registerDrone(@RequestBody Drone drone) {

        //validation logic
        if(drone.getSerialNumber() == null || drone.getModel() == null || drone.getWeightLimit() <= 0 || drone.getBatteryCapacity() <= 0) {
            return new ApiResponse<Object>(String.format("Invalid input, expected fields includes: %s",drone.getEmptyProperty()),HttpStatus.BAD_REQUEST,"");
        }

        // logic to check if drone is already registered
        if (droneService.getDroneBySerialNumber(drone.getSerialNumber()) != null) {
            return new ApiResponse<Object>("Drone with serial number " + drone.getSerialNumber() + " already registered", HttpStatus.CONFLICT,"");
        }
        //check if weight limit is above maximum limit
        if(drone.getWeightLimit() > 500){
            return new ApiResponse<>("Weight limit exceeds the maximum allowed value",HttpStatus.BAD_REQUEST);
        }
        //save the drone to the database
        droneService.createDrone(drone);
        return new ApiResponse<>("Drone created", HttpStatus.CREATED, drone);
    }

}
