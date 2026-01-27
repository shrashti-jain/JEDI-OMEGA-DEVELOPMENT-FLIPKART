-- ============================================
-- FlipFit Database Schema
-- MySQL Database Setup Script
-- ============================================

-- Create Database
DROP DATABASE IF EXISTS flipfit_db;
CREATE DATABASE flipfit_db;
USE flipfit_db;

-- ============================================
-- Table: users
-- Stores basic user information for all user types
-- ============================================
CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    role ENUM('CUSTOMER', 'OWNER', 'ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- Table: gym_customers
-- Additional information for gym customers
-- ============================================
CREATE TABLE gym_customers (
    customer_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    membership_type VARCHAR(50),
    join_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================
-- Table: gym_owners
-- Additional information for gym owners
-- ============================================
CREATE TABLE gym_owners (
    owner_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    business_license VARCHAR(100),
    verified BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================
-- Table: admins
-- Additional information for administrators
-- ============================================
CREATE TABLE admins (
    admin_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    admin_level INT DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================
-- Table: gym_centers
-- Information about gym centers/locations
-- ============================================
CREATE TABLE gym_centers (
    center_id VARCHAR(50) PRIMARY KEY,
    owner_id VARCHAR(50) NOT NULL,
    center_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    location VARCHAR(100),
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES gym_owners(owner_id) ON DELETE CASCADE
);

-- ============================================
-- Table: trainers
-- Information about trainers at gym centers
-- ============================================
CREATE TABLE trainers (
    trainer_id VARCHAR(50) PRIMARY KEY,
    center_id VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    experience_years INT,
    contact VARCHAR(15),
    FOREIGN KEY (center_id) REFERENCES gym_centers(center_id) ON DELETE CASCADE
);

-- ============================================
-- Table: slots
-- Time slots available at gym centers
-- ============================================
CREATE TABLE slots (
    slot_id VARCHAR(50) PRIMARY KEY,
    center_id VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT NOT NULL DEFAULT 10,
    available_seats INT NOT NULL DEFAULT 10,
    slot_date DATE NOT NULL,
    FOREIGN KEY (center_id) REFERENCES gym_centers(center_id) ON DELETE CASCADE,
    UNIQUE KEY unique_slot (center_id, slot_date, start_time, end_time)
);

-- ============================================
-- Table: bookings
-- Customer bookings for gym slots
-- ============================================
CREATE TABLE bookings (
    booking_id VARCHAR(50) PRIMARY KEY,
    user_email VARCHAR(100) NOT NULL,
    center_id VARCHAR(50) NOT NULL,
    slot_id VARCHAR(50) NOT NULL,
    gym_name VARCHAR(100) NOT NULL,
    slot_date DATE NOT NULL,
    slot_time VARCHAR(50) NOT NULL,
    status ENUM('CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'CONFIRMED',
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_email) REFERENCES users(email) ON DELETE CASCADE,
    FOREIGN KEY (center_id) REFERENCES gym_centers(center_id) ON DELETE CASCADE,
    FOREIGN KEY (slot_id) REFERENCES slots(slot_id) ON DELETE CASCADE
);

-- ============================================
-- Table: waitlist
-- Waitlist for fully booked slots
-- ============================================
CREATE TABLE waitlist (
    waitlist_id VARCHAR(50) PRIMARY KEY,
    customer_id VARCHAR(50) NOT NULL,
    slot_id VARCHAR(50) NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('WAITING', 'NOTIFIED', 'EXPIRED') DEFAULT 'WAITING',
    FOREIGN KEY (customer_id) REFERENCES gym_customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (slot_id) REFERENCES slots(slot_id) ON DELETE CASCADE
);

-- ============================================
-- Table: payments
-- Payment information for bookings
-- ============================================
CREATE TABLE payments (
    payment_id VARCHAR(50) PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_mode VARCHAR(50) NOT NULL,
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
);

-- ============================================
-- Table: notifications
-- Notifications sent to users
-- ============================================
CREATE TABLE notifications (
    notification_id VARCHAR(50) PRIMARY KEY,
    user_email VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_email) REFERENCES users(email) ON DELETE CASCADE
);

-- ============================================
-- Create Indexes for better performance
-- ============================================
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_role ON users(role);
CREATE INDEX idx_gym_center_city ON gym_centers(city);
CREATE INDEX idx_gym_center_status ON gym_centers(status);
CREATE INDEX idx_slot_date ON slots(slot_date);
CREATE INDEX idx_booking_user ON bookings(user_email);
CREATE INDEX idx_booking_status ON bookings(status);
CREATE INDEX idx_booking_date ON bookings(slot_date);

-- ============================================
-- Insert Sample Data
-- ============================================

-- Insert Admin User
INSERT INTO users (user_id, name, email, password, phone, role) 
VALUES ('ADMIN001', 'Admin User', 'admin@flipfit.com', 'admin123', '9999999999', 'ADMIN');

INSERT INTO admins (admin_id, user_id, admin_level) 
VALUES ('ADM001', 'ADMIN001', 3);

-- Insert Sample Gym Owner
INSERT INTO users (user_id, name, email, password, phone, role) 
VALUES ('USER001', 'John Doe', 'john@owner.com', 'owner123', '9876543210', 'OWNER');

INSERT INTO gym_owners (owner_id, user_id, business_license, verified) 
VALUES ('OWN001', 'USER001', 'LIC123456', TRUE);

-- Insert Sample Gym Centers
INSERT INTO gym_centers (center_id, owner_id, center_name, address, city, location, status) 
VALUES 
('GYM001', 'OWN001', 'Fitness Pro Delhi', '123 Main Street', 'Delhi', 'Connaught Place', 'APPROVED'),
('GYM002', 'OWN001', 'Power Gym Mumbai', '456 Park Avenue', 'Mumbai', 'Andheri', 'APPROVED'),
('GYM003', 'OWN001', 'FlexFit Bangalore', '789 MG Road', 'Bangalore', 'Indiranagar', 'APPROVED');

-- Insert Sample Trainers
INSERT INTO trainers (trainer_id, center_id, name, specialization, experience_years, contact) 
VALUES 
('TRA001', 'GYM001', 'Mike Smith', 'Strength Training', 5, '9876543211'),
('TRA002', 'GYM001', 'Sarah Johnson', 'Yoga & Cardio', 3, '9876543212'),
('TRA003', 'GYM002', 'Raj Kumar', 'CrossFit', 7, '9876543213');

-- Insert Sample Slots for today and tomorrow
INSERT INTO slots (slot_id, center_id, start_time, end_time, capacity, available_seats, slot_date) 
VALUES 
-- GYM001 - Delhi slots for today
('SLOT001', 'GYM001', '06:00:00', '07:00:00', 20, 20, CURDATE()),
('SLOT002', 'GYM001', '07:00:00', '08:00:00', 20, 20, CURDATE()),
('SLOT003', 'GYM001', '08:00:00', '09:00:00', 20, 20, CURDATE()),
('SLOT004', 'GYM001', '09:00:00', '10:00:00', 20, 15, CURDATE()),
('SLOT005', 'GYM001', '10:00:00', '11:00:00', 20, 20, CURDATE()),
('SLOT006', 'GYM001', '17:00:00', '18:00:00', 25, 25, CURDATE()),
('SLOT007', 'GYM001', '18:00:00', '19:00:00', 25, 25, CURDATE()),
('SLOT008', 'GYM001', '19:00:00', '20:00:00', 25, 25, CURDATE()),

-- GYM002 - Mumbai slots for today
('SLOT009', 'GYM002', '06:00:00', '07:00:00', 15, 15, CURDATE()),
('SLOT010', 'GYM002', '07:00:00', '08:00:00', 15, 15, CURDATE()),
('SLOT011', 'GYM002', '09:00:00', '10:00:00', 15, 10, CURDATE()),
('SLOT012', 'GYM002', '10:00:00', '11:00:00', 15, 15, CURDATE()),

-- GYM001 - Delhi slots for tomorrow
('SLOT013', 'GYM001', '06:00:00', '07:00:00', 20, 20, DATE_ADD(CURDATE(), INTERVAL 1 DAY)),
('SLOT014', 'GYM001', '07:00:00', '08:00:00', 20, 20, DATE_ADD(CURDATE(), INTERVAL 1 DAY)),
('SLOT015', 'GYM001', '09:00:00', '10:00:00', 20, 20, DATE_ADD(CURDATE(), INTERVAL 1 DAY)),
('SLOT016', 'GYM001', '10:00:00', '11:00:00', 20, 20, DATE_ADD(CURDATE(), INTERVAL 1 DAY));

-- Insert Sample Customer
INSERT INTO users (user_id, name, email, password, phone, role) 
VALUES ('USER002', 'Alice Brown', 'alice@customer.com', 'customer123', '9876543220', 'CUSTOMER');

INSERT INTO gym_customers (customer_id, user_id, membership_type, join_date) 
VALUES ('CUST001', 'USER002', 'Premium', CURDATE());

-- ============================================
-- Verification Queries
-- ============================================
SELECT '✅ Database schema created successfully!' AS Status;
SELECT 'Total Tables Created:' AS Info, COUNT(*) AS Count FROM information_schema.tables WHERE table_schema = 'flipfit_db';
SELECT 'Total Users:' AS Info, COUNT(*) AS Count FROM users;
SELECT 'Total Gym Centers:' AS Info, COUNT(*) AS Count FROM gym_centers;
SELECT 'Total Slots:' AS Info, COUNT(*) AS Count FROM slots;

-- Show all tables
SHOW TABLES;
