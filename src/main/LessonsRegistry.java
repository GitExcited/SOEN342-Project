package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import tdg.LessonTDG;

public class LessonsRegistry {
    private List<Lesson> lessonsCollection= new ArrayList<>();;
    private LessonTDG lessonTDG;

    //* Constructor
    public LessonsRegistry() {
        try {
            this.lessonTDG = new LessonTDG();
            lessonTDG.createTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } 
    }

    public void initializeLesson(Lesson lesson) {
        if (!lessonsCollection.contains(lesson)) {
            lessonsCollection.add(lesson);
            System.out.println("I added a location ");
        }
    }

    //* CREATE, UPDATE and DELETE Operations

    public void createLesson(Lesson lesson) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        lessonsCollection.add(lesson);
        try {
            lessonTDG.insert(lesson.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }
    public void removeLesson(Lesson lesson) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        try {
            lessonsCollection.remove(lesson);
            lessonTDG.delete(lesson.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //? Unlock
            writeLock.unlock();
        }
    }

    /***
     * Updates the lesson to match the parameters of updatedLesson while keeping the same ID 
     * @param lessonId the lesson to update. The resulting lesson has the same id
     * @param updatedLesson the lesson object with the updated values, possibly having some values not updated
     */
    public void updateLesson( int lessonId, Lesson updatedLesson) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();
 
        try {
            Lesson oldLesson = lessonsCollection.get(lessonId);
            //! WHEN updating a lesson, we keep the same id for foreign key purposes
            updatedLesson.setID(oldLesson.getID());
            lessonsCollection.set(lessonId, updatedLesson);
            lessonTDG.update(updatedLesson.toParams());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //? Unlock
            writeLock.unlock();
        }
    }

    //* READ Operations */

    public boolean checkTimeCollision(Lesson lesson) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        try{
            for (Lesson l: lessonsCollection){
                if( l.getLocation().equals(lesson.getLocation()) 
                    &&
                    l.getTimeSlot().collides(lesson.getTimeSlot())){
                    return true;
                }
            }
            return false;

        }finally{
            //? Unlock
            writeLock.unlock();
        }
    }

    public String getAllLessonsDescriptions() {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        try{
            StringBuilder sb = new StringBuilder();
        for (Lesson l : lessonsCollection) {
            //sb.append("LESSON NUMBER ").append(l.getID()).append("\n");
            sb.append(l.toString()).append("\n");
        }
        return sb.toString();

        }finally{
            //? Unlock
            writeLock.unlock();
        }
    }

    // Method to print all lessons
    public void printAllLessons() {
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();
        try {
            for (Lesson lesson : lessonsCollection) {
                System.out.println(lesson);
            }
        } finally {
            readLock.unlock();
        }
    }

    public Lesson getLessonById(String id){
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();
        try{
            Lesson lesson = null;
            for (Lesson lesson2 : lessonsCollection) {
                if(lesson2.getID().trim().equals(id.trim())){
                    lesson = lesson2;
                    break;
                }
            }
            return lesson;
        }finally{
            //? Unlock
            writeLock.unlock();
        }
    }




}
