package main;

public class Offering {
    private Lesson lesson;
    private Location location;
    private Event lessonTime;
    
    // Default constructor
    public Offering() {
    }

    // Parameterized constructor
    public Offering(Lesson lesson, Event lessonTime, Location location) {
        this.lesson = lesson;
        this.lessonTime = lessonTime;
        this.location=location;
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
                ", at time=" + lessonTime +
                '}';
    }
}