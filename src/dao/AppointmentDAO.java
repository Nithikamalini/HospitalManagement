package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Appointment;
import org.bson.Document;
import util.MongoDBConnection;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Data Access Object for Appointment operations.
 * Handles all database operations related to appointments.
 */
public class AppointmentDAO {
    private MongoCollection<Document> collection;

    public AppointmentDAO() {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        this.collection = database.getCollection("appointments");
    }

    /**
     * Book a new appointment
     * 
     * @param appointment Appointment object to add
     * @return true if successful, false otherwise
     */
    public boolean bookAppointment(Appointment appointment) {
        try {
            Document doc = new Document("appointmentId", appointment.getAppointmentId())
                    .append("patientId", appointment.getPatientId())
                    .append("doctorId", appointment.getDoctorId())
                    .append("date", appointment.getDate())
                    .append("time", appointment.getTime());

            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error booking appointment: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get an appointment by ID
     * 
     * @param appointmentId Appointment ID to search
     * @return Appointment object if found, null otherwise
     */
    public Appointment getAppointmentById(String appointmentId) {
        try {
            Document doc = collection.find(eq("appointmentId", appointmentId)).first();
            if (doc != null) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(doc.getString("appointmentId"));
                appointment.setPatientId(doc.getString("patientId"));
                appointment.setDoctorId(doc.getString("doctorId"));
                appointment.setDate(doc.getString("date"));
                appointment.setTime(doc.getString("time"));
                return appointment;
            }
        } catch (Exception e) {
            System.err.println("Error retrieving appointment: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all appointments from the database
     * 
     * @return List of all appointments
     */
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try {
            for (Document doc : collection.find()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(doc.getString("appointmentId"));
                appointment.setPatientId(doc.getString("patientId"));
                appointment.setDoctorId(doc.getString("doctorId"));
                appointment.setDate(doc.getString("date"));
                appointment.setTime(doc.getString("time"));
                appointments.add(appointment);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving appointments: " + e.getMessage());
        }
        return appointments;
    }
}
