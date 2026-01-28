package com.flipfit.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class Trainer.
 * Represents a gym trainer entity in the FlipFit system.
 * Stores details such as the trainer's specialization, contact information, and unique ID.
 *
 * @author Shreya
 * @ClassName Trainer
 */
public class Trainer {
    private String trainerId;
    private String name;
    private String specialization;
    private String contact;

    /**
     * Gets the trainer id.
     *
     * @return the trainer id
     */
    public String getTrainerId() {
        return trainerId;
    }

    /**
     * Sets the trainer id.
     *
     * @param trainerId the new trainer id
     */
    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the specialization.
     *
     * @return the specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the specialization.
     *
     * @param specialization the new specialization
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Gets the contact.
     *
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the contact.
     *
     * @param contact the new contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
}