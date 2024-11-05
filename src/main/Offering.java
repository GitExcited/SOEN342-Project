package main;

import java.util.UUID;

public class Offering {
    private String id;
    private Lesson lesson;
    private Event lessonTime;

    // Default constructor
    public Offering() {
    }

    // Parameterized constructor
    public Offering(Lesson lesson, Event lessonTime) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.lesson = lesson;
        this.lessonTime = lessonTime;
    }

    // Getters and setters
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Event getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(Event event) {
        this.lessonTime = event;
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
                ", at time=" + lessonTime +
                '}';
    }
}