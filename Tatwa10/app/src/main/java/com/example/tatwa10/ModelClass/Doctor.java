package com.example.tatwa10.ModelClass;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private int id;
    private String name;
    private String specification;
    private String qualifications;
    private String description;
    private int imageUri;
    private String password;
    private List<Boolean> slotToday = new ArrayList<>();
    private List<Boolean> slotTomorrow = new ArrayList<>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }

    public Doctor(String name, String specification, String qualifications, String description, int imageUri) {
        this.name = name;
        this.specification = specification;
        this.qualifications = qualifications;
        this.description = description;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Boolean> getSlotToday() {
        return slotToday;
    }

    public void setSlotToday(List<Boolean> slotToday) {
        this.slotToday = slotToday;
    }

    public List<Boolean> getSlotTomorrow() {
        return slotTomorrow;
    }

    public void setSlotTomorrow(List<Boolean> slotTomorrow) {
        this.slotTomorrow = slotTomorrow;
    }

    public Doctor(String name, String password) {
        this.name = name;
        this.password = password;

        for (int i =0;i<12;i++){
            this.slotToday.add(false);
        }
        for (int i =0;i<12;i++){
            this.slotTomorrow.add(false);
        }
    }

    public Doctor() {
    }
}
