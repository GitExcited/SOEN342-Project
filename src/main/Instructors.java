package main;

import java.util.ArrayList;
import java.util.List;

public class Instructors {
    private List<Instructor> instructors;

    // Constructor
    public Instructors() {
        this.instructors = new ArrayList<>();
    }

    /**
     * Adds a new instructor to the collection.
     * 
     * @param instructor The instructor to be added.
     */
    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    /**
     * Removes a instructor from the collection.
     * 
     * @param instructor The instructor to be removed.
     */
    public void removeInstructor(Instructor instructor) {
        instructors.remove(instructor);
    }

    /**
     * Retrieves the list of instructors.
     * 
     * @return The list of instructors.
     */
    public List<Instructor> getInstructors() {
        return instructors;
    }

    public Instructor getInstructorbyUsername(String username){
        for (Instructor instructor : instructors) {
            if (instructor.getName() == username){
                return instructor;
            }
        }
        return null;
    }

    public Instructor getInstructorbyPhoneNumber(String phoneNumber){
        for (Instructor instructor : instructors) {
            if (instructor.getPhoneNumber() == phoneNumber){
                return instructor;
            }
        }
        return null;
    }
}
