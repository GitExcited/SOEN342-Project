package main;
public class PrivateLesson extends Lesson {

    // Constructor
    public PrivateLesson(String title, String description, int duration, Location location) {
        super(title, description, duration,location);
    }

    @Override
    public String toString() {
        return "Private Lesson: " + super.toString();
    }
}