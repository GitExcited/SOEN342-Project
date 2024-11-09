package main;

import java.util.ArrayList;
import java.util.List;

public class BookingsRegistry {
    private List<Booking> bookings;

    // Constructor
    public BookingsRegistry() {
        this.bookings = new ArrayList<>();
    }
        /**
     * Checks if the given offering collides with any existing public offerings for the same LOCATION and TIME
     * 
     * @param offering The offering to check for time collision.
     * @return true if there is a time collision, false otherwise.
     */
    // public boolean checkTimeCollision(Offering offering){
    //     for (Booking b: bookings){
    //         if( b.getLesson().getLocation().equals(offering.getLesson().getLocation()) 
    //             &&
    //             b.getTimeSlot().collides(offering.getTimeSlot())){
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    /**
     * Adds a new booking to the registry.
     * 
     * @param booking The booking to be added.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Deletes a booking from the registry.
     * 
     * @param booking The booking to be deleted.
     * @return true if the booking was successfully deleted, false otherwise.
     */
    public void deleteBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Updates an existing booking in the registry.
     * 
     * @param oldBooking The booking to be updated.
     * @param newBooking The new booking details.
     * @return true if the booking was successfully updated, false otherwise.
     */
    public boolean updateBooking(Booking oldBooking, Booking newBooking) {
        int index = bookings.indexOf(oldBooking);
        if (index != -1) {
            bookings.set(index, newBooking);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the list of bookings.
     * 
     * @return The list of bookings.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    public boolean checkTimeCollision(Client currentClient, Offering offering) {
        ArrayList<Booking> clientsCollection = new ArrayList<Booking>();;
        for (Booking b: bookings){
            if(b.getClient().getID().trim().equals(currentClient.getID().trim())){
               clientsCollection.add(b);
            }
        }
        for (Booking b: clientsCollection){
            if(b.getOffering().getLesson().getTimeSlot().collides(offering.getLesson().getTimeSlot())){
                return true;
            }
        }
        return false;
    }

    /**
     * Provides a string representation of the list of bookings.
     * 
     * @return A string representation of the list of bookings.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BookingsRegistry{\n");
        for (Booking booking : bookings) {
            sb.append(booking.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public String getClientBookings(String id) {
        StringBuilder sb = new StringBuilder();
        for (Booking b: bookings) {
            if(b.getClient().getID().trim().equals(id.trim())){
                sb.append("BOOKING NUMBER ").append(b.getID()).append("\n");
                sb.append(b.toString()).append("\n");
                break;
            }
        }
        return sb.toString();
    }

    public Booking getBookingById(String id) {
        for (Booking booking: bookings) {
            if (booking.getID().trim().equals(id.trim())){
                return booking;
            }
        }
        return null;
    }
}
