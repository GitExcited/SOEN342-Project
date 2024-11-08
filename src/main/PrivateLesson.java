package main;
public class PrivateLesson extends Lesson {

    // Constructor
    public PrivateLesson(String id, String title, String description) {
        super(id, title, description);
    }

    @Override
    public String toString() {
        return "Private Lesson: " + super.toString();
    }
}