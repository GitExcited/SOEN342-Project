package main;

import java.util.UUID;

public class Offering {
    private String id;
    private Lesson lesson;
    private Location location;
    private TimeSlot timeSlot;
    
    // Default constructor
    public Offering() {
    }

    // Parameterized constructor
    public Offering(Lesson lesson, TimeSlot timeSlot) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.lesson = lesson;
        this.timeSlot = timeSlot;
    }

    public Offering(Lesson lesson, TimeSlot timeSlot, Location location) {
        this.lesson = lesson;
        this.timeSlot = timeSlot;
        this.location=location;
    }

    // Getters and setters
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    // toString method
    @Override
    public String toString() {
        return "Offering{" +
                "lesson=" + lesson +
                ", at time=" + timeSlot +
                '}';
    }
}