package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// ? Each location has a schedule which is an array of Events where the location is available
public class Schedule {
    private List<TimeSlot> schedule;

    // Constructor
    public Schedule() {
        schedule = new ArrayList<>();
    }

    // Method to add an available time
    public void addTime(String date, String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(date + " " + startTime, formatter);
        LocalDateTime end = LocalDateTime.parse(date + " " + endTime, formatter);
        TimeSlot event = new TimeSlot(start, end);
        schedule.add(event);
    }

    // Method to display the schedule
    public void displaySchedule() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (TimeSlot event : schedule) {
            System.out.println("Start: " + event.getStart().format(formatter) + ", End: " + event.getEnd().format(formatter));
        }
    }

// Method to check for time conflicts. 
public boolean hasConflict(TimeSlot newEvent) {
    for (TimeSlot existingEvent : schedule) {
        //Returns true if conflict is found
        if (!newEvent.getEnd().isBefore(existingEvent.getStart()) && !newEvent.getStart().isAfter(existingEvent.getEnd())) {
            return true;
        }
    }
    return false;
}
    
}