package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tdg.LessonTDG;

public class LessonsRegistry {
    private List<Lesson> lessonsCollection= new ArrayList<>();;
    private LessonTDG lessonTDG;

    // Constructor
    public LessonsRegistry() {
        try {
            this.lessonTDG = new LessonTDG();
            lessonTDG.createTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } 
    }

    public void createLesson(Lesson lesson) {
        lessonsCollection.add(lesson);
        try {
            lessonTDG.insert(lesson.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            lessonTDG.delete(lesson.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /***
     * Updates the lesson to match the parameters of updatedLesson while keeping the same ID 
     * @param lessonId the lesson to update. The resulting lesson has the same id
     * @param updatedLesson the lesson object with the updated values, possibly having some values not updated
     */
    public void updateLesson( int lessonId, Lesson updatedLesson) {

        Lesson oldLesson = lessonsCollection.get(lessonId);
        //! WHEN updating a lesson, we keep the same id for foreign key purposes
        updatedLesson.setID(oldLesson.getID());
        lessonsCollection.set(lessonId, updatedLesson);
                
        try {
            lessonTDG.update(updatedLesson.toParams());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
