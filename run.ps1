# Hospital Management System - Dual Mode Runner

Write-Host "`n╔════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║   HOSPITAL MANAGEMENT SYSTEM - v2.0        ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════╝" -ForegroundColor Cyan

# Step 1: Create bin directory
Write-Host "`n=== Creating bin directory ===" -ForegroundColor Yellow
New-Item -ItemType Directory -Force -Path "bin" | Out-Null

# Step 2: Compile all Java files
Write-Host "`n=== Compiling Java files ===" -ForegroundColor Yellow
javac -encoding UTF-8 -cp ".;lib/*" -d bin `
    src/model/Patient.java `
    src/model/Doctor.java `
    src/model/Appointment.java `
    src/model/Bill.java `
    src/util/MongoDBConnection.java `
    src/dao/PatientDAO.java `
    src/dao/DoctorDAO.java `
    src/dao/AppointmentDAO.java `
    src/dao/BillDAO.java `
    src/service/PatientService.java `
    src/service/DoctorService.java `
    src/service/AppointmentService.java `
    src/service/BillingService.java `
    src/WebServer.java `
    src/HospitalManagementSystem.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Compilation successful!" -ForegroundColor Green
    
    # Step 3: Run the application
    Write-Host "`n=== Starting Hospital Management System ===" -ForegroundColor Cyan
    Write-Host "Note: Make sure MongoDB is running on localhost:27017" -ForegroundColor Yellow
    Write-Host ""
    
    java -cp ".;lib/*;bin" HospitalManagementSystem
} else {
    Write-Host "✗ Compilation failed! Please check the errors above." -ForegroundColor Red
}
