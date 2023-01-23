package com.musala.droneservice.models;

import jakarta.persistence.*;
import org.springframework.data.domain.Example;


@Entity
@Table(name = "medication")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private double weight;

    @Column(name = "code", length = 100)
    private String code;

    @Lob
    @Column(name = "image")
    private byte[] image;

    public Medication() {}

    public Medication(String name, double weight, String code, byte[] image) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public boolean isValidName(){
        String pattern = "^[a-zA-Z0-9-_]+$";
        return this.name.matches(pattern);
    }

    public boolean isValidCode(){
        String pattern = "^[A-Z0-9_]+$";
        return this.code.matches(pattern);
    }

    //displays the list of empty properties;
    public String isEmptyProperty(){
        String emptyProperties = "";
        if(getName() == null) emptyProperties += "Name, ";
        if(getWeight() <= 0) emptyProperties += "Weight, ";
        if(getImage() == null) emptyProperties += "Image, ";
        if(getCode() == null) emptyProperties += "Code, ";
        return emptyProperties;

    }

}