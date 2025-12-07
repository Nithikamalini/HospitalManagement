package model;

import java.util.UUID;

/**
 * Bill model class representing a billing entity in the hospital system.
 */
public class Bill {
    private String billId;
    private String appointmentId;
    private String patientName;
    private String doctorName;
    private double treatmentCharges;
    private double medicineCharges;
    private double totalAmount;

    // Default constructor
    public Bill() {
        this.billId = UUID.randomUUID().toString();
    }

    // Parameterized constructor
    public Bill(String appointmentId, String patientName, String doctorName, 
                double treatmentCharges, double medicineCharges) {
        this.billId = UUID.randomUUID().toString();
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.treatmentCharges = treatmentCharges;
        this.medicineCharges = medicineCharges;
        this.totalAmount = treatmentCharges + medicineCharges;
    }

    // Getters and Setters
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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

    public double getTreatmentCharges() {
        return treatmentCharges;
    }

    public void setTreatmentCharges(double treatmentCharges) {
        this.treatmentCharges = treatmentCharges;
        this.totalAmount = this.treatmentCharges + this.medicineCharges;
    }

    public double getMedicineCharges() {
        return medicineCharges;
    }

    public void setMedicineCharges(double medicineCharges) {
        this.medicineCharges = medicineCharges;
        this.totalAmount = this.treatmentCharges + this.medicineCharges;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return String.format(
            "Bill ID: %s\nAppointment ID: %s\nPatient Name: %s\nDoctor Name: %s\n" +
            "Treatment Charges: ₹%.2f\nMedicine Charges: ₹%.2f\nTotal Amount: ₹%.2f",
            billId, appointmentId, patientName, doctorName, 
            treatmentCharges, medicineCharges, totalAmount
        );
    }
}
