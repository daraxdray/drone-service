package com.musala.droneservice.services;

import com.musala.droneservice.models.Delivery;
import com.musala.droneservice.models.Drone;
import com.musala.droneservice.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    //Load the drone and update the delivery table
    public Delivery loadDrone(Delivery delivery){
        return deliveryRepository.save(delivery);
    }

    //get all active delivery list of drone.
    public List<Delivery> getPendingDeliveryListByDrone(Drone drone){
        return deliveryRepository.findByDroneAndDeliveryStatus(drone.getId(),false);
    }

    //get all active delivery list of drone.
    public List<Object> findMedicationByDroneAndDeliveryStatus(Drone drone){
        System.out.println(deliveryRepository.findMedicationByDroneAndDeliveryStatus(drone.getId(),false));
         return deliveryRepository.findMedicationByDroneAndDeliveryStatus(drone.getId(),false);

    }

//    get all inactive delivery list of drone
    public List<Delivery> getInActiveDeliveryListByDrone(Drone drone){
        return deliveryRepository.findByDroneAndDeliveryStatus(drone.getId(),true);
    }

    //get all delivery list of drone
    public List<Delivery> getInAllDeliveryListByDrone(Drone drone){
        return deliveryRepository.findByDroneId(drone.getId());
    }


}
