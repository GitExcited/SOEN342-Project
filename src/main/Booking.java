package main;

public class Booking extends PublicOffering {
    private Client client;

    // Constructor
    public Booking(Lesson lesson, TimeSlot timeSlot, Instructor instructor, Location location, Client client) {
        super(lesson, timeSlot, instructor, location);
        this.client = client;
    }
    // Constructor to transform Public Offering into Booking
    public Booking(PublicOffering po, Client client) {
        super(po.getLesson(), po.getTimeSlot(), po.getInstructor(), po.getLocation());
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

    @Override
    public String toString() {
        return "Booking{" +
                "lesson=" + getLesson() +
                ", at time=" + getTimeSlot() +
                ", instructor=" + getInstructor() +
                ", client=" + client +
                '}';
    }
}
