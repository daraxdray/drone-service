package com.musala.droneservice.entities;

import com.musala.droneservice.enums.DroneModel;
import com.musala.droneservice.enums.DroneState;
import jakarta.persistence.*;
import org.springframework.data.domain.Example;

@Entity
@Table(name = "drone")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private DroneModel model;

    @Column(name = "weight_limit")
    private double weightLimit;

    @Column(name = "battery_capacity")
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private DroneState state;

    public Drone() {}

    public Drone(String serialNumber, DroneModel model, double weightLimit, int batteryCapacity, DroneState state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public double getWeightLimit() {
        return weightLimit;
    }



    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public DroneState getState() {
        return state;
    }

    public void setState(DroneState state) {
        this.state = state;
    }



    //Helps to know the empty properties
    public String getEmptyProperty(){
        String emptyProperties = "";
        if(getModel() == null) emptyProperties += "Model, ";
        if(getState() == null) emptyProperties += "State, ";
        if(getSerialNumber() == null) emptyProperties += "Serial Number, ";
        if(getBatteryCapacity() == 0) emptyProperties += "Battery Capacity, ";
        if(getWeightLimit() == 0) emptyProperties += "Weight Limit ";
        return emptyProperties;

    }

    //Helps get an example to use for querying db
    public Example<Drone> getExample(){
        return  Example.of(this);
    }

}

