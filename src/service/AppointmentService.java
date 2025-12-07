package service;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import model.Appointment;
import model.Doctor;
import model.Patient;

import java.util.List;
import java.util.Scanner;

/**
 * Service class for Appointment management.
 * Contains business logic and validation for appointment operations.
 */
public class AppointmentService {
    private AppointmentDAO appointmentDAO;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private Scanner scanner;

    public AppointmentService() {
        this.appointmentDAO = new AppointmentDAO();
        this.patientDAO = new PatientDAO();
        this.doctorDAO = new DoctorDAO();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display appointment management menu
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n========== Appointment Management ==========");
            System.out.println("1. Book New Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. Search Appointment by ID");
            System.out.println("4. Back to Main Menu");
            System.out.println("============================================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        bookAppointment();
                        break;
                    case 2:
                        viewAllAppointments();
                        break;
                    case 3:
                        searchAppointment();
                        break;
                    case 4:
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
     * Book a new appointment
     */
    private void bookAppointment() {
        try {
            System.out.println("\n--- Book New Appointment ---");

            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();

            // Validate patient exists
            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                System.out.println("✗ Patient not found! Please add the patient first.");
                return;
            }
            System.out.println("Patient: " + patient.getName());

            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine().trim();

            // Validate doctor exists
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("✗ Doctor not found! Please add the doctor first.");
                return;
            }
            System.out.println("Doctor: " + doctor.getName() + " (" + doctor.getSpecialization() + ")");

            System.out.print("Enter Date (DD-MM-YYYY): ");
            String date = scanner.nextLine().trim();
            if (date.isEmpty()) {
                System.out.println("✗ Date cannot be empty!");
                return;
            }

            System.out.print("Enter Time (HH:MM): ");
            String time = scanner.nextLine().trim();
            if (time.isEmpty()) {
                System.out.println("✗ Time cannot be empty!");
                return;
            }

            Appointment appointment = new Appointment(patientId, doctorId, date, time);

            if (appointmentDAO.bookAppointment(appointment)) {
                System.out.println("✓ Appointment booked successfully!");
                System.out.println("Appointment ID: " + appointment.getAppointmentId());
                System.out.println("Patient: " + patient.getName());
                System.out.println("Doctor: " + doctor.getName());
                System.out.println("Date: " + date + " at " + time);
            } else {
                System.out.println("✗ Failed to book appointment!");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View all appointments
     */
    private void viewAllAppointments() {
        System.out.println("\n--- All Appointments ---");
        List<Appointment> appointments = appointmentDAO.getAllAppointments();

        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("Total Appointments: " + appointments.size());
            System.out.println("========================================");
            for (Appointment appointment : appointments) {
                // Fetch patient and doctor details for better display
                Patient patient = patientDAO.getPatientById(appointment.getPatientId());
                Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());

                System.out.println(appointment);
                if (patient != null) {
                    System.out.println("Patient Name: " + patient.getName());
                }
                if (doctor != null) {
                    System.out.println("Doctor Name: " + doctor.getName());
                }
                System.out.println("----------------------------------------");
            }
        }
    }

    /**
     * Search appointment by ID
     */
    private void searchAppointment() {
        try {
            System.out.println("\n--- Search Appointment ---");
            System.out.print("Enter Appointment ID: ");
            String appointmentId = scanner.nextLine().trim();

            Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);
            if (appointment != null) {
                Patient patient = patientDAO.getPatientById(appointment.getPatientId());
                Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());

                System.out.println("\n" + appointment);
                if (patient != null) {
                    System.out.println("Patient Name: " + patient.getName());
                }
                if (doctor != null) {
                    System.out.println("Doctor Name: " + doctor.getName());
                }
            } else {
                System.out.println("✗ Appointment not found!");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}
