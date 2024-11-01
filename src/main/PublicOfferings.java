package main;

import java.util.ArrayList;
import java.util.List;

public class PublicOfferings {
    private List<PublicOffering> publicOfferingsCollection;

    // Constructor
    public PublicOfferings() {
        this.publicOfferingsCollection = new ArrayList<>();
    }

    /**
     * Creates a new public offering and adds it to the collection.
     * 
     * @param lesson The lesson for the offering.
     * @param event The event for the offering.
     * @return The newly created public offering.
     */
    public Offering createPublicOffering(Lesson lesson, Event event, Instructor instructor) {
        PublicOffering newPublicOffering = new PublicOffering(lesson, event, instructor);
        publicOfferingsCollection.add(newPublicOffering);
        return newPublicOffering;
    }

    /**
     * Displays the available public offerings.
     */
    public void getAvailablePublicOfferings() {
        int id = 0;
        for (Offering o : publicOfferingsCollection) {
            System.out.println("PUBLIC OFFERING NUMBER " + id);
            System.out.println(o.toString());
            id++; // Increment the id for each offering
        }
    }

    //? Dont know if we will need this
    // /**
    //  * Retrieves a public offering by its ID (index in the list).
    //  * 
    //  * @param id The ID (index) of the public offering to retrieve.
    //  * @return The public offering with the specified ID.
    //  * @throws IllegalArgumentException If the ID is invalid or the public offering does not exist.
    //  */
    // public Offering getPublicOfferingById(int id) {
    //     if (id < 0 || id >= publicOfferingsCollection.size()) {
    //         throw new IllegalArgumentException("ID is out of bounds.");
    //     }
    //     Offering offering = publicOfferingsCollection.get(id);
    //     if (offering == null) {
    //         throw new IllegalArgumentException("Public offering with ID " + id + " is null.");
    //     }
    //     return offering;
    // }

    /**
     * Checks if the given offering collides with any existing public offerings for the same LOCATION and TIME
     * 
     * @param offering The offering to check for time collision.
     * @return true if there is a time collision, false otherwise.
     */
    public boolean checkTimeCollision(Offering offering){
        for (Offering o: publicOfferingsCollection){
            if( o.getLesson().getLocation().equals(offering.getLesson().getLocation()) 
                &&
                o.getLessonTime().collides(offering.getLessonTime())){
                return true;
            }
        }
        return false;
    }

    public Offering removeOffering(int pofferingId){
        return publicOfferingsCollection.remove(pofferingId);
    }
    public void addOffering(PublicOffering poffering){
        publicOfferingsCollection.add(poffering);
    }


}
