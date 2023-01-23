package com.musala.droneservice.controllers;

import com.musala.droneservice.models.*;
import com.musala.droneservice.services.DeliveryService;
import com.musala.droneservice.services.DroneService;
import com.musala.droneservice.services.MedicationService;
import com.musala.droneservice.services.RecipientService;
import com.musala.droneservice.utils.DroneState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class DroneController {

    @Autowired
    DroneService droneService;
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    MedicationService medicationService;
    @Autowired
    RecipientService recipientService;

    @GetMapping("/welcome")
    public String getWelcom(){
        return "Welcome the Drone Service API";
    }


    /*
    * THE LIST OF API ARE REQUIRED ROUTES WHILE THE OTHERS ARE ADDITIONAL
    * ROUTES ADDED TO MAKE THE SYSTEM MORE EFFICIENT
    *
    *
    * FOR A PRETTIER AND FRIENDLY URL : We User
    * ds -> to represent drone-service;
    * */

    //registering a drone
    @PostMapping("/ds/create-drone" )
    public ApiResponse<Object> createDrone(@RequestBody Drone drone) {

        //validation logic
        if(drone.getSerialNumber() == null || drone.getModel() == null || drone.getWeightLimit() <= 0 || drone.getBatteryCapacity() <= 0) {
            return new ApiResponse<Object>(String.format("Invalid input, expected fields includes: %s",drone.isEmptyProperty()),HttpStatus.BAD_REQUEST,"");
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

    //loading a drone with medication items
    @PostMapping("/ds/load-drone-by-medication-items")
    public ApiResponse<Object> loadDroneByMedicationIds(@RequestBody List<Long> medicationsIds){
        //Get an idle drone for loading
        Drone idleDrone = droneService.getBestAvailableDrone();
        int totalWeightOfMedications = 0;
        int loadedDrones = 0;
        if(idleDrone == null){
            return new ApiResponse<>("There are currently no idle drones",HttpStatus.EXPECTATION_FAILED);
        }
        //Ensure the battery capacity is higher than 25%
        if(idleDrone.getBatteryCapacity() <= 25){
            return new ApiResponse<>("Drone battery capacity is quite low to do any task!",HttpStatus.EXPECTATION_FAILED);
        }

        // Logic that checks the weight of each medications before loading on the drone.
        // To prevent overloading drone.
        for(Long id : medicationsIds){
            //fetch the medication from db to get weight.
          Medication  fetchedMedication = medicationService.getMedicationById(id);

          if(fetchedMedication != null){
              totalWeightOfMedications += fetchedMedication.getWeight();
              if(totalWeightOfMedications < idleDrone.getWeightLimit()){
                  //gets the first recipient as default
                  idleDrone.setState(DroneState.LOADING);
                  Recipient recipient = recipientService.getRecipientById(1L);
                  deliveryService.loadDrone(new Delivery(idleDrone,fetchedMedication, LocalDateTime.now(),recipient));
                  //change drone status after loading
                  idleDrone.setState(DroneState.LOADED);
                  droneService.updateDrone(idleDrone);
                  loadedDrones++;

              }
          }

        }


        return new ApiResponse<>(String.format("%d of %d Drones loaded successfully",loadedDrones, medicationsIds.size()),HttpStatus.OK);
    }

    //checking loaded medication items for a given drone
    @GetMapping("/ds/get-medications/{drone_id}")
    public ApiResponse<Object> getDroneLoadedItems(@PathVariable("drone_id") Long droneId){

        Drone droneExist = droneService.getDroneById(droneId);
        if(droneExist != null) {
            return new ApiResponse<>("Pending Deliveries fetched",HttpStatus.OK,deliveryService.findMedicationByDroneAndDeliveryStatus(droneExist));
        }
        return new ApiResponse<>("Drone with specified ID not found",HttpStatus.NOT_FOUND);
    }

    //checking available drones for loading
    @GetMapping("ds/get-available-drones-for-loading")
    public ApiResponse<Object> getAvailableDronesForLoading(){
        return  new ApiResponse<>("Idle drones listed",HttpStatus.OK,droneService.getDronesByState(DroneState.IDLE));
    }

    //check drone battery level for a given drone
    @GetMapping("/ds/get-drone-battery-level/{drone_id}")
    public ApiResponse<Object> getBatteryLevelOfDrone(@PathVariable("drone_id") Long droneId){
        Map<String,String> res = droneService.getDroneBattery(droneId);
        if(res != null){
            return new ApiResponse<>(res.get("message"),HttpStatus.OK, res);
        }
        return new ApiResponse<>("Drone with specified ID not found",HttpStatus.NOT_FOUND);
    }

    /*
    * END OF FUNCTIONAL REQUIREMENTS
    *
    *
    * */


    /*
    OTHER ROUTES ARE LISTED BELOW
    * */

    //Fetch all drones;
    @GetMapping("/ds/get-drones")
    public ApiResponse<Object> getDrones(){
        return new ApiResponse<>(String.format("Drone loaded successfully"),HttpStatus.OK,droneService.getDrones());
    }

    @GetMapping("/ds/check-loaded-medications/{drone_id}")
    public ApiResponse<Object> getDronePendingDeliveryItems(@PathVariable("drone_id") Long droneId){

        Drone droneExist = droneService.getDroneById(droneId);
        if(droneExist != null) {
            return new ApiResponse<>("Pending Deliveries fetched",HttpStatus.OK,deliveryService.getPendingDeliveryListByDrone(droneExist));
        }
        return new ApiResponse<>("Drone with specified ID not found",HttpStatus.NOT_FOUND);
    }



    @GetMapping("/ds/get-drone/{drone_id}")
    public ApiResponse<Object> getDrones(@PathVariable("drone_id") Long droneId){

        Drone drone = droneService.getDroneById(droneId);
        if(drone == null){
            return new ApiResponse<>("Drone not found",HttpStatus.EXPECTATION_FAILED);
        }

        return new ApiResponse<>(String.format("Drone data found"),HttpStatus.OK,drone);
    }

    @PutMapping("/ds/update-drone/{drone_id}")
    public ApiResponse<Object> updateDrones(@PathVariable("drone_id") Long droneId, @RequestBody Drone drone){

        //get the drone from db
        //
        Drone fetchedDrone = droneService.getDroneById(droneId);
        if(fetchedDrone == null){
            return new ApiResponse<>("Drone not found",HttpStatus.EXPECTATION_FAILED);
        }

        if(drone.getState() != null){
            fetchedDrone.setState(drone.getState());
        }
        if(drone.getBatteryCapacity() == 0){
            fetchedDrone.setBatteryCapacity(drone.getBatteryCapacity());
        }
        if(drone.getSerialNumber() != null){
            fetchedDrone.setSerialNumber(drone.getSerialNumber());
        }
        if(drone.getModel() != null){
            fetchedDrone.setModel(drone.getModel());
        }
        if(drone.getWeightLimit() != 0){
            fetchedDrone.setWeightLimit(drone.getWeightLimit());
        }
        droneService.updateDrone(fetchedDrone);
        return new ApiResponse<>(String.format("Drone data updated"),HttpStatus.OK,fetchedDrone);
    }


    @DeleteMapping("/ds/delete-drone/{drone_id}")
    public ApiResponse<Object> deleteDrones(@PathVariable("drone_id") Long droneId){

        //get the drone from db
        //

        if(!droneService.droneExist(droneId)){
            return new ApiResponse<>("Drone not found",HttpStatus.EXPECTATION_FAILED);
        }

        droneService.deleteDrone(droneId);
        return new ApiResponse<>("Drone delete successfully",HttpStatus.OK);
    }



    @GetMapping("/ds/load-drone/{drone_id}/{medication_id}")
    public ApiResponse<Object> loadSpecifiedDrone(@PathVariable("drone_id") Long drone_id, @PathVariable("medication_id") Long medication_id){
        //Get an idle drone for loading
        Drone fetchedDrone = droneService.getDroneById(drone_id);

        if(fetchedDrone == null){
            return new ApiResponse<>("Drone not found",HttpStatus.EXPECTATION_FAILED);
        }


        //Ensure the battery capacity is higher than 25%
        if(fetchedDrone.getBatteryCapacity() <= 25){
            return new ApiResponse<>("Drone battery capacity is quite low to do any task!",HttpStatus.EXPECTATION_FAILED);
        }

        // Logic that checks the weight of each medications before loading on the drone.
        // To prevent overloading drone.
            Medication  fetchedMedication = medicationService.getMedicationById(medication_id);

            if(fetchedMedication != null){

                if(fetchedMedication.getWeight() < fetchedDrone.getWeightLimit()){
                    //gets the first recipient as default
                    fetchedDrone.setState(DroneState.LOADING);
                    Recipient recipient = recipientService.getRecipientById(1L);
                    deliveryService.loadDrone(new Delivery(fetchedDrone,fetchedMedication, LocalDateTime.now(),recipient));
                    //change drone status after loading
                    fetchedDrone.setState(DroneState.LOADED);
                    droneService.updateDrone(fetchedDrone);

                }else{
                    return new ApiResponse<>("Drone has reached it maximum weight limit!",HttpStatus.EXPECTATION_FAILED);
                }
            }




        return new ApiResponse<>(String.format("Drone loaded successfully"),HttpStatus.OK);
    }


    @GetMapping("/ds/change-drone-status/{drone_id}/{state}")
    public ApiResponse<Object> changeDroneStatus(@PathVariable("drone_id") Long drone_id, @PathVariable("state") DroneState state){
        //Get an idle drone for loading
        Drone fetchedDrone = droneService.getDroneById(drone_id);
        if(fetchedDrone == null){
            return new ApiResponse<>("Drone not found",HttpStatus.EXPECTATION_FAILED);
        }
        // Logic that checks the weight of each medications before loading on the drone.
        // To prevent overloading drone.
        //gets the first recipient as default
        fetchedDrone.setState(state);
        droneService.updateDrone(fetchedDrone);

        return new ApiResponse<>("Drone status changed successfully",HttpStatus.OK);
    }





}
