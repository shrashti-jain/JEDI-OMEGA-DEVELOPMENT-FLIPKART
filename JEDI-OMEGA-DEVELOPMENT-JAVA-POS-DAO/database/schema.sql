-- ============================================
-- FlipFit Database Schema (Simplified)
-- MySQL Database Setup Script
-- Removed: membership, business_license, admin_level, trainers table
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
-- Additional information for gym customers (membership removed)
-- ============================================
CREATE TABLE gym_customers (
    customer_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    join_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================
-- Table: gym_owners
-- Additional information for gym owners (business_license removed)
-- ============================================
CREATE TABLE gym_owners (
    owner_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ============================================
-- Table: admins
-- Additional information for administrators (admin_level removed)
-- ============================================
CREATE TABLE admins (
    admin_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
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
    FOREIGN KEY (owner_id) REFERENCES gym_owners(owner_id) ON DELETE CASCADE,
    INDEX idx_city (city),
    INDEX idx_status (status)
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
    UNIQUE KEY unique_slot (center_id, slot_date, start_time, end_time),
    INDEX idx_date (slot_date),
    INDEX idx_center_date (center_id, slot_date)
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
    FOREIGN KEY (slot_id) REFERENCES slots(slot_id) ON DELETE CASCADE,
    INDEX idx_user_email (user_email),
    INDEX idx_slot_date (slot_date)
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
-- Notifications for users
-- ============================================
CREATE TABLE notifications (
    notification_id VARCHAR(50) PRIMARY KEY,
    user_email VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    notification_type ENUM('BOOKING', 'CANCELLATION', 'REMINDER', 'GENERAL') DEFAULT 'GENERAL',
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_email) REFERENCES users(email) ON DELETE CASCADE
);

-- ============================================
-- Insert Sample Data
-- ============================================

-- Insert Users
INSERT INTO users (user_id, name, email, password, phone, role) VALUES
('U001', 'Admin User', 'admin@flipfit.com', 'admin123', '9999999999', 'ADMIN'),
('U002', 'John Doe', 'john@owner.com', 'owner123', '9876543210', 'OWNER'),
('U003', 'Alice Brown', 'alice@customer.com', 'customer123', '9123456780', 'CUSTOMER');

-- Insert Admin
INSERT INTO admins (admin_id, user_id) VALUES
('A001', 'U001');

-- Insert Gym Owner
INSERT INTO gym_owners (owner_id, user_id, verified) VALUES
('O001', 'U002', TRUE);

-- Insert Gym Customer
INSERT INTO gym_customers (customer_id, user_id, join_date) VALUES
('C001', 'U003', '2026-01-15');

-- Insert Gym Centers
INSERT INTO gym_centers (center_id, owner_id, center_name, address, city, location, status) VALUES
('GYM001', 'O001', 'Fitness Pro Delhi', '123 Main Street', 'Delhi', 'Connaught Place', 'APPROVED'),
('GYM002', 'O001', 'Power Gym Mumbai', '456 Marine Drive', 'Mumbai', 'South Mumbai', 'APPROVED'),
('GYM003', 'O001', 'FlexFit Bangalore', '789 MG Road', 'Bangalore', 'Indiranagar', 'APPROVED');

-- Insert Slots for Gym Centers (today's date: 2026-01-27)
-- Fitness Pro Delhi Slots
INSERT INTO slots (slot_id, center_id, start_time, end_time, capacity, available_seats, slot_date) VALUES
('SLOT001', 'GYM001', '06:00:00', '07:00:00', 20, 20, '2026-01-27'),
('SLOT002', 'GYM001', '07:00:00', '08:00:00', 20, 20, '2026-01-27'),
('SLOT003', 'GYM001', '08:00:00', '09:00:00', 20, 20, '2026-01-27'),
('SLOT004', 'GYM001', '09:00:00', '10:00:00', 20, 15, '2026-01-27'),
('SLOT005', 'GYM001', '10:00:00', '11:00:00', 20, 20, '2026-01-27'),
('SLOT006', 'GYM001', '17:00:00', '18:00:00', 25, 25, '2026-01-27'),
('SLOT007', 'GYM001', '18:00:00', '19:00:00', 25, 25, '2026-01-27'),
('SLOT008', 'GYM001', '19:00:00', '20:00:00', 25, 25, '2026-01-27');

-- Power Gym Mumbai Slots
INSERT INTO slots (slot_id, center_id, start_time, end_time, capacity, available_seats, slot_date) VALUES
('SLOT009', 'GYM002', '06:00:00', '07:00:00', 15, 15, '2026-01-27'),
('SLOT010', 'GYM002', '07:00:00', '08:00:00', 15, 15, '2026-01-27'),
('SLOT011', 'GYM002', '18:00:00', '19:00:00', 20, 20, '2026-01-27'),
('SLOT012', 'GYM002', '19:00:00', '20:00:00', 20, 20, '2026-01-27');

-- FlexFit Bangalore Slots
INSERT INTO slots (slot_id, center_id, start_time, end_time, capacity, available_seats, slot_date) VALUES
('SLOT013', 'GYM003', '06:00:00', '07:00:00', 18, 18, '2026-01-27'),
('SLOT014', 'GYM003', '08:00:00', '09:00:00', 18, 18, '2026-01-27'),
('SLOT015', 'GYM003', '17:00:00', '18:00:00', 22, 22, '2026-01-27'),
('SLOT016', 'GYM003', '19:00:00', '20:00:00', 22, 22, '2026-01-27');

-- ============================================
-- Database Setup Complete
-- ============================================

SELECT 'Database flipfit_db created successfully!' AS Status;
SELECT COUNT(*) AS 'Total Tables' FROM information_schema.tables WHERE table_schema = 'flipfit_db';
