package main;

public class Offering {
    private Lesson lesson;
    private Location location;
    private TimeSlot timeSlot;
    
    // Default constructor
    public Offering() {
    }

    // Parameterized constructor
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

    // toString method
    @Override
    public String toString() {
        return "Offering{" +
                "lesson=" + lesson +
                ", at time=" + timeSlot +
                '}';
    }
}