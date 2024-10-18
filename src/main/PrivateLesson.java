package main;
public class PrivateLesson extends Lesson {

    // Constructor
    public PrivateLesson(String title, String description, int duration) {
        super(title, description, duration);
    }

    @Override
    public String toString() {
        return "Private Lesson: " + super.toString();
    }
}