package com.example.tatwa10.ModelClass;

public class Appointment {

    private String id;
    private String doctorName;
    private String appointmentDate;
    private String appointmentTime;
    private boolean appointmentDone;
    private boolean appointmentAccepted;
    private String name;

    public Appointment(String id, String doctorName, String appointmentDate, String appointmentTime, boolean appointmentDone, boolean appointmentAccepted, String name) {
        this.id = id;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDone = appointmentDone;
        this.appointmentAccepted = appointmentAccepted;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAppointmentDone() {
        return appointmentDone;
    }

    public void setAppointmentDone(boolean appointmentDone) {
        this.appointmentDone = appointmentDone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public boolean isAppointmentAccepted() {
        return appointmentAccepted;
    }

    public void setAppointmentAccepted(boolean appointmentAccepted) {
        this.appointmentAccepted = appointmentAccepted;
    }

    public Appointment() {
    }
}
