package service;

import dao.AppointmentDAO;
import dao.BillDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import model.Appointment;
import model.Bill;
import model.Doctor;
import model.Patient;

import java.util.Scanner;

/**
 * Service class for Billing management.
 * Contains business logic and validation for billing operations.
 */
public class BillingService {
    private BillDAO billDAO;
    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private Scanner scanner;

    public BillingService() {
        this.billDAO = new BillDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.patientDAO = new PatientDAO();
        this.doctorDAO = new DoctorDAO();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display billing management menu
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n========== Billing Management ==========");
            System.out.println("1. Create New Bill");
            System.out.println("2. View Bill by ID");
            System.out.println("3. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        createBill();
                        break;
                    case 2:
                        viewBill();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("✗ Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a number.");
            }
        }
    }

    /**
     * Create a new bill
     */
    private void createBill() {
        try {
            System.out.println("\n--- Create New Bill ---");

            System.out.print("Enter Appointment ID: ");
            String appointmentId = scanner.nextLine().trim();

            // Validate appointment exists
            Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
            if (appointment == null) {
                System.out.println("✗ Appointment not found! Please book an appointment first.");
                return;
            }

            // Fetch patient and doctor details
            Patient patient = patientDAO.getPatientById(appointment.getPatientId());
            Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());

            if (patient == null || doctor == null) {
                System.out.println("✗ Error: Patient or Doctor information not found!");
                return;
            }

            System.out.println("\nAppointment Details:");
            System.out.println("Patient: " + patient.getName());
            System.out.println("Doctor: " + doctor.getName());
            System.out.println("Date: " + appointment.getDate() + " at " + appointment.getTime());

            System.out.print("\nEnter Treatment Charges: ₹");
            double treatmentCharges = Double.parseDouble(scanner.nextLine());
            if (treatmentCharges < 0) {
                System.out.println("✗ Treatment charges cannot be negative!");
                return;
            }

            System.out.print("Enter Medicine Charges: ₹");
            double medicineCharges = Double.parseDouble(scanner.nextLine());
            if (medicineCharges < 0) {
                System.out.println("✗ Medicine charges cannot be negative!");
                return;
            }

            Bill bill = new Bill(appointmentId, patient.getName(), doctor.getName(),
                    treatmentCharges, medicineCharges);

            if (billDAO.createBill(bill)) {
                System.out.println("\n✓ Bill created successfully!");
                System.out.println("========================================");
                System.out.println(bill);
                System.out.println("========================================");
            } else {
                System.out.println("✗ Failed to create bill!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input! Please enter valid numeric values.");
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View bill by ID
     */
    private void viewBill() {
        try {
            System.out.println("\n--- View Bill ---");
            System.out.print("Enter Bill ID: ");
            String billId = scanner.nextLine().trim();

            Bill bill = billDAO.getBillById(billId);
            if (bill != null) {
                System.out.println("\n========================================");
                System.out.println(bill);
                System.out.println("========================================");
            } else {
                System.out.println("✗ Bill not found!");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}
