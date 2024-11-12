package main;

import java.util.ArrayList;
import java.util.List;

public class LessonsRegistry {
    private List<Lesson> lessonsCollection;

    // Constructor
    public LessonsRegistry() {
        this.lessonsCollection = new ArrayList<>();
    }

    public void createLesson(Lesson lesson) {
        lessonsCollection.add(lesson);
    }

    public boolean checkTimeCollision(Lesson lesson) {
        for (Lesson l: lessonsCollection){
            if( l.getLocation().equals(lesson.getLocation()) 
                &&
                l.getTimeSlot().collides(lesson.getTimeSlot())){
                return true;
            }
        }
        return false;
    }

    public String getAllLessonsDescriptions() {
        StringBuilder sb = new StringBuilder();
        for (Lesson l : lessonsCollection) {
            //sb.append("LESSON NUMBER ").append(l.getID()).append("\n");
            sb.append(l.toString()).append("\n");
        }
        return sb.toString();
    }

    public Lesson getLessonById(String id){
        Lesson lesson = null;
        for (Lesson lesson2 : lessonsCollection) {
            if(lesson2.getID().trim().equals(id.trim())){
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
