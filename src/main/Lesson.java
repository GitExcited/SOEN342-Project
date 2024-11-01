package main;

public class Lesson {
    // Class attributes
    private String title;
    private String description;
    private int duration; // Duration in minutes. For testing purposes all lessons are 60 MIN
    private Location location;
    // Constructor
    public Lesson(String title, String description, int duration, Location loc) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.location = loc;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Location getLocation(){
        return location;
    }
    public void setLocation(Location loc){
        this.location = loc;
    }

    // Method to display lesson details
    public void displayLessonDetails() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("At Location"+location);
    }

}