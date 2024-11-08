package main;

public class PublicLesson extends Lesson {

    // Constructor
    public PublicLesson(String id ,String title, String description) {
        super(id, title, description);
    }

    @Override
    public String toString() {
        return "Public Lesson: " + super.toString();
    }
}