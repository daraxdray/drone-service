package com.musala.droneservice.repositories;

import com.musala.droneservice.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
