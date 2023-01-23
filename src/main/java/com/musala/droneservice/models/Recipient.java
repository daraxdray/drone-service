package com.musala.droneservice.models;

import jakarta.persistence.*;

@Entity
@Table(name = "recipient")
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipientId;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "address")
    private String address;

    // getters and setters
}