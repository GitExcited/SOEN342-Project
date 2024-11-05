package main;

public class Client {
    private String name;
    private String phoneNumber;
    private int age;

    // Constructor
    public Client(String name, String phoneNumber, int age) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    //TODO: Implement Booking and Public Offerings classes
    /**
     * Creates a booking from public offerings.
     * 
     * @param publicOfferings The public offerings to choose from.
     * @param offeringId The ID of the offering to book.
     * @return The created booking.
     * @throws Exception If the booking cannot be created.
     */
    // public Booking createBooking(PublicOfferings publicOfferings, int offeringId) throws Exception {
    //     Offering offering = publicOfferings.getOfferingById(offeringId);
    //     if (offering == null) {
    //         throw new Exception("Offering not found.");
    //     }
    //     Booking booking = new Booking(this, offering);
    //     return booking;
    // }

    // Getters and setters for name and email
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
