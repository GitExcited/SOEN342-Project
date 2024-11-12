package main;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Each location has a schedule which is an array of Events where the location is available
public class Schedule {
    private List<TimeSlot> schedule;

    // Constructor
    public Schedule() {
        schedule = new ArrayList<>();
    }

    // Method to display the schedule
    public void displaySchedule() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (TimeSlot event : schedule) {
            System.out.println("Start: " + event.getStart().format(formatter) + ", End: " + event.getEnd().format(formatter));
        }
    }

    // Method to check for time conflicts
    public boolean hasConflict(TimeSlot newEvent) {
        for (TimeSlot existingEvent : schedule) {
            // Returns true if conflict is found
            if (newEvent.getDayOfWeek().equals(existingEvent.getDayOfWeek())) {
                if (!newEvent.getEnd().isBefore(existingEvent.getStart()) && !newEvent.getStart().isAfter(existingEvent.getEnd())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Method to convert Schedule to JSON string
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < schedule.size(); i++) {
            json.append(schedule.get(i).toJson());
            if (i < schedule.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    // Method to add a TimeSlot
    public void addTimeSlot(TimeSlot timeSlot) {
        schedule.add(timeSlot);
    }

    // Method to delete a TimeSlot by index
    public void deleteTimeSlot(int index) {
        if (index >= 0 && index < schedule.size()) {
            schedule.remove(index);
        } else {
            System.out.println("Invalid index");
        }
    }

    // Method to view all TimeSlots
    public List<TimeSlot> viewTimeSlots() {
        return new ArrayList<>(schedule);
    }

    // Method to update a TimeSlot by index
    public void updateTimeSlot(int index, TimeSlot newTimeSlot) {
        if (index >= 0 && index < schedule.size()) {
            schedule.set(index, newTimeSlot);
        } else {
            System.out.println("Invalid index");
        }
    }
}