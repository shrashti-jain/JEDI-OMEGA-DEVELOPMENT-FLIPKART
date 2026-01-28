package com.flipfit.bean;

import java.time.LocalTime;

//TODO: Auto-generated Javadoc
/**
* The Class Schedule.
* Represents a specific scheduling instance for a gym slot.
* It defines the time boundaries (start and end time) and the current availability
* for a specific session.
*
* @author Shreya
* @ClassName Schedule
*/
public class Schedule {
 private String scheduleId;
 private String slotId;
 private LocalTime startTime; // e.g., 08:00
 private LocalTime endTime;   // e.g., 09:00
 private int availability;

 /**
  * Instantiates a new Schedule.
  *
  * @param scheduleId the unique identifier for this schedule
  * @param slotId the identifier of the parent slot
  * @param startTime the start time of the schedule
  * @param endTime the end time of the schedule
  * @param availability the number of available spots
  */
 public Schedule(String scheduleId, String slotId, LocalTime startTime, LocalTime endTime, int availability) {
     this.scheduleId = scheduleId;
     this.slotId = slotId;
     this.startTime = startTime;
     this.endTime = endTime;
     this.availability = availability;
 }

 /**
  * Gets the schedule id.
  *
  * @return the schedule id
  */
 public String getScheduleId() {
     return scheduleId;
 }

 /**
  * Sets the schedule id.
  *
  * @param scheduleId the new schedule id
  */
 public void setScheduleId(String scheduleId) {
     this.scheduleId = scheduleId;
 }

 /**
  * Gets the slot id.
  *
  * @return the slot id
  */
 public String getSlotId() {
     return slotId;
 }

 /**
  * Sets the slot id.
  *
  * @param slotId the new slot id
  */
 public void setSlotId(String slotId) {
     this.slotId = slotId;
 }

 /**
  * Gets the start time.
  *
  * @return the start time
  */
 public LocalTime getStartTime() {
     return startTime;
 }

 /**
  * Sets the start time.
  *
  * @param startTime the new start time
  */
 public void setStartTime(LocalTime startTime) {
     this.startTime = startTime;
 }

 /**
  * Gets the end time.
  *
  * @return the end time
  */
 public LocalTime getEndTime() {
     return endTime;
 }

 /**
  * Sets the end time.
  *
  * @param endTime the new end time
  */
 public void setEndTime(LocalTime endTime) {
     this.endTime = endTime;
 }

 /**
  * Gets the availability.
  *
  * @return the number of available spots
  */
 public int getAvailability() {
     return availability;
 }

 /**
  * Sets the availability.
  *
  * @param availability the new availability count
  */
 public void setAvailability(int availability) {
     this.availability = availability;
 }

 /**
  * Gets the time slot formatted.
  * Helper method to return a readable string representation of the schedule's duration.
  *
  * @return the formatted string (e.g., "08:00 to 09:00")
  */
 public String getTimeSlotFormatted() {
     return startTime + " to " + endTime;
 }
}