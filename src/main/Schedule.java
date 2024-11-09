package main;

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
        if (newEvent.getdayOfWeek() == existingEvent.getdayOfWeek()){
            if (!newEvent.getEnd().isBefore(existingEvent.getStart()) && !newEvent.getStart().isAfter(existingEvent.getEnd())) {
                return true;
            }
        }
    }
    return false;
}
    
}