# JEDI-OMEGA-FLIPFIT-POS-SYSTEM

This repository contains the complete implementation of the **FlipFit Gym Management System**, a comprehensive POS application developed for the JEDI training program. The project is structured into modular components to handle RESTful services, database persistence, and design artifacts.

## 📂 Project Structure

- **`JEDI-FLIPFIT-OMEGA-DROPWIZARD-REST`**: The core REST API project built using the Dropwizard framework.
- **`JEDI-OMEGA-DEVELOPMENT-JAVA-POS-DAO`**: The foundational Data Access Object (DAO) layer for JDBC implementation.
- **`JEDI-FLIPFIT-UML-ARTIFACTS`**: UML diagrams including Class, Sequence, and Use Case diagrams.
- **`JEDI-OMEGA-FLIPFIT-DOCUMENTATION-POS`**: Technical documentation and system requirements specifications.
- **`JEDI-OMEGA-FLIPFIT-PPT.pdf`**: Final presentation deck covering project milestones and architecture.

---

## 🚀 Key Features

### **1. Advanced Booking & Waitlist**
- **Atomic Booking:** Prevents race conditions during slot reservations.
- **Auto-Promotion:** When a confirmed booking is cancelled, the system automatically promotes the first user in the waitlist without releasing the seat to the public.
- **Conflict Detection:** Prevents a single user from booking overlapping gym sessions.

### **2. Admin & Owner Dashboard**
- **Center Management:** Admins can approve or reject new gym centers.
- **Slot Scheduling:** Owners can dynamically manage gym timings and capacity.

---

## 🛠️ Tech Stack

- **Backend:** Java 17, Dropwizard (Jersey + Jackson)
- **Database:** MySQL 8.0
- **Persistence:** JDBC
- **Build Tool:** Maven
- **API Testing:** Postman

---

## ⚙️ Quick Start

### 1. Database Setup
Execute the SQL scripts found in the `POS-DAO` folder to initialize your schema.
```sql
CREATE DATABASE flipfit_db;
-- Import the provided SQL dump to create tables: 
-- users, gym_center, slot, booking, waitlist
```

### 2. Database Configuration for JDBC Connection
```database:
  driverClass: com.mysql.cj.jdbc.Driver
  user: root                # Update with your MySQL username
  password: yourpassword    # Update with your MySQL password
  url: jdbc:mysql://localhost:3306/flipfit_db?useSSL=false&serverTimezone=UTC
```
  

### 3. Build and Run
Clean and build the JAR
```mvn clean install```

Start the server
```java -jar target/JEDI-FLIPFIT-OMEGA-DROPWIZARD-REST-1.0-SNAPSHOT.jar server config.yml```

---

## 📊 System Flow: Cancellation & Promotion

The system ensures data integrity and high availability during the cancellation process through an automated reactive chain:

1. **Identify**: The service layer fetches the `SlotID` and `BookingDate` of the target booking record before deletion to maintain context for promotion.
2. **Remove**: A hard delete is performed on the `booking` table using the `BookingID`.
3. **Queue Check**: The system queries the `waitlist` table for the entry with the lowest `waitlist_id` (FIFO - First In, First Out) matching the same `SlotID` and `Date`.
4. **Promote**: 
   - A new `CONFIRMED` booking is created for the waitlisted user.
   - The corresponding entry is removed from the `waitlist` table.
   - **Note**: The `available_seats` count in the `slots` table remains unchanged as the spot is immediately filled.



---

## 📜 Group Omega - JEDI Participants

- **Project Lead**: Shrashti Jain
- **Modules Covered**: 
  - **REST API**: Developed Dropwizard resources and health checks.
  - **JDBC Persistence**: Implemented DAO patterns and database connection pooling.
  - **POS Documentation**: Authored technical manuals and API catalogs.
  - **UML Artifacts**: Created Sequence and Class diagrams for system architecture.

---
