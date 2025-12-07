package model;

import java.util.UUID;

/**
 * Doctor model class representing a doctor entity in the hospital system.
 */
public class Doctor {
    private String doctorId;
    private String name;
    private String specialization;
    private String phone;
    private int experience;

    // Default constructor
    public Doctor() {
        this.doctorId = UUID.randomUUID().toString();
    }

    // Parameterized constructor
    public Doctor(String name, String specialization, String phone, int experience) {
        this.doctorId = UUID.randomUUID().toString();
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.experience = experience;
    }

    // Getters and Setters
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return String.format(
            "Doctor ID: %s\nName: %s\nSpecialization: %s\nPhone: %s\nExperience: %d years",
            doctorId, name, specialization, phone, experience
        );
    }
}
