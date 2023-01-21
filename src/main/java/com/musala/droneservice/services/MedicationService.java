package com.musala.droneservice.services;
import com.musala.droneservice.entities.Medication;
import com.musala.droneservice.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    @Autowired
    private MedicationRepository MedicationRepository;

    public Medication createMedication(Medication medication){
        return MedicationRepository.save(medication);
    }

    public List<Medication> createAllMedication(List<Medication> medication){
        return MedicationRepository.saveAll(medication);
    }

    public List<Medication> createAllMedications(List<Medication> medications){
        return MedicationRepository.saveAll(medications);
    }

    public Medication getMedicationById(Long id){
        return MedicationRepository.findById(id).orElse(null) ;
    }

    public List<Medication> getMedications(){
        return MedicationRepository.findAll();
    }

    public Medication updateMedication(Medication medication){
        Optional<Medication> foundMedication = MedicationRepository.findById(medication.getId());
        if(foundMedication.isPresent()){
            Medication oldMedication = foundMedication.get();
            MedicationRepository.save(oldMedication);
        }
        return foundMedication.orElse(null);
    }
    public String deleteMedication(Long id){
        MedicationRepository.deleteById(id);
        return "Medication deleted";
    }

}
