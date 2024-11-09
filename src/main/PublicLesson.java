package main;

public class PublicLesson extends Lesson {

    // Constructor
    public PublicLesson(String title, String description, Location loc, TimeSlot timeSlot) {
        super( title, description, loc, timeSlot);
    }

    @Override
    public String toString() {
        return "Public Lesson: " + super.toString();
    }
}