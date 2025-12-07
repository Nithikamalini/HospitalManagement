package util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * MongoDB Connection Utility using Singleton pattern.
 * Manages connection to MongoDB database.
 */
public class MongoDBConnection {
    private static MongoDBConnection instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "hospital_management";

    // Private constructor for Singleton pattern
    private MongoDBConnection() {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
            System.out.println("✓ Successfully connected to MongoDB!");
        } catch (Exception e) {
            System.err.println("✗ Error connecting to MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get singleton instance of MongoDBConnection
     * 
     * @return MongoDBConnection instance
     */
    public static MongoDBConnection getInstance() {
        if (instance == null) {
            synchronized (MongoDBConnection.class) {
                if (instance == null) {
                    instance = new MongoDBConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Get the MongoDB database instance
     * 
     * @return MongoDatabase instance
     */
    public MongoDatabase getDatabase() {
        return database;
    }

    /**
     * Close MongoDB connection
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("✓ MongoDB connection closed.");
        }
    }
}
