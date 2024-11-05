package main;

public class Booking extends PublicOffering {
    private Client client;

    // Constructor
    public Booking(Lesson lesson, Event event, Instructor instructor, Location location, Client client) {
        super(lesson, event, instructor, location);
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
                ", at time=" + getLessonTime() +
                ", instructor=" + getInstructor() +
                ", client=" + client +
                '}';
    }
}
