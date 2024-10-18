package main;

public class PublicLesson extends Lesson {

    // Constructor
    public PublicLesson(String title, String description, int duration) {
        super(title, description, duration);
    }

    @Override
    public String toString() {
        return "Public Lesson: " + super.toString();
    }
}