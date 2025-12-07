# Hospital Management System

A comprehensive **Java console-based Hospital Management System** with **MongoDB** integration. This system provides complete CRUD operations for managing patients, doctors, appointments, and billing in a hospital environment.

## 📋 Features

### Core Modules
- **Patient Management**: Add, update, delete, view, and search patients
- **Doctor Management**: Add, update, delete, view, and search doctors
- **Appointment Management**: Book appointments with validation, view all appointments, search by ID
- **Billing Management**: Create bills with auto-calculation, view bill details

### Key Highlights
- ✅ Clean architecture with DAO and Service layers
- ✅ Input validation and exception handling
- ✅ Referential integrity (appointments validate patient/doctor existence)
- ✅ Auto-generated UUIDs for all entities
- ✅ Auto-calculated bill totals
- ✅ MongoDB integration with proper connection management
- ✅ User-friendly console interface

---

## 🛠️ Prerequisites

### Required Software
1. **Java Development Kit (JDK) 17 or higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify installation: `java -version`

2. **MongoDB**
   - **Option A**: Local MongoDB
     - Download from: https://www.mongodb.com/try/download/community
     - Start MongoDB service: `mongod --dbpath <your-data-path>`
   - **Option B**: MongoDB Atlas (Cloud)
     - Sign up at: https://www.mongodb.com/cloud/atlas
     - Update connection string in `MongoDBConnection.java`

3. **MongoDB Java Driver**
   - Required JAR: `mongodb-driver-sync-4.11.1.jar` (or latest version)

---

## 📦 Setup Instructions

### Step 1: Download MongoDB Java Driver

#### Option A: Using Maven (Recommended)
Create a `pom.xml` file in the project root:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.hospital</groupId>
    <artifactId>hospital-management</artifactId>
    <version>1.0</version>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver-sync</artifactId>
            <version>4.11.1</version>
        </dependency>
    </dependencies>
</project>
```

#### Option B: Manual JAR Download
1. Download MongoDB Java Driver from: https://repo1.maven.org/maven2/org/mongodb/mongodb-driver-sync/4.11.1/
2. Download these JARs:
   - `mongodb-driver-sync-4.11.1.jar`
   - `mongodb-driver-core-4.11.1.jar`
   - `bson-4.11.1.jar`
3. Place all JARs in the `lib/` folder

### Step 2: Start MongoDB
```bash
# If using local MongoDB
mongod --dbpath C:\data\db

# MongoDB will run on: mongodb://localhost:27017
```

### Step 3: Compile the Project

#### Using Maven:
```bash
mvn clean compile
```

#### Manual Compilation (with JARs in lib/):
```bash
# Windows
javac -cp ".;lib/*" -d bin src/model/*.java src/util/*.java src/dao/*.java src/service/*.java src/HospitalManagementSystem.java

# Linux/Mac
javac -cp ".:lib/*" -d bin src/model/*.java src/util/*.java src/dao/*.java src/service/*.java src/HospitalManagementSystem.java
```

### Step 4: Run the Application

#### Using Maven:
```bash
mvn exec:java -Dexec.mainClass="HospitalManagementSystem"
```

#### Manual Execution:
```bash
# Windows
java -cp ".;lib/*;bin" HospitalManagementSystem

# Linux/Mac
java -cp ".:lib/*:bin" HospitalManagementSystem
```

---

## 🗄️ MongoDB Schema

The application automatically creates the following collections:

### Collections

#### 1. **patients**
```json
{
  "patientId": "UUID",
  "name": "String",
  "age": "Integer",
  "gender": "String",
  "phone": "String",
  "address": "String",
  "disease": "String"
}
```

#### 2. **doctors**
```json
{
  "doctorId": "UUID",
  "name": "String",
  "specialization": "String",
  "phone": "String",
  "experience": "Integer"
}
```

#### 3. **appointments**
```json
{
  "appointmentId": "UUID",
  "patientId": "String (reference)",
  "doctorId": "String (reference)",
  "date": "String",
  "time": "String"
}
```

#### 4. **bills**
```json
{
  "billId": "UUID",
  "appointmentId": "String (reference)",
  "patientName": "String",
  "doctorName": "String",
  "treatmentCharges": "Double",
  "medicineCharges": "Double",
  "totalAmount": "Double (auto-calculated)"
}
```

### Create Indexes (Optional, for better performance)
```javascript
// Connect to MongoDB shell
use hospital_management

// Create indexes
db.patients.createIndex({ "patientId": 1 })
db.doctors.createIndex({ "doctorId": 1 })
db.appointments.createIndex({ "appointmentId": 1 })
db.bills.createIndex({ "billId": 1 })
```

---

## 📖 Usage Guide

### Sample Workflow

1. **Add a Patient**
   - Select: `1. Patient Management` → `1. Add New Patient`
   - Enter patient details
   - Note the generated Patient ID

2. **Add a Doctor**
   - Select: `2. Doctor Management` → `1. Add New Doctor`
   - Enter doctor details
   - Note the generated Doctor ID

3. **Book an Appointment**
   - Select: `3. Appointment Management` → `1. Book New Appointment`
   - Enter Patient ID and Doctor ID (from steps 1 & 2)
   - Enter date and time
   - Note the generated Appointment ID

4. **Create a Bill**
   - Select: `4. Billing Management` → `1. Create New Bill`
   - Enter Appointment ID (from step 3)
   - Enter treatment and medicine charges
   - Total amount is calculated automatically

### Sample Input/Output

```
╔════════════════════════════════════════════╗
║      HOSPITAL MANAGEMENT SYSTEM            ║
╠════════════════════════════════════════════╣
║  1. Patient Management                     ║
║  2. Doctor Management                      ║
║  3. Appointment Management                 ║
║  4. Billing Management                     ║
║  5. Exit                                   ║
╚════════════════════════════════════════════╝
Enter your choice: 1

========== Patient Management ==========
1. Add New Patient
2. Update Patient
3. Delete Patient
4. View All Patients
5. Search Patient by ID
6. Back to Main Menu
========================================
Enter your choice: 1

--- Add New Patient ---
Enter Name: John Doe
Enter Age: 45
Enter Gender (Male/Female/Other): Male
Enter Phone: 9876543210
Enter Address: 123 Main Street, City
Enter Disease: Fever
✓ Patient added successfully!
Patient ID: a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

---

## 🐛 Troubleshooting

### Issue: "Error connecting to MongoDB"
**Solution**: 
- Ensure MongoDB is running: `mongod --dbpath <path>`
- Check connection string in `MongoDBConnection.java`
- Verify MongoDB is accessible on `localhost:27017`

### Issue: "ClassNotFoundException: com.mongodb.client.MongoClient"
**Solution**: 
- Ensure MongoDB driver JARs are in the `lib/` folder
- Include JARs in classpath when compiling/running

### Issue: "Patient/Doctor not found" when booking appointment
**Solution**: 
- First add patients and doctors
- Use the exact Patient ID and Doctor ID shown after creation

---

## 📁 Project Structure

```
hospitalmanagement/
├── src/
│   ├── model/
│   │   ├── Patient.java
│   │   ├── Doctor.java
│   │   ├── Appointment.java
│   │   └── Bill.java
│   ├── dao/
│   │   ├── PatientDAO.java
│   │   ├── DoctorDAO.java
│   │   ├── AppointmentDAO.java
│   │   └── BillDAO.java
│   ├── service/
│   │   ├── PatientService.java
│   │   ├── DoctorService.java
│   │   ├── AppointmentService.java
│   │   └── BillingService.java
│   ├── util/
│   │   └── MongoDBConnection.java
│   └── HospitalManagementSystem.java
├── lib/                          (MongoDB driver JARs)
├── bin/                          (Compiled classes)
├── pom.xml                       (Maven configuration - optional)
└── README.md
```

---

## 🔒 Database Connection

**Default Configuration:**
- **Connection URL**: `mongodb://localhost:27017`
- **Database Name**: `hospital_management`

To change the connection settings, edit `src/util/MongoDBConnection.java`:
```java
private static final String CONNECTION_STRING = "mongodb://localhost:27017";
private static final String DATABASE_NAME = "hospital_management";
```

---

## 👨‍💻 Development

### Technologies Used
- **Language**: Java 17
- **Database**: MongoDB
- **Driver**: MongoDB Java Driver (Sync) 4.11.1
- **Architecture**: Layered (Model-DAO-Service)
- **Design Patterns**: Singleton (MongoDB Connection), DAO Pattern

### Code Quality Features
- Exception handling throughout
- Input validation on all user inputs
- Referential integrity checks
- Clean separation of concerns
- Comprehensive error messages

