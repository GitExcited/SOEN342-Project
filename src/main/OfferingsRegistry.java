package main;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import tdg.OfferingTDG;

class OfferingsRegistry{
    private ArrayList<Offering> offeringsCollection = new ArrayList<Offering>();//Collection of offerings
    private OfferingTDG offeringTDG;

    //* CONSTRUCTOR */
    public OfferingsRegistry()  {
        try {
            this.offeringTDG = new OfferingTDG();
            //Initializes the table of offerings it not existant
            offeringTDG.createTable();
        } catch (ClassNotFoundException | SQLException e) {
       
            e.printStackTrace();
        }
        
    }

    //* CREATE, UPDATE and DELETE Operations */

    public void createOffering(Offering offering) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        offeringsCollection.add(offering);
        try {
            offeringTDG.insert(offering.toParams());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }

    /**
     * Updates the passed offeringId to match the attributes of updatesOffering while keeping the same ID
     * @param OfferingId The offering which will be updates. The resulting Offering has the same ID.
     * @param updatedOffering The offering with attributes to use.
     */
    public void updateOffering(int OfferingId, Offering updatedOffering ){
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();
        
        Offering oldOffering = offeringsCollection.get(OfferingId);
        
        updatedOffering.setID(oldOffering.getID());
        offeringsCollection.set(OfferingId, updatedOffering);

        try {
            offeringTDG.update(oldOffering.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }

    }


    public void deleteOffering(Offering offering) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();
        offeringsCollection.remove(offering);
        try {
            offeringTDG.delete(offering.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }

    public boolean deleteOffering(String id) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        try{
            Offering offeringToRemove = null;
            for (Offering offering : offeringsCollection) {
                if(offering.getID() == id){
                    offeringToRemove = offering;
                    break;
                }
            }
            if (offeringToRemove == null){
                return false;
            }else{
                deleteOffering(offeringToRemove);
                return true;
            }
        }
        finally {
            //? Unlock
            writeLock.unlock();
        }
    }

    //* READ Operations */

    public void getAvailableOfferings(){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();
               
        for (Offering o : offeringsCollection) {
            System.out.println("OFFERING NUMBER "+ o.getID());
            System.out.println(o.toString());
        }
        //? Unlock
        readLock.unlock();

    }

    /**
     * Retrieves an offering by its ID (index in the list).
     * 
     * @param id The ID (index) of the offering to retrieve.
     * @return The offering with the specified ID.
     * @throws IllegalArgumentException If the ID is invalid or the offering does not exist.
     */
    public Offering getOfferingById(int id) throws IllegalArgumentException{
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
            if (id < 0 || id >= offeringsCollection.size()) {
                throw new IllegalArgumentException("ID is out of bounds.");
            }
            Offering offering = offeringsCollection.get(id);
            if (offering == null) {
                throw new IllegalArgumentException("Offering with ID " + id + " is null.");
            }
            return offering;
            
        } finally{
            //? Unlock
            readLock.unlock();
        }
                
    }

    public Offering getOfferingById(String id){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
            for (Offering offering : offeringsCollection) {
                if (offering.getID().trim().equals(id.trim())){
                    return offering;
                }
            }
            return null;
        } finally{
            //? Unlock
            readLock.unlock();
        }
              
    }

    public String getAllOfferingDescriptions(){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {        
            StringBuilder description = new StringBuilder("");
            for (Offering offering : offeringsCollection) {
                description.append(offering.toString()+ " \n");
            }
            return description.toString();
        }finally{
            //? Unlock
            readLock.unlock();
        }
    }

    public String getAllnonBookedOfferingDescriptions(){
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try{
        StringBuilder description = new StringBuilder("");
        for (Offering offering : offeringsCollection) {
        if(!offering.getBooked()){
        description.append(offering.toString()+ " \n");
            }
        }
        return description.toString();}
        finally{
            
        }
    }

    

    public boolean checkTimeCollision(Instructor currentInstructor, Lesson lesson) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try{
            ArrayList<Offering> instructorsCollection = new ArrayList<Offering>();;
            for (Offering o: offeringsCollection){
                if(o.getInstructor() == currentInstructor){
                    instructorsCollection.add(o);
                }
            }
            for (Offering o: instructorsCollection){
                if(o.getLesson().getTimeSlot().collides(lesson.getTimeSlot())){
                    return true;
                }
            }
            return false;
        }finally{
            //? Unlock
            readLock.unlock();
        }
    }

    public boolean checkTimeCollision(Lesson lesson) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();
        try{
        for (Offering o: offeringsCollection){
            if(o.getLesson().getTimeSlot().collides(lesson.getTimeSlot())){
                return true;
            }
        }
        return false;}
        finally{
            //? Unlock
            readLock.unlock();
        }
    }

    public String getOfferingsforInstructor(Instructor currentInstructor) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try{
            StringBuilder sb = new StringBuilder();
            for (Offering o: offeringsCollection) {
                if(o.getInstructor().getID().trim().equals(currentInstructor.getID().trim())){
                    sb.append("OFFERING NUMBER ").append(o.getID()).append("\n");
                    sb.append(o.toString()).append("\n");
                    break;
                }
            }
            return sb.toString();
        }finally{
            //? Unlock
            readLock.unlock();
        }
    }
}