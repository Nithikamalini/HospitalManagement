import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import util.MongoDBConnection;
import dao.*;
import model.*;
import com.google.gson.Gson;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Web Server for Hospital Management System
 * Provides REST API endpoints and serves the HTML frontend
 */
public class WebServer {
    private HttpServer server;
    private Gson gson;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private AppointmentDAO appointmentDAO;
    private BillDAO billDAO;

    public WebServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8080), 0);
        this.gson = new Gson();

        // Initialize MongoDB connection
        MongoDBConnection.getInstance();

        // Initialize DAOs
        this.patientDAO = new PatientDAO();
        this.doctorDAO = new DoctorDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.billDAO = new BillDAO();

        setupRoutes();
    }

    private void setupRoutes() {
        // Serve the main HTML page
        server.createContext("/", this::handleIndex);

        // Patient endpoints
        server.createContext("/api/patients", this::handlePatients);
        server.createContext("/api/patient", this::handlePatient);

        // Doctor endpoints
        server.createContext("/api/doctors", this::handleDoctors);
        server.createContext("/api/doctor", this::handleDoctor);

        // Appointment endpoints
        server.createContext("/api/appointments", this::handleAppointments);
        server.createContext("/api/appointment", this::handleAppointment);

        // Bill endpoints
        server.createContext("/api/bills", this::handleBills);
        server.createContext("/api/bill", this::handleBill);
    }

    private void handleIndex(HttpExchange exchange) throws IOException {
        String htmlPath = "web/index.html";
        File htmlFile = new File(htmlPath);

        if (htmlFile.exists()) {
            byte[] response = Files.readAllBytes(Paths.get(htmlPath));
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        } else {
            String response = "HTML file not found. Please ensure web/index.html exists.";
            exchange.sendResponseHeaders(404, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Patient handlers
    private void handlePatients(HttpExchange exchange) throws IOException {
        setCORSHeaders(exchange);

        if ("GET".equals(exchange.getRequestMethod())) {
            List<Patient> patients = patientDAO.getAllPatients();
            sendJsonResponse(exchange, 200, patients);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            String body = readRequestBody(exchange);
            Patient patient = gson.fromJson(body, Patient.class);
            boolean success = patientDAO.addPatient(patient);
            sendJsonResponse(exchange, success ? 201 : 500,
                    Map.of("success", success, "patientId", patient.getPatientId()));
        } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
        }
    }

    private void handlePatient(HttpExchange exchange) throws IOException {
        setCORSHeaders(exchange);
        String query = exchange.getRequestURI().getQuery();

        if ("GET".equals(exchange.getRequestMethod()) && query != null) {
            String id = getQueryParam(query, "id");
            Patient patient = patientDAO.getPatientById(id);
            sendJsonResponse(exchange, patient != null ? 200 : 404, patient);
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            String body = readRequestBody(exchange);
            Patient patient = gson.fromJson(body, Patient.class);
            boolean success = patientDAO.updatePatient(patient);
            sendJsonResponse(exchange, 200, Map.of("success", success));
        } else if ("DELETE".equals(exchange.getRequestMethod()) && query != null) {
            String id = getQueryParam(query, "id");
            boolean success = patientDAO.deletePatient(id);
            sendJsonResponse(exchange, 200, Map.of("success", success));
        }
    }

    // Doctor handlers
    private void handleDoctors(HttpExchange exchange) throws IOException {
        setCORSHeaders(exchange);

        if ("GET".equals(exchange.getRequestMethod())) {
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            sendJsonResponse(exchange, 200, doctors);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            String body = readRequestBody(exchange);
            Doctor doctor = gson.fromJson(body, Doctor.class);
            boolean success = doctorDAO.addDoctor(doctor);
            sendJsonResponse(exchange, success ? 201 : 500,
                    Map.of("success", success, "doctorId", doctor.getDoctorId()));
        }
    }

    private void handleDoctor(HttpExchange exchange) throws IOException {
        setCORSHeaders(exchange);
        String query = exchange.getRequestURI().getQuery();

        if ("GET".equals(exchange.getRequestMethod()) && query != null) {
            String id = getQueryParam(query, "id");
            Doctor doctor = doctorDAO.getDoctorById(id);
            sendJsonResponse(exchange, doctor != null ? 200 : 404, doctor);
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            String body = readRequestBody(exchange);
            Doctor doctor = gson.fromJson(body, Doctor.class);
            boolean success = doctorDAO.updateDoctor(doctor);
            sendJsonResponse(exchange, 200, Map.of("success", success));
        } else if ("DELETE".equals(exchange.getRequestMethod()) && query != null) {
            String id = getQueryParam(query, "id");
            boolean success = doctorDAO.deleteDoctor(id);
            sendJsonResponse(exchange, 200, Map.of("success", success));
        }
    }

    // Appointment handlers
    private void handleAppointments(HttpExchange exchange) throws IOException {
        setCORSHeaders(exchange);

        if ("GET".equals(exchange.getRequestMethod())) {
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            sendJsonResponse(exchange, 200, appointments);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            String body = readRequestBody(exchange);
            Appointment appointment = gson.fromJson(body, Appointment.class);
            boolean success = appointmentDAO.bookAppointment(appointment);
            sendJsonResponse(exchange, success ? 201 : 500,
                    Map.of("success", success, "appointmentId", appointment.getAppointmentId()));
        }
    }

    private void handleAppointment(HttpExchange exchange) throws IOException {
        setCORSHeaders(exchange);
        String query = exchange.getRequestURI().getQuery();

        if ("GET".equals(exchange.getRequestMethod()) && query != null) {
            String id = getQueryParam(query, "id");
            Appointment appointment = appointmentDAO.getAppointmentById(id);
            sendJsonResponse(exchange, appointment != null ? 200 : 404, appointment);
        }
    }

    // Bill handlers
    private void handleBills(HttpExchange exchange) throws IOException {
        setCORSHeaders(exchange);

        if ("POST".equals(exchange.getRequestMethod())) {
            String body = readRequestBody(exchange);
            Bill bill = gson.fromJson(body, Bill.class);
            boolean success = billDAO.createBill(bill);
            sendJsonResponse(exchange, success ? 201 : 500,
                    Map.of("success", success, "billId", bill.getBillId()));
        }
    }

    private void handleBill(HttpExchange exchange) throws IOException {
        setCORSHeaders(exchange);
        String query = exchange.getRequestURI().getQuery();

        if ("GET".equals(exchange.getRequestMethod()) && query != null) {
            String id = getQueryParam(query, "id");
            Bill bill = billDAO.getBillById(id);
            sendJsonResponse(exchange, bill != null ? 200 : 404, bill);
        }
    }

    // Utility methods
    private void setCORSHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    private String getQueryParam(String query, String param) {
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2 && keyValue[0].equals(param)) {
                return keyValue[1];
            }
        }
        return null;
    }

    private void sendJsonResponse(HttpExchange exchange, int statusCode, Object data) throws IOException {
        String json = gson.toJson(data);
        byte[] response = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.length);
        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    public void start() {
        server.setExecutor(null);
        server.start();
    }

    public void stop() {
        server.stop(0);
        MongoDBConnection.getInstance().close();
    }
}
