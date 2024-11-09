package main;

import java.util.UUID;

public class Offering {
    private String id;
    private Lesson lesson;
    private Instructor instructor;
    
    // Default constructor
    public Offering() {
    }

    // Parameterized constructor
    public Offering(Lesson lesson, Instructor instructor) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.lesson = lesson;
        this.instructor = instructor;
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

    // toString method
    @Override
    public String toString() {
        return "Offering{" +
                "lesson=" + lesson +
                '}';
    }
}