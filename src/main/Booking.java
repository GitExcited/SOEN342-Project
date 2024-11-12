package main;

import java.util.UUID;

public class Booking{
    private String id;
    private Client client;
    private Offering offering;

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

    public Offering getOffering() {
        return offering;
    }

    public void setOffering(Offering offering) {
        this.offering = offering;
    }

    @Override
    public String toString() {
        return "Booking{" +
                ", offering= " + offering.toString() +
                ", client= " + client.toString() +
                '}';
    }

    public Object[] toParams() {
        return new Object[] {
            this.id,
            this.client.getID(),
            this.offering.getID()
        };
    }
}
