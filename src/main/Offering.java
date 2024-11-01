package main;

public class Offering {
    private Lesson lesson;
    private Event lessonTime;
    
    // Default constructor
    public Offering() {
    }

    // Parameterized constructor
    public Offering(Lesson lesson, Event lessonTime) {
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
    

    // toString method
    @Override
    public String toString() {
        return "Offering{" +
                "lesson=" + lesson +
                ", at time=" + lessonTime +
                '}';
    }
}