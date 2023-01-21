package com.musala.droneservice.controllers;

import com.musala.droneservice.entities.ApiResponse;
import com.musala.droneservice.entities.Medication;
import com.musala.droneservice.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicationController {

    @Autowired
    MedicationService medicationService;
    
    
    @PostMapping("/medications/create")
    public ApiResponse<Object> registerMedication(@RequestBody Medication medication) {

        //validation logic
        if(medication.getName() == null || medication.getCode() == null || medication.getImage() == null || medication.getWeight() <= 0) {
            return new ApiResponse<Object>(String.format("Invalid input, expected fields includes: %s",medication.getEmptyProperty()),HttpStatus.BAD_REQUEST,"");
        }

        //check if weight limit is above maximum limit
        if(!medication.isValidName()){
            return new ApiResponse<>("Found unwanted character in name, Only letters, numbers, '-','_' are acceptable.",HttpStatus.BAD_REQUEST);
        }

        if(!medication.isValidCode()){
            return new ApiResponse<>("Found unwanted character in code, Only uppers case letters, numbers,'_' are acceptable.",HttpStatus.BAD_REQUEST);
        }

        //save the medication to the database
        medicationService.createMedication(medication);
        return new ApiResponse<>("Medication created", HttpStatus.CREATED, medication);
    }

    @PostMapping("/medications/create-all")
    public ApiResponse<Object> registerAllMedications(@RequestBody List<Medication> medications) {

        for(Medication medication: medications) {
            //validation logic
            if (medication.getName() == null || medication.getCode() == null || medication.getImage() == null || medication.getWeight() <= 0) {
                return new ApiResponse<Object>(String.format("Invalid input, expected fields includes: %s", medication.getEmptyProperty()), HttpStatus.BAD_REQUEST, "");
            }

            //check if weight limit is above maximum limit
            if (!medication.isValidName()) {
                return new ApiResponse<>("Found unwanted character in name, Only letters, numbers, '-','_' are acceptable.", HttpStatus.BAD_REQUEST);
            }

            if (!medication.isValidCode()) {
                return new ApiResponse<>("Found unwanted character in code, Only uppers case letters, numbers,'_' are acceptable.", HttpStatus.BAD_REQUEST);
            }
        }

        //save the medication to the database
        medicationService.createAllMedication(medications);
        return new ApiResponse<>("Medication created", HttpStatus.CREATED, medications);
    }





}
