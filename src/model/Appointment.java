package model;

import java.util.UUID;

/**
 * Appointment model class representing an appointment entity in the hospital system.
 */
public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;

    // Default constructor
    public Appointment() {
        this.appointmentId = UUID.randomUUID().toString();
    }

    // Parameterized constructor
    public Appointment(String patientId, String doctorId, String date, String time) {
        this.appointmentId = UUID.randomUUID().toString();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format(
            "Appointment ID: %s\nPatient ID: %s\nDoctor ID: %s\nDate: %s\nTime: %s",
            appointmentId, patientId, doctorId, date, time
        );
    }
}
