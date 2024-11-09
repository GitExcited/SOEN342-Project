package main;

import java.util.UUID;

public class Lesson {
    // Class attributes
    private String id;
    private String title;
    private String description;
    private Location location;
    private TimeSlot timeSlot;
    // Constructor
    public Lesson( String title, String description, Location loc, TimeSlot timeSlot) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.title = title;
        this.description = description;
        this.location = loc;
        this.timeSlot = timeSlot;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Location getLocation(){
        return location;
    }
    public void setLocation(Location loc){
        this.location = loc;
    }

    public TimeSlot getTimeSlot(){
        return timeSlot;
    }
    public void setTimeSlot(TimeSlot timeSlot){
        this.timeSlot = timeSlot;
    } 

    // Method to display lesson details
    public void displayLessonDetails() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("At Location"+location);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}