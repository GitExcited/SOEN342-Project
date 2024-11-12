package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tdg.BookingTDG;

public class BookingsRegistry {
    private List<Booking> bookingCollection= new ArrayList<>();;
    private BookingTDG bookingTDG;

    // Constructor
    public BookingsRegistry() {
        this.bookingTDG = new BookingTDG();
        try {
            bookingTDG.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Adds a new booking to the registry.
     * 
     * @param booking The booking to be added.
     */
    public void addBooking(Booking booking) {
        bookingCollection.add(booking);
        try {
            bookingTDG.insert(booking.toParams());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a booking from the registry.
     * 
     * @param booking The booking to be deleted.
     * @return true if the booking was successfully deleted, false otherwise.
     */
    public void deleteBooking(Booking booking) {
        bookingCollection.remove(booking);
        try {
            bookingTDG.delete(booking.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBooking(int BookingId, Booking newBooking) {
        Booking oldBooking= bookingCollection.get(BookingId);
        
        newBooking.setID(oldBooking.getID());
        bookingCollection.set(BookingId, newBooking);

        try {
            bookingTDG.update(newBooking.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the list of bookings.
     * 
     * @return The list of bookings.
     */
    public List<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public boolean checkTimeCollision(Client currentClient, Offering offering) {
        ArrayList<Booking> clientsCollection = new ArrayList<Booking>();;
        for (Booking b: bookingCollection){
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
        for (Booking booking : bookingCollection) {
            sb.append(booking.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public String getClientBookings(String id) {
        StringBuilder sb = new StringBuilder();
        for (Booking b: bookingCollection) {
            if(b.getClient().getID().trim().equals(id.trim())){
                sb.append("BOOKING NUMBER ").append(b.getID()).append("\n");
                sb.append(b.toString()).append("\n");
                break;
            }
        }
        return sb.toString();
    }

    public Booking getBookingById(String id) {
        for (Booking booking: bookingCollection) {
            if (booking.getID().trim().equals(id.trim())){
                return booking;
            }
        }
        return null;
    }
}
