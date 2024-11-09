package main;

import java.util.UUID;

public class Booking{
    private String id;
    private Client client;
    private Offering offering;

    // Constructor
    // public Booking(Lesson lesson, TimeSlot timeSlot, Instructor instructor, Location location, Client client) {
    //     super(lesson, timeSlot, instructor, location);
    //     this.client = client;
    // }
    // Constructor to transform Public Offering into Booking
    public Booking(Offering offering, Client client) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.offering = offering;
        this.client = client;
    }

    
    // Getter for client
    public Client getClient() {
        return client;
    }

    // Setter for client
    public void setClient(Client client) {
        this.client = client;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Booking{" +
                ", offering= " + offering.toString() +
                ", client= " + client.toString() +
                '}';
    }
}
