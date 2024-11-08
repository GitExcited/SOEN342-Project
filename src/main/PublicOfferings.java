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
    public Offering createPublicOffering(Lesson lesson, TimeSlot event, Instructor instructor, Location location) {
        PublicOffering newPublicOffering = new PublicOffering(lesson, event, instructor, location);
        publicOfferingsCollection.add(newPublicOffering);
        return newPublicOffering;
    }

    /**
     * Displays the available public offerings.
     */
    public String getAvailablePublicOfferings() {
        StringBuilder sb = new StringBuilder();
        int id = 0;
        for (Offering o : publicOfferingsCollection) {
            sb.append("PUBLIC OFFERING NUMBER ").append(id).append("\n");
            sb.append(o.toString()).append("\n");
            id++; // Increment the id for each offering
        }
        return sb.toString();
    }

    /**
     * Retrieves a public offering by its ID (index in the list).
     * 
     * @param id The ID (index) of the public offering to retrieve.
     * @return The public offering with the specified ID.
     * @throws IllegalArgumentException If the ID is invalid or the public offering does not exist.
     */
    public PublicOffering getPublicOfferingById(int id) {
        if (id < 0 || id >= publicOfferingsCollection.size()) {
            throw new IllegalArgumentException("ID is out of bounds.");
        }
        PublicOffering offering = publicOfferingsCollection.get(id);
        if (offering == null) {
            throw new IllegalArgumentException("Public offering with ID " + id + " is null.");
        }
        return offering;
    }

    /**
     * Checks if the given offering collides with any existing public offerings for the same LOCATION and TIME
     * 
     * @param offering The offering to check for time collision.
     * @return true if there is a time collision, false otherwise.
     */
    // public boolean checkTimeCollision(Offering offering){
    //     for (Offering o: publicOfferingsCollection){
    //         if( o.getLesson().getLocation().equals(offering.getLesson().getLocation()) 
    //             &&
    //             o.getTimeSlot().collides(offering.getTimeSlot())){
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public PublicOffering deleteOffering(int pofferingId){
        return publicOfferingsCollection.remove(pofferingId);
    }
    public void addOffering(PublicOffering poffering){
        publicOfferingsCollection.add(poffering);
    }
}
