package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class GymCustomer.
 *
 * This class represents a customer in the FlipFit system.
 * A GymCustomer extends the {@link User} class and is assigned
 * the CUSTOMER role.
 *
 * Gym customers can browse gym centers, view available slots,
 * and make bookings.
 *
 * @author Shreya
 * @ClassName "GymCustomer"
 */
public class GymCustomer extends User {

    /** The address of the customer */
    private String address;

    /** The city where the customer resides */
    private String city;

    /**
     * Instantiates a new GymCustomer.
     *
     * The role ID is automatically set to 1
     * which represents the CUSTOMER role.
     *
     * @param userId   the unique user ID
     * @param name     the customer's full name
     * @param email    the customer's email address
     * @param phone    the customer's phone number
     * @param password the customer's login password
     * @param address  the customer's address
     * @param city     the customer's city
     */
    public GymCustomer(int userId,
                       String name,
                       String email,
                       String phone,
                       String password,
                       String address,
                       String city) {

        // role_id = 1 → CUSTOMER
        super(userId, name, email, phone, password, 1);
        this.address = address;
        this.city = city;
    }

    /**
     * Gets the customer's address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the customer's city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }
}
