package com.flipfit.constants;

public class SQLConstants {

    // --- USER QUERIES ---
    public static final String USER_REGISTER =
            "INSERT INTO User (userId, name, email, contact, password, role, identityNo, isApproved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String USER_AUTHENTICATE =
            "SELECT * FROM User WHERE email = ? AND password = ?";

    public static final String USER_UPDATE_PASSWORD =
            "UPDATE User SET password = ? WHERE email = ?";

    public static final String USER_GET_PENDING_OWNERS =
            "SELECT * FROM User WHERE role = 'OWNER' AND isApproved = false";

    public static final String USER_APPROVE_OWNER =
            "UPDATE User SET isApproved = true WHERE email = ? AND role = 'OWNER'";

    // --- GYM CENTER QUERIES ---
    public static final String GYM_CENTRE_ADD =
            "INSERT INTO GymCenter (centerId, centerName, location, city, pincode, gstNo, ownerEmail, isApproved) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String GYM_CENTRE_GET_ALL_PENDING =
            "SELECT * FROM GymCenter WHERE isApproved = false";

    public static final String GYM_CENTRE_APPROVE =
            "UPDATE GymCenter SET isApproved = true WHERE centerId = ?";

    public static final String GYM_CENTRE_GET_BY_OWNER =
            "SELECT * FROM GymCenter WHERE ownerEmail = ?";

    public static final String GET_ALL_GYM_OWNERS =
            "SELECT * FROM User WHERE role = 'OWNER'";

    public static final String GYM_CENTRE_GET_BY_CITY =
            "SELECT * FROM GymCenter WHERE city = ? AND isApproved = true";

    public static final String GYM_CENTRE_GET_BY_ID =
            "SELECT centerName FROM GymCenter WHERE centerId = ?";

    public static final String Delete_GYM_CENTR_BY_ID =
            "DELETE FROM GymCenter WHERE centerId = ?";

    public static final String CHECK_APPROVE_STATUS =
            "SELECT isApproved FROM GymCenter WHERE centerId = ?";

    // --- SLOT QUERIES ---
    public static final String SLOT_ADD =
            "INSERT INTO Slot (slotId, centerId, startTime, endTime, slotDate, capacity) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SLOT_GET_BY_CENTER_AND_DATE =
            "SELECT * FROM Slot WHERE centerId = ? AND slotDate = ?";

    public static final String SLOT_UPDATE_CAPACITY =
            "UPDATE Slot SET capacity = ? WHERE slotId = ?";
    public static final String SLOT_GET_BY_ID =
            "SELECT * FROM Slot WHERE slotId = ?";

    // --- BOOKING QUERIES ---
    public static final String BOOKING_CREATE =
            "INSERT INTO Booking (bookingId, slotId, customerEmail, status) VALUES (?, ?, ?, ?)";

    public static final String BOOKING_GET_BY_CUSTOMER =
            "SELECT b.bookingId, b.slotId, b.status, s.slotDate, s.startTime, s.endTime, g.centerName " +
                    "FROM Booking b " +
                    "JOIN Slot s ON b.slotId = s.slotId " +
                    "JOIN GymCenter g ON s.centerId = g.centerId " +
                    "WHERE b.customerEmail = ?";

    public static final String BOOKING_CANCEL =
            "UPDATE Booking SET status = 'CANCELLED' WHERE bookingId = ?";

    public static final String USER_DELETE_BY_EMAIL =
            "DELETE FROM User WHERE email = ? AND role = 'OWNER'";

}