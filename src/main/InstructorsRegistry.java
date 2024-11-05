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
