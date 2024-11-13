package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import tdg.BookingTDG;

public class BookingsRegistry {
    private List<Booking> bookingCollection= new ArrayList<>();;
    private BookingTDG bookingTDG;

    //* Constructor
    public BookingsRegistry() {
        this.bookingTDG = new BookingTDG();
        try {
            bookingTDG.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    //* CREATE, UPDATE and DELETE Operations */

    /**
     * Adds a new booking to the registry.
     * 
     * @param booking The booking to be added.
     */
    public void createBooking(Booking booking) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        bookingCollection.add(booking);
        try {
            bookingTDG.insert(booking.toParams());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }

    /**
     * Deletes a booking from the registry.
     * 
     * @param booking The booking to be deleted.
     * @return true if the booking was successfully deleted, false otherwise.
     */
    public void deleteBooking(Booking booking) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        bookingCollection.remove(booking);
        try {
            bookingTDG.delete(booking.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }

    public void updateBooking(int BookingId, Booking newBooking) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        Booking oldBooking= bookingCollection.get(BookingId);
        
        newBooking.setID(oldBooking.getID());
        bookingCollection.set(BookingId, newBooking);

        try {
            bookingTDG.update(newBooking.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //? Unlock
            writeLock.unlock();
        }
    }

    //* READ Operations */
    /**
     * Retrieves the list of bookings.
     * 
     * @return The list of bookings.
     */
    public List<Booking> getBookingCollection() {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        return bookingCollection;
        }finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public boolean checkTimeCollision(Client currentClient, Offering offering) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
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
        }finally {
            //? Unlock
            readLock.unlock();
        }
    }

    /**
     * Provides a string representation of the list of bookings.
     * 
     * @return A string representation of the list of bookings.
     */
    @Override
    public String toString() {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        StringBuilder sb = new StringBuilder();
        sb.append("BookingsRegistry{\n");
        for (Booking booking : bookingCollection) {
            sb.append(booking.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
        }finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public String getClientBookings(String id) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        StringBuilder sb = new StringBuilder();
        for (Booking b: bookingCollection) {
            if(b.getClient().getID().trim().equals(id.trim())){
                sb.append("BOOKING NUMBER ").append(b.getID()).append("\n");
                sb.append(b.toString()).append("\n");
                break;
            }
        }
        return sb.toString();
        }finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public String getAllBookingsDescriptions() {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        StringBuilder sb = new StringBuilder();
        for (Booking b : bookingCollection) {
            sb.append(b.toString()).append("\n");
        }
        return sb.toString();
        }finally {
            //? Unlock
            readLock.unlock();
        }
    }

    public Booking getBookingById(String id) {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
        for (Booking booking: bookingCollection) {
            if (booking.getID().trim().equals(id.trim())){
                return booking;
            }
        }
        return null;
        }finally {
            //? Unlock
            readLock.unlock();
        }
    }
}
