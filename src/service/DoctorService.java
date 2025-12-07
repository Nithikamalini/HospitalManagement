package service;

import dao.DoctorDAO;
import model.Doctor;

import java.util.List;
import java.util.Scanner;

/**
 * Service class for Doctor management.
 * Contains business logic and validation for doctor operations.
 */
public class DoctorService {
    private DoctorDAO doctorDAO;
    private Scanner scanner;

    public DoctorService() {
        this.doctorDAO = new DoctorDAO();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display doctor management menu
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n========== Doctor Management ==========");
            System.out.println("1. Add New Doctor");
            System.out.println("2. Update Doctor");
            System.out.println("3. Delete Doctor");
            System.out.println("4. View All Doctors");
            System.out.println("5. Search Doctor by ID");
            System.out.println("6. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        updateDoctor();
                        break;
                    case 3:
                        deleteDoctor();
                        break;
                    case 4:
                        viewAllDoctors();
                        break;
                    case 5:
                        searchDoctor();
                        break;
                    case 6:
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
     * Add a new doctor
     */
    private void addDoctor() {
        try {
            System.out.println("\n--- Add New Doctor ---");

            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("✗ Name cannot be empty!");
                return;
            }

            System.out.print("Enter Specialization: ");
            String specialization = scanner.nextLine().trim();
            if (specialization.isEmpty()) {
                System.out.println("✗ Specialization cannot be empty!");
                return;
            }

            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("✗ Phone cannot be empty!");
                return;
            }

            System.out.print("Enter Experience (in years): ");
            int experience = Integer.parseInt(scanner.nextLine());
            if (experience < 0) {
                System.out.println("✗ Experience cannot be negative!");
                return;
            }

            Doctor doctor = new Doctor(name, specialization, phone, experience);

            if (doctorDAO.addDoctor(doctor)) {
                System.out.println("✓ Doctor added successfully!");
                System.out.println("Doctor ID: " + doctor.getDoctorId());
            } else {
                System.out.println("✗ Failed to add doctor!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input! Please enter valid data.");
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * Update an existing doctor
     */
    private void updateDoctor() {
        try {
            System.out.println("\n--- Update Doctor ---");
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine().trim();

            Doctor existingDoctor = doctorDAO.getDoctorById(doctorId);
            if (existingDoctor == null) {
                System.out.println("✗ Doctor not found!");
                return;
            }

            System.out.println("\nCurrent Details:");
            System.out.println(existingDoctor);

            System.out.print("\nEnter New Name (or press Enter to keep current): ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                existingDoctor.setName(name);
            }

            System.out.print("Enter New Specialization (or press Enter to keep current): ");
            String specialization = scanner.nextLine().trim();
            if (!specialization.isEmpty()) {
                existingDoctor.setSpecialization(specialization);
            }

            System.out.print("Enter New Phone (or press Enter to keep current): ");
            String phone = scanner.nextLine().trim();
            if (!phone.isEmpty()) {
                existingDoctor.setPhone(phone);
            }

            System.out.print("Enter New Experience (or press Enter to keep current): ");
            String expStr = scanner.nextLine().trim();
            if (!expStr.isEmpty()) {
                int experience = Integer.parseInt(expStr);
                if (experience >= 0) {
                    existingDoctor.setExperience(experience);
                }
            }

            if (doctorDAO.updateDoctor(existingDoctor)) {
                System.out.println("✓ Doctor updated successfully!");
            } else {
                System.out.println("✗ Failed to update doctor!");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * Delete a doctor
     */
    private void deleteDoctor() {
        try {
            System.out.println("\n--- Delete Doctor ---");
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine().trim();

            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("✗ Doctor not found!");
                return;
            }

            System.out.println("\nDoctor Details:");
            System.out.println(doctor);
            System.out.print("\nAre you sure you want to delete this doctor? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes")) {
                if (doctorDAO.deleteDoctor(doctorId)) {
                    System.out.println("✓ Doctor deleted successfully!");
                } else {
                    System.out.println("✗ Failed to delete doctor!");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View all doctors
     */
    private void viewAllDoctors() {
        System.out.println("\n--- All Doctors ---");
        List<Doctor> doctors = doctorDAO.getAllDoctors();

        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            System.out.println("Total Doctors: " + doctors.size());
            System.out.println("========================================");
            for (Doctor doctor : doctors) {
                System.out.println(doctor);
                System.out.println("----------------------------------------");
            }
        }
    }

    /**
     * Search doctor by ID
     */
    private void searchDoctor() {
        try {
            System.out.println("\n--- Search Doctor ---");
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine().trim();

            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor != null) {
                System.out.println("\n" + doctor);
            } else {
                System.out.println("✗ Doctor not found!");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}
