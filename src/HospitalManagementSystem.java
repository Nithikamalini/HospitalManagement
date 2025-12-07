import service.AppointmentService;
import service.BillingService;
import service.DoctorService;
import service.PatientService;
import util.MongoDBConnection;

import java.util.Scanner;

/**
 * Hospital Management System - Main Application
 * A comprehensive hospital management system with dual modes:
 * 1. Console-based interface
 * 2. Web-based interface
 * 
 * @author Hospital Management Team
 * @version 2.0
 */
public class HospitalManagementSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   HOSPITAL MANAGEMENT SYSTEM - v2.0        ║");
        System.out.println("╚════════════════════════════════════════════╝");

        System.out.println("\nSelect Mode:");
        System.out.println("1. Console Mode (Terminal Interface)");
        System.out.println("2. Web Mode (Browser Interface)");
        System.out.print("\nEnter your choice (1 or 2): ");

        try {
            int modeChoice = Integer.parseInt(scanner.nextLine());

            switch (modeChoice) {
                case 1:
                    runConsoleMode();
                    break;
                case 2:
                    runWebMode();
                    break;
                default:
                    System.out.println("✗ Invalid choice! Defaulting to Console Mode.");
                    runConsoleMode();
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input! Defaulting to Console Mode.");
            runConsoleMode();
        }
    }

    /**
     * Run the console-based interface
     */
    private static void runConsoleMode() {
        // Initialize MongoDB connection
        MongoDBConnection.getInstance();

        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService();
        BillingService billingService = new BillingService();

        System.out.println("\n✓ Console Mode Activated");

        boolean running = true;

        while (running) {
            try {
                displayMainMenu();
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        patientService.showMenu();
                        break;
                    case 2:
                        doctorService.showMenu();
                        break;
                    case 3:
                        appointmentService.showMenu();
                        break;
                    case 4:
                        billingService.showMenu();
                        break;
                    case 5:
                        running = false;
                        System.out.println("\n✓ Thank you for using Hospital Management System!");
                        System.out.println("✓ Goodbye!");
                        break;
                    default:
                        System.out.println("✗ Invalid choice! Please enter a number between 1-5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("✗ An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Close MongoDB connection
        MongoDBConnection.getInstance().close();
        scanner.close();
    }

    /**
     * Run the web-based interface
     */
    private static void runWebMode() {
        System.out.println("\n✓ Web Mode Activated");
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         WEB SERVER STARTING...             ║");
        System.out.println("╚════════════════════════════════════════════╝");

        try {
            // Start the web server
            WebServer server = new WebServer();
            server.start();

            System.out.println("\n✓ Web server started successfully!");
            System.out.println("✓ Open your browser and navigate to:");
            System.out.println("\n   http://localhost:8080\n");
            System.out.println("Press Enter to stop the server...");

            scanner.nextLine();

            server.stop();
            System.out.println("\n✓ Web server stopped.");
            System.out.println("✓ Thank you for using Hospital Management System!");

        } catch (Exception e) {
            System.err.println("✗ Error starting web server: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

    /**
     * Display the main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      HOSPITAL MANAGEMENT SYSTEM            ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. Patient Management                     ║");
        System.out.println("║  2. Doctor Management                      ║");
        System.out.println("║  3. Appointment Management                 ║");
        System.out.println("║  4. Billing Management                     ║");
        System.out.println("║  5. Exit                                   ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.print("Enter your choice: ");
    }
}
