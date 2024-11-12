package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tdg.LocationTDG;

public class LocationsRegistry {
    private List<Location> locationCollection = new ArrayList<>();
    private LocationTDG locationTDG;

    // Constructor
    public LocationsRegistry() {
        try {
            this.locationTDG = new LocationTDG();

            //Initialize table Location if not existant
            locationTDG.createTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new location to the registry.
     * 
     * @param location The location to be added.
     */
    public void addLocation(Location location) {
        locationCollection.add(location);
        try {
            locationTDG.insert(location.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a location from the registry.
     * 
     * @param location The location to be deleted.
     * @return true if the location was successfully deleted, false otherwise.
     */
    public boolean deleteLocation(Location location) {
        try {
            locationTDG.delete(location.getID());
        } catch (Exception e) {
            e.printStackTrace();

        }

        return locationCollection.remove(location);
    }


    public void updateLocation(int locationId, Location newLocation) {
        //! VERY IMPORTANT: the new location must have the same id as the old one for persistence reasons ( other tables might have this location id as a foreign key)
        Location oldLocation = locationCollection.get(locationId);
        
        newLocation.setID(oldLocation.getID());
        locationCollection.set(locationId, newLocation);

        try {
            locationTDG.update(newLocation.toParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the list of locations.
     * 
     * @return The list of locations.
     */
    public List<Location> getLocationCollection() {
        return locationCollection;
    }

    /**
     * Provides a string representation of the list of locations.
     * 
     * @return A string representation of the list of locations.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LocationRegistry{\n");
        for (Location location : locationCollection) {
            sb.append(location.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
