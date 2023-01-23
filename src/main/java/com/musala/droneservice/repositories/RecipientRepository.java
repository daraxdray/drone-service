package com.musala.droneservice.repositories;

import com.musala.droneservice.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
}
