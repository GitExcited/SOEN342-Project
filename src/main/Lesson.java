package main;

import java.util.UUID;

public abstract class Lesson {
    // Class attributes
    protected String id;
    protected String title;
    protected String description;
    protected Location location;
    protected TimeSlot timeSlot;
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


            /**
     * Helper method to turn Lesson into paramters that can be passed to its TDG 
     * @param offering
     * @return Object array with each of its attributes in order : 
     * id, title, description, locatin id and timeslot as a json 
     */
    public Object[] toParams() {
        return new Object[] {
            this.id,
            this.title,
            this.description,
            this.location.getID(),
            this.timeSlot != null ? this.timeSlot.toJson() : null
        };
    }
    @Override
    public String toString() {
        return "Lesson{" +
                "Lessonid= '" + id + '\'' +
                ", title= '" + title + '\'' +
                ", description= '" + description + '\'' +
                ", location= '" + location + '\'' +
                ", timeslot= '" + timeSlot + '\'' +
                '}';
    }

}