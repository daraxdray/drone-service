package com.musala.droneservice.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @Column(name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Column(name = "delivery_status")
    private boolean deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    public Delivery() { }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public boolean isDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Delivery(Drone drone, Medication medication, LocalDateTime deliveryTime, Recipient recipient) {
        this.deliveryId = deliveryId;
        this.drone = drone;
        this.medication = medication;
        this.deliveryTime = deliveryTime;
        this.recipient = recipient;
    }
}
