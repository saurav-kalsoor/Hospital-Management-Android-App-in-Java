package com.example.tatwa10.ModelClass;

public class MedicalRecord {

    private String title;
    private String id;

    public MedicalRecord(String title,  String id) {
        this.title = title;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MedicalRecord() {
    }

}
