package service;

import dao.PatientDAO;
import model.Patient;

import java.util.List;
import java.util.Scanner;

/**
 * Service class for Patient management.
 * Contains business logic and validation for patient operations.
 */
public class PatientService {
    private PatientDAO patientDAO;
    private Scanner scanner;

    public PatientService() {
        this.patientDAO = new PatientDAO();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display patient management menu
     */
    public void showMenu() {
        while (true) {
            System.out.println("\n========== Patient Management ==========");
            System.out.println("1. Add New Patient");
            System.out.println("2. Update Patient");
            System.out.println("3. Delete Patient");
            System.out.println("4. View All Patients");
            System.out.println("5. Search Patient by ID");
            System.out.println("6. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        updatePatient();
                        break;
                    case 3:
                        deletePatient();
                        break;
                    case 4:
                        viewAllPatients();
                        break;
                    case 5:
                        searchPatient();
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
     * Add a new patient
     */
    private void addPatient() {
        try {
            System.out.println("\n--- Add New Patient ---");

            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("✗ Name cannot be empty!");
                return;
            }

            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine());
            if (age <= 0 || age > 150) {
                System.out.println("✗ Invalid age! Please enter a valid age.");
                return;
            }

            System.out.print("Enter Gender (Male/Female/Other): ");
            String gender = scanner.nextLine().trim();
            if (gender.isEmpty()) {
                System.out.println("✗ Gender cannot be empty!");
                return;
            }

            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) {
                System.out.println("✗ Phone cannot be empty!");
                return;
            }

            System.out.print("Enter Address: ");
            String address = scanner.nextLine().trim();
            if (address.isEmpty()) {
                System.out.println("✗ Address cannot be empty!");
                return;
            }

            System.out.print("Enter Disease: ");
            String disease = scanner.nextLine().trim();
            if (disease.isEmpty()) {
                System.out.println("✗ Disease cannot be empty!");
                return;
            }

            Patient patient = new Patient(name, age, gender, phone, address, disease);

            if (patientDAO.addPatient(patient)) {
                System.out.println("✓ Patient added successfully!");
                System.out.println("Patient ID: " + patient.getPatientId());
            } else {
                System.out.println("✗ Failed to add patient!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input! Please enter valid data.");
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * Update an existing patient
     */
    private void updatePatient() {
        try {
            System.out.println("\n--- Update Patient ---");
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();

            Patient existingPatient = patientDAO.getPatientById(patientId);
            if (existingPatient == null) {
                System.out.println("✗ Patient not found!");
                return;
            }

            System.out.println("\nCurrent Details:");
            System.out.println(existingPatient);

            System.out.print("\nEnter New Name (or press Enter to keep current): ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                existingPatient.setName(name);
            }

            System.out.print("Enter New Age (or press Enter to keep current): ");
            String ageStr = scanner.nextLine().trim();
            if (!ageStr.isEmpty()) {
                int age = Integer.parseInt(ageStr);
                if (age > 0 && age <= 150) {
                    existingPatient.setAge(age);
                }
            }

            System.out.print("Enter New Gender (or press Enter to keep current): ");
            String gender = scanner.nextLine().trim();
            if (!gender.isEmpty()) {
                existingPatient.setGender(gender);
            }

            System.out.print("Enter New Phone (or press Enter to keep current): ");
            String phone = scanner.nextLine().trim();
            if (!phone.isEmpty()) {
                existingPatient.setPhone(phone);
            }

            System.out.print("Enter New Address (or press Enter to keep current): ");
            String address = scanner.nextLine().trim();
            if (!address.isEmpty()) {
                existingPatient.setAddress(address);
            }

            System.out.print("Enter New Disease (or press Enter to keep current): ");
            String disease = scanner.nextLine().trim();
            if (!disease.isEmpty()) {
                existingPatient.setDisease(disease);
            }

            if (patientDAO.updatePatient(existingPatient)) {
                System.out.println("✓ Patient updated successfully!");
            } else {
                System.out.println("✗ Failed to update patient!");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * Delete a patient
     */
    private void deletePatient() {
        try {
            System.out.println("\n--- Delete Patient ---");
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();

            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                System.out.println("✗ Patient not found!");
                return;
            }

            System.out.println("\nPatient Details:");
            System.out.println(patient);
            System.out.print("\nAre you sure you want to delete this patient? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes")) {
                if (patientDAO.deletePatient(patientId)) {
                    System.out.println("✓ Patient deleted successfully!");
                } else {
                    System.out.println("✗ Failed to delete patient!");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View all patients
     */
    private void viewAllPatients() {
        System.out.println("\n--- All Patients ---");
        List<Patient> patients = patientDAO.getAllPatients();

        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            System.out.println("Total Patients: " + patients.size());
            System.out.println("========================================");
            for (Patient patient : patients) {
                System.out.println(patient);
                System.out.println("----------------------------------------");
            }
        }
    }

    /**
     * Search patient by ID
     */
    private void searchPatient() {
        try {
            System.out.println("\n--- Search Patient ---");
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();

            Patient patient = patientDAO.getPatientById(patientId);
            if (patient != null) {
                System.out.println("\n" + patient);
            } else {
                System.out.println("✗ Patient not found!");
            }
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}
