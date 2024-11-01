package main;

public class PublicLesson extends Lesson {

    // Constructor
    public PublicLesson(String title, String description, int duration, Location location) {
        super(title, description, duration,location);
    }

    @Override
    public String toString() {
        return "Public Lesson: " + super.toString();
    }
}