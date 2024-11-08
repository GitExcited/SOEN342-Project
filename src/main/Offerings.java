package main;
import java.util.ArrayList;

import main.exceptions.ScheduleConflictException;

class Offerings{
    private ArrayList<Offering> offeringsCollection = new ArrayList<Offering>();//Collection of offerings
    /**
     * Creates a new offering for a given lesson and schedule.
     * 
     * This method checks if the provided event conflicts with the existing schedule.
     * If there is no conflict, it creates a new offering, adds it to the list of offerings,
     * and returns the newly created offering.
     * 
     * @param lesson The lesson for which the offering is being created.
     * @param location The location to which the offering will be added.
     * @param event The event representing the time slot for the offering.
     * @return The newly created offering.
     * @throws Exception If there is a conflict in the schedule for the given event.
     */
    public Offering createOffering(Lesson lesson, Location location, TimeSlot event)throws ScheduleConflictException{
        Schedule schedule = location.getSchedule();
        boolean conflict = schedule.hasConflict(event);
        if (conflict) {
            throw new ScheduleConflictException("A conflict was found for the given time slot.");
        }
        Offering newOffering = new Offering(lesson, event,location);
        offeringsCollection.add(newOffering);
        return newOffering;
    }

    public void addOffering(Offering offering) {
        offeringsCollection.add(offering);
    }

    public void removeOffering(Offering offering) {
        offeringsCollection.remove(offering);
    }

    public void getAvailableOfferings(){
        //int id = 0 ;
        for (Offering o : offeringsCollection) {
            System.out.println("OFFERING NUMBER "+ o.getID());
            System.out.println(o.toString());
            //id++;
        }
    }

    /**
     * Retrieves an offering by its ID (index in the list).
     * 
     * @param id The ID (index) of the offering to retrieve.
     * @return The offering with the specified ID.
     * @throws IllegalArgumentException If the ID is invalid or the offering does not exist.
     */
    public Offering getOfferingById(int id) throws IllegalArgumentException{
        if (id < 0 || id >= offeringsCollection.size()) {
            throw new IllegalArgumentException("ID is out of bounds.");
        }
        Offering offering = offeringsCollection.get(id);
        if (offering == null) {
            throw new IllegalArgumentException("Offering with ID " + id + " is null.");
        }
        return offering;
    }

    public Offering deleteOffering(int offeringId){
        return offeringsCollection.remove(offeringId);
    }

    public String getAllOfferingDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (Offering offering : offeringsCollection) {
            description.append(offering.toString()+ " \n");
        }
        return description.toString();
    }

    public boolean deleteOffering(String id) {
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
            removeOffering(offeringToRemove);
            return true;
        }
    }
}