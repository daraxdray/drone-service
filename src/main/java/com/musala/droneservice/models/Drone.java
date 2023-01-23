package com.musala.droneservice.models;

import com.musala.droneservice.utils.DroneModel;
import com.musala.droneservice.utils.DroneState;
import jakarta.persistence.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name = "drone")
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private static Log log = LogFactory.getLog(Drone.class);

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

    public Drone(DroneModel model) {
        this.model = model;
    }

    public Drone(String serialNumber) {
        this.serialNumber = serialNumber;
    }

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
    public String isEmptyProperty(){
        String emptyProperties = "";
        if(getModel() == null) emptyProperties += "Model, ";
        if(getState() == null) emptyProperties += "State, ";
        if(getSerialNumber() == null) emptyProperties += "Serial Number, ";
        if(getBatteryCapacity() == 0) emptyProperties += "Battery Capacity, ";
        if(getWeightLimit() == 0) emptyProperties += "Weight Limit ";
        return emptyProperties;

    }


    @PostLoad
    public void logDroneBattery(){
        log.info(String.format("[DRONE AUDIT][%s] current battery level is: %d%s",getSerialNumber(),getBatteryCapacity(),'%'));
    }
}

