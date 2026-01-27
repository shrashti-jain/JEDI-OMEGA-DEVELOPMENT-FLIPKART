package com.flipfit.bean;

import java.time.LocalTime;

public class Schedule {
        private String scheduleId;
        private String slotId;
        private LocalTime startTime; // e.g., 08:00
        private LocalTime endTime;   // e.g., 09:00
        private int availability;

        public String getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
        }

        public String getSlotId() {
            return slotId;
        }

        public void setSlotId(String slotId) {
            this.slotId = slotId;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public int getAvailability() {
            return availability;
        }

        public void setAvailability(int availability) {
            this.availability = availability;
        }

        public Schedule(String scheduleId, String slotId, LocalTime startTime, LocalTime endTime, int availability) {
            this.scheduleId = scheduleId;
            this.slotId = slotId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.availability = availability;
        }

        // You can still provide a helper method to print it nicely
        public String getTimeSlotFormatted() {
            return startTime + " to " + endTime;
        }
}
