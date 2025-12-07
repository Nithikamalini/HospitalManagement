package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Bill;
import org.bson.Document;
import util.MongoDBConnection;

import static com.mongodb.client.model.Filters.eq;

/**
 * Data Access Object for Bill operations.
 * Handles all database operations related to billing.
 */
public class BillDAO {
    private MongoCollection<Document> collection;

    public BillDAO() {
        MongoDatabase database = MongoDBConnection.getInstance().getDatabase();
        this.collection = database.getCollection("bills");
    }

    /**
     * Create a new bill
     * 
     * @param bill Bill object to add
     * @return true if successful, false otherwise
     */
    public boolean createBill(Bill bill) {
        try {
            Document doc = new Document("billId", bill.getBillId())
                    .append("appointmentId", bill.getAppointmentId())
                    .append("patientName", bill.getPatientName())
                    .append("doctorName", bill.getDoctorName())
                    .append("treatmentCharges", bill.getTreatmentCharges())
                    .append("medicineCharges", bill.getMedicineCharges())
                    .append("totalAmount", bill.getTotalAmount());

            collection.insertOne(doc);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating bill: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get a bill by ID
     * 
     * @param billId Bill ID to search
     * @return Bill object if found, null otherwise
     */
    public Bill getBillById(String billId) {
        try {
            Document doc = collection.find(eq("billId", billId)).first();
            if (doc != null) {
                Bill bill = new Bill();
                bill.setBillId(doc.getString("billId"));
                bill.setAppointmentId(doc.getString("appointmentId"));
                bill.setPatientName(doc.getString("patientName"));
                bill.setDoctorName(doc.getString("doctorName"));
                bill.setTreatmentCharges(doc.getDouble("treatmentCharges"));
                bill.setMedicineCharges(doc.getDouble("medicineCharges"));
                bill.setTotalAmount(doc.getDouble("totalAmount"));
                return bill;
            }
        } catch (Exception e) {
            System.err.println("Error retrieving bill: " + e.getMessage());
        }
        return null;
    }
}
