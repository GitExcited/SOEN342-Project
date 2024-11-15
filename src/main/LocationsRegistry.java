package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import tdg.LocationTDG;

public class LocationsRegistry {
    private List<Location> locationCollection = new ArrayList<>();
    private LocationTDG locationTDG;

    //* CONSTRUCTOR */ 
    public LocationsRegistry() {
        try {
            this.locationTDG = new LocationTDG();

            //Initialize table Location if not existant
            locationTDG.createTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //*INITIALIZE from database */
    public void initializeLocation(Location location) {
        if (!locationCollection.contains(location)) {
            locationCollection.add(location);
            System.out.println("I added a location ");
        }
    }


    //* CREATE, UPDATE and DELETE Operations */
    /**
     * Adds a new location to the registry.
     * 
     * @param location The location to be added.
     */
    public void createLocation(Location location) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();
        
        locationCollection.add(location);
        try {
            locationTDG.insert(location.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //? Unlock
            writeLock.unlock();
        }
    }

    /**
     * Deletes a location from the registry.
     * 
     * @param location The location to be deleted.
     * @return true if the location was successfully deleted, false otherwise.
     */
    public boolean deleteLocation(Location location) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        try {
            locationTDG.delete(location.getID());
            return locationCollection.remove(location);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            //? Unlock
            writeLock.unlock();
        }

       
    }

    public void updateLocation(int locationId, Location newLocation) {
        //? Writer operates in self-exclusion
        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();

        try {
            //! VERY IMPORTANT: the new location must have the same id as the old one for persistence reasons ( other tables might have this location id as a foreign key)
            Location oldLocation = locationCollection.get(locationId);
            
            newLocation.setID(oldLocation.getID());
            locationCollection.set(locationId, newLocation);
            locationTDG.update(newLocation.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //? Unlock
            writeLock.unlock();
        }
    }

    //* READ operations */

    /**
     * Retrieves the list of locations.
     * 
     * @return The list of locations.
     */
    public List<Location> getLocationCollection() {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();
        try{
            return locationCollection;
        }finally{
            readLock.unlock();
        }
    }

    /**
     * Provides a string representation of the list of locations.
     * 
     * @return A string representation of the list of locations.
     */
    @Override
    public String toString() {
        //? Readers operate in mutual exclusion with writers
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try{
            StringBuilder sb = new StringBuilder();
            sb.append("LocationRegistry{\n");
            for (Location location : locationCollection) {
                sb.append(location.toString()).append("\n");
            }
            sb.append("}");
            return sb.toString();
        }finally{
            readLock.unlock();
        }
    }

    public Location getLocationById(String id) {
        
        ReentrantReadWriteLock.ReadLock readLock = DatabaseLock.lock.readLock();
        readLock.lock();

        try {
            for (Location location : locationCollection) {
                if (location.getID().trim().equals(id.trim())) {
                    return location;
                }
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }


 /**
     * Prints all locations in the registry.
     */
    public void printAllLocations() {
        for (Location location : locationCollection) {
            System.out.println(location);
        }
    }
    
}
