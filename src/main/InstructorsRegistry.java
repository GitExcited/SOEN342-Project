package main;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tdg.InstructorTDG;

public class InstructorsRegistry {
    private List<Instructor> instructorsCollection = new ArrayList<>();
    private InstructorTDG instructorTDG;

    // Constructor
    public InstructorsRegistry() {
        try {
            this.instructorTDG = new InstructorTDG();
            instructorTDG.createTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createInstructor(Instructor instructor) {
        instructorsCollection.add(instructor);
        try {
            instructorTDG.insert(instructor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAllInstructorsDescriptions() {
        StringBuilder sb = new StringBuilder();
        for (Instructor i : instructorsCollection) {
            sb.append(i.toString()).append("\n");
        }
        return sb.toString();
    }

    public Instructor getInstructorById(String id) {
        Instructor instructor = null;
        for (Instructor instructor2 : instructorsCollection) {
            if (instructor2.getID().trim().equals(id.trim())) {
                instructor = instructor2;
                break;
            }
        }
        return instructor;
    }

    public Instructor getInstructorbyName(String name){
        for (Instructor instructor : instructorsCollection) {
            if (instructor.getName().trim().equals(name.trim())){
                return instructor;
            }
        }
        return null;
    }


    public boolean removeInstructor(Instructor instructor) {
        instructorsCollection.remove(instructor);
        try {
            instructorTDG.delete(instructor.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Instructor getInstructorbyPhoneNumber(String phoneNumber){
        for (Instructor instructor : instructorsCollection) {
            if (instructor.getPhoneNumber() == phoneNumber){
                return instructor;
            }
        }
        return null;
    }


    public void updateInstructor(int instructorId, Instructor updatedInstructor) {
        Instructor oldInstructor = instructorsCollection.get(instructorId);
        updatedInstructor.setID(oldInstructor.getID());
        instructorsCollection.set(instructorId, updatedInstructor);

        try {
            instructorTDG.update(updatedInstructor.toParams());
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
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
        for (Instructor instructor : instructorsCollection) {
            sb.append(instructor.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
