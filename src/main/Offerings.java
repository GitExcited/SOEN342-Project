package main;
import java.util.ArrayList;

class Offerings{
    private ArrayList<Offering> offeringsCollection = new ArrayList<Offering>();//Collection of offerings

    public void addOffering(Offering offering) {
        offeringsCollection.add(offering);
    }

    public void removeOffering(Offering offering) {
        offeringsCollection.remove(offering);
    }

    public Offering deleteOffering(int offeringId){
        return offeringsCollection.remove(offeringId);
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

    public Offering getOfferingById(String id){
        for (Offering offering : offeringsCollection) {
            if (offering.getID().trim().equals(id.trim())){
                return offering;
            }
        }
        return null;
    }

    public String getAllOfferingDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (Offering offering : offeringsCollection) {
            description.append(offering.toString()+ " \n");
        }
        return description.toString();
    }

    public String getAllnonBookedOfferingDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (Offering offering : offeringsCollection) {
            if(!offering.getBooked()){
                description.append(offering.toString()+ " \n");
            }
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

    public boolean checkTimeCollision(Instructor currentInstructor, Lesson lesson) {
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
    }

    public boolean checkTimeCollision(Lesson lesson) {
        for (Offering o: offeringsCollection){
            if(o.getLesson().getTimeSlot().collides(lesson.getTimeSlot())){
                return true;
            }
        }
        return false;
    }

    public String getOfferingsforInstructor(Instructor currentInstructor) {
        StringBuilder sb = new StringBuilder();
        for (Offering o: offeringsCollection) {
            if(o.getInstructor().getID().trim().equals(currentInstructor.getID().trim())){
                sb.append("OFFERING NUMBER ").append(o.getID()).append("\n");
                sb.append(o.toString()).append("\n");
                break;
            }
        }
        return sb.toString();
    }
}