package com.musala.droneservice.repositories;

import com.musala.droneservice.models.Delivery;
import com.musala.droneservice.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    //Find delivery data by supplied drone id and delivery status
    @Query("SELECT d FROM Delivery d where d.drone.id = :id and d.deliveryStatus = :status")
    List<Delivery> findByDroneAndDeliveryStatus(Long id, boolean status);

    //Only returns medication list of a provided drone id
    @Query("SELECT d.medication FROM Delivery d where d.drone.id = :id and d.deliveryStatus = :status")
    List<Object> findMedicationByDroneAndDeliveryStatus(Long id, boolean status);

    @Query("SELECT d FROM Delivery d where d.drone.id = :id")
    List<Delivery> findByDroneId(Long droneId);
}
