package com.musala.droneservice.services;

import com.musala.droneservice.models.Recipient;
import com.musala.droneservice.repositories.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipientService {

    @Autowired
    RecipientRepository recipientRepository;

    public Recipient saveRecipient(Recipient recipient){
        return recipientRepository.save(recipient);
    }


    public Recipient getRecipientById(Long id){
      return recipientRepository.findById(id).orElse(null);
    }


}
