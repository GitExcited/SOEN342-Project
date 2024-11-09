package main;

import java.util.ArrayList;
import java.util.List;

public class Lessons {
    private List<Lesson> lessonsCollection;

    // Constructor
    public Lessons() {
        this.lessonsCollection = new ArrayList<>();
    }

    public void createLesson(String title, String description, Location location, TimeSlot timeslot) {
        lessonsCollection.add(new Lesson(title, description, location, timeslot));
    }

    public String getAllLessonsDescriptions() {
        StringBuilder sb = new StringBuilder();
        for (Lesson l : lessonsCollection) {
            sb.append("LESSON NUMBER ").append(l.getID()).append("\n");
            sb.append(l.toString()).append("\n");
        }
        return sb.toString();
    }

    public Lesson getLessonById(String id){
        Lesson lesson = null;
        for (Lesson lesson2 : lessonsCollection) {
            if(lesson2.getID() == id){
                lesson = lesson2;
                break;
            }
        }
        return lesson;
    }

    public void removeLesson(Lesson lesson) {
        lessonsCollection.remove(lesson);
    }

    public void addLesson(Lesson lesson) {
        lessonsCollection.add(lesson);
    }
}