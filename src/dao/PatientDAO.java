package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Patient;
import org.bson.Document;
import util.MongoDBConnection;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Data Access Object for Patient operations.
 * Handles all database operations related to patients.
 */
public class PatientDAO {
    private MongoCollection<Document> collection;

    public PatientDAO() {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        this.collection = database.getCollection("patients");
    }

    /**
     * Add a new patient to the database
     * 
     * @param patient Patient object to add
     * @return true if successful, false otherwise
     */
    public boolean addPatient(Patient patient) {
        try {
            Document doc = new Document("patientId", patient.getPatientId())
                    .append("name", patient.getName())
                    .append("age", patient.getAge())
                    .append("gender", patient.getGender())
                    .append("phone", patient.getPhone())
                    .append("address", patient.getAddress())
                    .append("disease", patient.getDisease());

            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding patient: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update an existing patient
     * 
     * @param patient Patient object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updatePatient(Patient patient) {
        try {
            Document updatedDoc = new Document("name", patient.getName())
                    .append("age", patient.getAge())
                    .append("gender", patient.getGender())
                    .append("phone", patient.getPhone())
                    .append("address", patient.getAddress())
                    .append("disease", patient.getDisease());

            Document updateOperation = new Document("$set", updatedDoc);
            collection.updateOne(eq("patientId", patient.getPatientId()), updateOperation);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating patient: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a patient by ID
     * 
     * @param patientId Patient ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deletePatient(String patientId) {
        try {
            collection.deleteOne(eq("patientId", patientId));
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting patient: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get a patient by ID
     * 
     * @param patientId Patient ID to search
     * @return Patient object if found, null otherwise
     */
    public Patient getPatientById(String patientId) {
        try {
            Document doc = collection.find(eq("patientId", patientId)).first();
            if (doc != null) {
                Patient patient = new Patient();
                patient.setPatientId(doc.getString("patientId"));
                patient.setName(doc.getString("name"));
                patient.setAge(doc.getInteger("age"));
                patient.setGender(doc.getString("gender"));
                patient.setPhone(doc.getString("phone"));
                patient.setAddress(doc.getString("address"));
                patient.setDisease(doc.getString("disease"));
                return patient;
            }
        } catch (Exception e) {
            System.err.println("Error retrieving patient: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all patients from the database
     * 
     * @return List of all patients
     */
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        try {
            for (Document doc : collection.find()) {
                Patient patient = new Patient();
                patient.setPatientId(doc.getString("patientId"));
                patient.setName(doc.getString("name"));
                patient.setAge(doc.getInteger("age"));
                patient.setGender(doc.getString("gender"));
                patient.setPhone(doc.getString("phone"));
                patient.setAddress(doc.getString("address"));
                patient.setDisease(doc.getString("disease"));
                patients.add(patient);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving patients: " + e.getMessage());
        }
        return patients;
    }
}
