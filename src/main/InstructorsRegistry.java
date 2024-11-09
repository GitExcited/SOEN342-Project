package main;

import java.util.ArrayList;
import java.util.List;

public class InstructorsRegistry {
    private List<Instructor> instructors;

    // Constructor
    public InstructorsRegistry() {
        this.instructors = new ArrayList<>();
    }

    /**
     * Adds a new instructor to the registry.
     * 
     * @param instructor The instructor to be added.
     */
    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    /**
     * Deletes an instructor from the registry.
     * 
     * @param instructor The instructor to be deleted.
     * @return true if the instructor was successfully deleted, false otherwise.
     */
    public Instructor deleteInstructor(int instructorId) {
        return instructors.remove(instructorId);
    }

    public Instructor deleteInstructor(Instructor instructor) {
        instructors.remove(instructor);
        return instructor;
    }

    /**
     * Updates an existing instructor in the registry.
     * 
     * @param oldInstructor The instructor to be updated.
     * @param newInstructor The new instructor details.
     * @return true if the instructor was successfully updated, false otherwise.
     */
    public boolean updateInstructor(Instructor oldInstructor, Instructor newInstructor) {
        int index = instructors.indexOf(oldInstructor);
        if (index != -1) {
            instructors.set(index, newInstructor);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the list of instructors.
     * 
     * @return The list of instructors.
     */
    public List<Instructor> getInstructors() {
        return instructors;
    }

    public Instructor getInstructorbyName(String name){
        for (Instructor instructor : instructors) {
            if (instructor.getName().trim().equals(name.trim())){
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

    public String getAllInstructorsDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (Instructor instructor : instructors) {
            description.append(instructor.toString()+ " \n");
        }
        return description.toString();
    }

    public boolean deleteInstructor(String id) {
        Instructor instructorToRemove = null;
        for (Instructor instructor : instructors) {
            if(instructor.getID().trim().equals(id.trim())){
                instructorToRemove = instructor;
                break;
            }
        }
        if (instructorToRemove == null){
            return false;
        }else{
            deleteInstructor(instructorToRemove);
            return true;
        }
    }

    /**
     * Provides a string representation of the list of instructors.
     * 
     * @return A string representation of the list of instructors.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InstructorsRegistry{\n");
        for (Instructor instructor : instructors) {
            sb.append(instructor.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
