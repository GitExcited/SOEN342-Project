package main;
public class PrivateLesson extends Lesson {

    // Constructor
    public PrivateLesson(String title, String description, Location loc, TimeSlot timeSlot) {
        super(title, description, loc, timeSlot);
    }

    @Override
    public String toString() {
        return "Private Lesson: " + super.toString();
    }
}