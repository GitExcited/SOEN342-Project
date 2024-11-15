package main;

import java.util.UUID;

public class Offering {
    private String id;
    private Lesson lesson;
    private Instructor instructor;
    private boolean booked;
    
    // Default constructor
    public Offering() {
    }

    // Parameterized constructor
    public Offering(Lesson lesson, Instructor instructor) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.lesson = lesson;
        this.instructor = instructor;
        this.booked = false;
    }

    // Getters and setters
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public boolean getBooked() {
        return booked;
    }

    public void setBooked(boolean booked){
        if (this.lesson instanceof PrivateLesson){
            this.booked = booked;
        }
    }
    
    // toString method
    @Override
    public String toString() {
        return "Offering{" +
                "lesson =" + lesson +
                "instructor =" + instructor +
                '}';
    }

    /**
     * Helper method to turn Offering into paramters that can be passed to its TDG 
     * @param offering
     * @return Object array with each of its attributes in order : id, lesson_id, instructor_id, booked boolean
     */
    public Object[] toParams() {
        return new Object[] {
            this.id,
            this.lesson.getID(),
            this.instructor.getID(),
            this.booked
        };
    }
}