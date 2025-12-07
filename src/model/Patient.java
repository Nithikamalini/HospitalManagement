package model;

import java.util.UUID;

/**
 * Patient model class representing a patient entity in the hospital system.
 */
public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String address;
    private String disease;

    // Default constructor
    public Patient() {
        this.patientId = UUID.randomUUID().toString();
    }

    // Parameterized constructor
    public Patient(String name, int age, String gender, String phone, String address, String disease) {
        this.patientId = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.disease = disease;
    }

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Override
    public String toString() {
        return String.format(
            "Patient ID: %s\nName: %s\nAge: %d\nGender: %s\nPhone: %s\nAddress: %s\nDisease: %s",
            patientId, name, age, gender, phone, address, disease
        );
    }
}
