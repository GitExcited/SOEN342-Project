package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<Event> schedule;

    // Constructor
    public Schedule() {
        schedule = new ArrayList<>();
    }

    // Method to add an available time
    public void addTime(String date, String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(date + " " + startTime, formatter);
        LocalDateTime end = LocalDateTime.parse(date + " " + endTime, formatter);
        Event event = new Event(start, end);
        schedule.add(event);
    }

    // Method to display the schedule
    public void displaySchedule() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Event event : schedule) {
            System.out.println("Start: " + event.getStart().format(formatter) + ", End: " + event.getEnd().format(formatter));
        }
    }

    // Method to check for time conflicts
    public boolean hasConflict(Event newEvent) {
        for (Event existingEvent : schedule) {
            if (newEvent.getStart().isBefore(existingEvent.getEnd()) && newEvent.getEnd().isAfter(existingEvent.getStart())) {
                return true;
            }
        }
        return false;
    }
    
}