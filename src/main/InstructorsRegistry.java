package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import tdg.InstructorTDG;

public class InstructorsRegistry {
    private List<Instructor> instructorsCollection = new ArrayList<>();
    private InstructorTDG instructorTDG;


    //* Constructor
    public InstructorsRegistry() {
        try {
            this.instructorTDG = new InstructorTDG();
            instructorTDG.createTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

       //* INITIALIZE operation */

    /**Only called by AppSystem to bring a object from the Database to the collection
     * @param instructor The object to be initialized from db
     */
    public void initializeInstructor(Instructor instructor){
        if (!instructorsCollection.contains(instructor)) {
            instructorsCollection.add(instructor);
        }
    }

    //* CREATE, UPDATE and DELETE Operations

    public void createInstructor(Instructor instructor) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        instructorsCollection.add(instructor);
        try {
            instructorTDG.insert(instructor.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }
    
    public void updateInstructor(int instructorId, Instructor updatedInstructor) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        Instructor oldInstructor = instructorsCollection.get(instructorId);
        updatedInstructor.setID(oldInstructor.getID());
        instructorsCollection.set(instructorId, updatedInstructor);
        instructorTDG.update(updatedInstructor.toParams());

        //? Unlock 
        writeLock.unlock();
    }

    public boolean deleteInstructor(Instructor instructor) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        instructorsCollection.remove(instructor);
        try {
            instructorTDG.delete(instructor.getID());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            //? Unlock
            writeLock.unlock();
        }
    }

    public boolean deleteInstructor(String id) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        try{
            Instructor instructorToRemove = null;
            for (Instructor i : instructorsCollection) {
                if(i.getID() == id){
                    instructorToRemove = i;
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
        finally {
            //? Unlock
            writeLock.unlock();
        }
    }


    //* READ operations

    public Instructor getInstructorById(String id) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
            Instructor instructor = null;
            for (Instructor instructor2 : instructorsCollection) {
                if (instructor2.getID().trim().equals(id.trim())) {
                    instructor = instructor2;
                    break;
                }
            }
            return instructor;
        } finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public Instructor getInstructorbyName(String name){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();
        try{
            for (Instructor instructor : instructorsCollection) {
                if (instructor.getName().trim().equals(name.trim())){
                    return instructor;
                }
            }
            return null;
        } finally{
            //? unlock
            readLock.unlock();
        }
    }

    public Instructor getInstructorbyPhoneNumber(String phoneNumber){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try{
            for (Instructor instructor : instructorsCollection) {
                if (instructor.getPhoneNumber() == phoneNumber){
                    return instructor;
                }
            }
            return null;
        }finally{
            //? Unlock
            readLock.unlock();
        }
    }

    public String getAllInstructorsDescriptions() {
            //? Readers operate in mutual exclusion with writers
            ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
            readLock.lock();
            try{
                StringBuilder description = new StringBuilder("");
                for (Instructor instructor : instructorsCollection) {
                    description.append(instructor.toString()+ " \n");
                }
                return description.toString();
        }   
            finally{
                readLock.unlock();}
    }

    public byte[] getSaltByInstructorId(String instructorId) throws SQLException {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();
        try{return instructorTDG.getSaltByInstructorId(instructorId);}   
        finally{
            readLock.unlock();}
    }

    public String getHashedPasswordByInstructorId(String instructorId) throws SQLException {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();
        try{return instructorTDG.getHashedPasswordByInstructorId(instructorId);}   
        finally{
            readLock.unlock();}
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

    //** TESTING operations */
    public boolean isEmpty(){
        return this.instructorsCollection.isEmpty();
    }
    public Instructor snatch(){
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();
        try{
            if (!instructorsCollection.isEmpty()) {
                Instructor snatchedInstructor =instructorsCollection.remove(0);
                deleteInstructor(snatchedInstructor);
                return snatchedInstructor;
            } else {
                return null;
            }
        }
        finally {
            //? Unlock
            writeLock.unlock();
        }

    }

    
}
