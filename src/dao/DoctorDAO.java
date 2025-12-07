package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Doctor;
import org.bson.Document;
import util.MongoDBConnection;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * Data Access Object for Doctor operations.
 * Handles all database operations related to doctors.
 */
public class DoctorDAO {
    private MongoCollection<Document> collection;

    public DoctorDAO() {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        this.collection = database.getCollection("doctors");
    }

    /**
     * Add a new doctor to the database
     * 
     * @param doctor Doctor object to add
     * @return true if successful, false otherwise
     */
    public boolean addDoctor(Doctor doctor) {
        try {
            Document doc = new Document("doctorId", doctor.getDoctorId())
                    .append("name", doctor.getName())
                    .append("specialization", doctor.getSpecialization())
                    .append("phone", doctor.getPhone())
                    .append("experience", doctor.getExperience());

            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding doctor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Update an existing doctor
     * 
     * @param doctor Doctor object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateDoctor(Doctor doctor) {
        try {
            Document updatedDoc = new Document("name", doctor.getName())
                    .append("specialization", doctor.getSpecialization())
                    .append("phone", doctor.getPhone())
                    .append("experience", doctor.getExperience());

            Document updateOperation = new Document("$set", updatedDoc);
            collection.updateOne(eq("doctorId", doctor.getDoctorId()), updateOperation);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating doctor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a doctor by ID
     * 
     * @param doctorId Doctor ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteDoctor(String doctorId) {
        try {
            collection.deleteOne(eq("doctorId", doctorId));
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting doctor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get a doctor by ID
     * 
     * @param doctorId Doctor ID to search
     * @return Doctor object if found, null otherwise
     */
    public Doctor getDoctorById(String doctorId) {
        try {
            Document doc = collection.find(eq("doctorId", doctorId)).first();
            if (doc != null) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(doc.getString("doctorId"));
                doctor.setName(doc.getString("name"));
                doctor.setSpecialization(doc.getString("specialization"));
                doctor.setPhone(doc.getString("phone"));
                doctor.setExperience(doc.getInteger("experience"));
                return doctor;
            }
        } catch (Exception e) {
            System.err.println("Error retrieving doctor: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all doctors from the database
     * 
     * @return List of all doctors
     */
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            for (Document doc : collection.find()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(doc.getString("doctorId"));
                doctor.setName(doc.getString("name"));
                doctor.setSpecialization(doc.getString("specialization"));
                doctor.setPhone(doc.getString("phone"));
                doctor.setExperience(doc.getInteger("experience"));
                doctors.add(doctor);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving doctors: " + e.getMessage());
        }
        return doctors;
    }
}
