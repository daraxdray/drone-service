package com.musala.droneservice.controllers;

import com.musala.droneservice.models.ApiResponse;
import com.musala.droneservice.models.Drone;
import com.musala.droneservice.models.Medication;
import com.musala.droneservice.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicationController {

    @Autowired
    MedicationService medicationService;


    @PostMapping("/md/create")
    public ApiResponse<Object> registerMedication(@RequestBody Medication medication) {

        //validation logic
        if(medication.getName() == null || medication.getCode() == null || medication.getImage() == null || medication.getWeight() <= 0) {
            return new ApiResponse<Object>(String.format("Invalid input, expected fields includes: %s",medication.isEmptyProperty()),HttpStatus.BAD_REQUEST,"");
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

    @PostMapping("/md/create-all")
    public ApiResponse<Object> registerAllMedications(@RequestBody List<Medication> medications) {

        for(Medication medication: medications) {
            //validation logic
            if (medication.getName() == null || medication.getCode() == null || medication.getImage() == null || medication.getWeight() <= 0) {
                return new ApiResponse<Object>(String.format("Invalid input, expected fields includes: %s", medication.isEmptyProperty()), HttpStatus.BAD_REQUEST, "");
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

    @GetMapping("/md/get-medications")
    public ApiResponse<Object> getMedications(){
        return new ApiResponse<>("Medications loaded successfully",HttpStatus.OK,medicationService.getMedications());
    }


    @GetMapping("/md/get-medication/{md_id}")
    public ApiResponse<Object> getMedication(@PathVariable("md_id") Long medId){

        Medication med = medicationService.getMedicationById(medId);
        if(med == null){
            return new ApiResponse<>("Medication not found",HttpStatus.EXPECTATION_FAILED);
        }

        return new ApiResponse<>("Drone data found",HttpStatus.OK,med);
    }

    @PutMapping("/md/update-medication/{md_id}")
    public ApiResponse<Object> updateDrones(@PathVariable("md_id") Long medId, @RequestBody Medication medication){

        //get the drone from db
        //
        Medication fetchedMed = medicationService.getMedicationById(medId);
        if(fetchedMed == null){
            return new ApiResponse<>("Medication not found",HttpStatus.EXPECTATION_FAILED);
        }

        if(medication.getCode() != null){
            fetchedMed.setCode(medication.getCode());
        }
        if(medication.getImage() != null){
            fetchedMed.setImage(medication.getImage());
        }
        if(medication.getWeight() != 0){
            fetchedMed.setWeight(medication.getWeight());
        }
        if(medication.getName() != null){
            fetchedMed.setName(medication.getName());
        }
        medicationService.updateMedication(fetchedMed);
        return new ApiResponse<>(String.format("Drone data updated"),HttpStatus.OK,fetchedMed);
    }


    @DeleteMapping("/md/delete-medication/{md_id}")
    public ApiResponse<Object> deleteDrones(@PathVariable("md_id") Long medId){

        //get the medication from db
        //

        if(!medicationService.medicationExists(medId)){
            return new ApiResponse<>("Medication not found",HttpStatus.EXPECTATION_FAILED);
        }

        medicationService.deleteMedication(medId);
        return new ApiResponse<>("Medication deleted successfully",HttpStatus.OK);
    }






}
