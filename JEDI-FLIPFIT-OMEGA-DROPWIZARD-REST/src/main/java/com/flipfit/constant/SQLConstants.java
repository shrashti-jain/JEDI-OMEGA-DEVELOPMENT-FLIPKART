package com.flipfit.constant;

public class SQLConstants {

    private SQLConstants() {
        // prevent instantiation
    }

    /* ================= USER TABLE QUERIES ================= */

    public static final String INSERT_USER =
            "INSERT INTO users (username, password, email, phone, role_id, is_active) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String AUTHENTICATE_USER =
            "SELECT u.*, o.approved FROM users u LEFT JOIN gym_owner o ON u.user_id = o.user_id WHERE u.email = ? AND u.password = ?";

    public static final String UPDATE_PASSWORD =
            "UPDATE users SET password = ? WHERE email = ?";

    public static final String DELETE_USER_BY_ID =
            "DELETE FROM users WHERE user_id = ?";

    public static final String GET_ALL_USERS =
            "SELECT user_id, username, email, phone, role_id FROM users";

    /* ================= USER TABLE COLUMNS ================= */

    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String ROLE_ID = "role_id";




    /* ================= ADMIN QUERIES ================= */

    public static final String APPROVE_GYM_OWNER =
            "UPDATE gym_owner SET approved = 1 WHERE user_id = ?";

    public static final String APPROVE_GYM_CENTER =
            "UPDATE gym_center SET approved = 1 WHERE center_id = ?";




    /* ================= GYM OWNER QUERIES ================= */

    public static final String GET_OWNER_ID_BY_USER_ID =
            "SELECT owner_id FROM gym_owner WHERE user_id = ? AND approved = 1";

    public static final String INSERT_GYM_OWNER =
            "INSERT INTO gym_owner (user_id, approved) VALUES (?, 0)";

    public static final String GET_ALL_OWNERS =
            "SELECT * FROM users WHERE role_id = 2";

    /* ================= GYM OWNER COLUMNS ================= */

    public static final String OWNER_ID = "owner_id";

    /* ================= GYM CENTER QUERIES ================= */

    public static final String INSERT_GYM_CENTER =
            "INSERT INTO gym_center (owner_id, name, location, city, pincode, gstNo, approved, capacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String GET_ALL_GYM_CENTER  =
            "SELECT * FROM gym_center";

    public static final String GET_CENTERS_BY_OWNER =
            "SELECT * FROM gym_center WHERE owner_id = ?";

    public static final String APPROVE_GYM_CENTER_BY_ID =
            "UPDATE gym_center SET approved = 1 WHERE center_id = ?";

    /* ================= GYM CENTER COLUMNS ================= */

    public static final String CENTER_ID = "center_id";
    public static final String CENTER_NAME = "name";
    public static final String LOCATION = "location";
    public static final String CAPACITY = "capacity";
    public static final String APPROVED = "approved";
    public static final String OWNER_ID_FK = "owner_id";






    /* ================= SLOT QUERIES ================= */

    public static final String INSERT_SLOT =
            "INSERT INTO slot (center_id, start_time, end_time, capacity, available_seats) VALUES (?, ?, ?, ?, ?)";

    public static final String GET_SLOT_BY_ID =
            "SELECT * FROM slot WHERE slot_id = ?";

    /* ================= SLOT COLUMNS ================= */

    public static final String SLOT_ID = "slot_id";
    public static final String SLOT_CENTER_ID = "center_id";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String SLOT_CAPACITY = "capacity";


    /* ================= BOOKING QUERIES ================= */

    public static final String INSERT_BOOKING =
            "INSERT INTO booking (booking_id, user_id, slot_id, booking_date, status) VALUES (?, ?, ?, ?, ?)";

    public static final String CHECK_DUPLICATE_BOOKING =
            "SELECT COUNT(*) FROM booking " +
                    "WHERE user_id = ? AND slot_id = ? AND booking_date = ? AND status = 'CONFIRMED'";
    // In SQLConstants.java
    public static final String GET_BOOKING_DETAILS_BY_USER_ID =
            "SELECT b.booking_id, b.user_id, b.slot_id, b.booking_date, b.status, " +
                    "s.center_id, s.start_time, s.end_time, gc.name AS gym_name " +
                    "FROM booking b " +
                    "JOIN slot s ON b.slot_id = s.slot_id " +
                    "JOIN gym_center gc ON s.center_id = gc.center_id " +
                    "WHERE b.user_id = ? AND b.status = 'CONFIRMED'";

    public static final String GET_BOOKING_BY_BOOKING_ID =
            "SELECT b.*, s.center_id, s.start_time, s.end_time, gc.name AS gym_name " +
                    "FROM booking b " +
                    "LEFT JOIN slot s ON b.slot_id = s.slot_id " +
                    "LEFT JOIN gym_center gc ON s.center_id = gc.center_id " +
                    "WHERE b.booking_id = ?";

    public static final String GET_BOOKING_CENTER_DETAILS_BY_STATUS =
    "SELECT b.*, s.start_time, s.end_time, b.booking_date, g.name AS centerName " +
            "FROM booking b " +
            "JOIN slot s ON b.slot_id = s.slot_id " +
            "JOIN gym_center g ON s.center_id = g.center_id " +
            "WHERE b.user_id = ? AND b.booking_date = ? AND b.status = 'CONFIRMED'";

    public static final String CANCEL_BOOKING =
            "UPDATE booking SET status = 'CANCELLED' WHERE booking_id = ?";

    /* ================= BOOKING COLUMNS ================= */

    public static final String BOOKING_ID = "booking_id";
    public static final String BOOKING_STATUS = "status";


    /* ================= PAYMENT QUERIES ================= */

    public static final String INSERT_PAYMENT =
            "INSERT INTO payment (payment_id, booking_id, amount, status) VALUES (?, ?, ?, ?)";

    public static final String GET_PAYMENT_BY_BOOKING_ID =
            "SELECT * FROM payment WHERE booking_id = ?";

    /* ================= PAYMENT COLUMNS ================= */

    public static final String PAYMENT_ID = "payment_id";
    public static final String AMOUNT = "amount";
    public static final String STATUS = "status";

    public static final String GET_SLOTS_BY_CENTER_AND_DATE =
            "SELECT * FROM slot WHERE center_id=? AND date=?";

    public static final String DECREASE_AVAILABLE_SEATS =
            "UPDATE slot SET available_seats = available_seats - 1 WHERE slot_id=? AND available_seats > 0";

    public static final String GET_BOOKINGS_BY_USER =
            "SELECT * FROM booking WHERE user_id = ?";

    public static final String FIND_BOOKING_CONFLICT =
            """
            SELECT * FROM booking
            WHERE user_id = (
                SELECT user_id FROM user WHERE email = ?
            )
            AND booking_date = ?
            AND slot_time = ?
            AND status = 'CONFIRMED'
            """;




}
