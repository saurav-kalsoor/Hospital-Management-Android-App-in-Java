package com.example.tatwa10.ModelClass;

public class Prescription {
    private String id;
    private String doctorName;
    private String medicineName;
    private boolean breakfast;
    private boolean lunch;
    private boolean dinner;
    private String dateStart;
    private String dateEnd;
    private int duration;
    private String patientName;

    public Prescription(String id, String doctorName, String medicineName, boolean breakfast, boolean lunch, boolean dinner, String dateStart, String dateEnd, int duration, String patientName) {
        this.id = id;
        this.doctorName = doctorName;
        this.medicineName = medicineName;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
        this.patientName = patientName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public boolean isBreakfast() {
        return breakfast;
    }

    public void setBreakfast(boolean breakfast) {
        this.breakfast = breakfast;
    }

    public boolean isLunch() {
        return lunch;
    }

    public void setLunch(boolean lunch) {
        this.lunch = lunch;
    }

    public boolean isDinner() {
        return dinner;
    }

    public void setDinner(boolean dinner) {
        this.dinner = dinner;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Prescription() {
    }
}
