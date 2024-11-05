package main;

public class UnderAgeClient extends Client {
    private Client guardian;

    // Constructor
    public UnderAgeClient(String name, String email,int age, String password, Client guardian) {
        super(name, email,age , password);
        this.guardian = guardian;
    }

    //TODO : Implement booking and public offering classes
    // /**
    //  * Creates a booking from public offerings with guardian approval.
    //  * 
    //  * @param publicOfferings The public offerings to choose from.
    //  * @param offeringId The ID of the offering to book.
    //  * @return The created booking.
    //  * @throws Exception If the booking cannot be created or if guardian approval is not granted.
    //  */
    // public Booking createBooking(PublicOfferings publicOfferings, int offeringId) throws Exception {
    //     if (!guardianApprove()) {
    //         throw new Exception("Guardian approval required.");
    //     }
    //     return super.createBooking(publicOfferings, offeringId);
    // }

    /**
     * Simulates guardian approval.
     * 
     * @return true if the guardian approves, false otherwise.
     */
    private boolean guardianApprove() {
        // Simulate guardian approval logic here
        return true;
    }

    // Getter and setter for guardian
    public Client getGuardian() {
        return guardian;
    }

    public void setGuardian(Client guardian) {
        this.guardian = guardian;
    }
}