package com.musala.droneservice.entities;

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

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    // getters and setters
}
