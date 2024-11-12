package main;

import java.util.ArrayList;
import java.util.List;

public class LocationsRegistry {
    private List<Location> locations;

    // Constructor
    public LocationsRegistry() {
        this.locations = new ArrayList<>();
    }

    /**
     * Adds a new location to the registry.
     * 
     * @param location The location to be added.
     */
    public void addLocation(Location location) {
        locations.add(location);
    }

    /**
     * Deletes a location from the registry.
     * 
     * @param location The location to be deleted.
     * @return true if the location was successfully deleted, false otherwise.
     */
    public boolean deleteLocation(Location location) {
        return locations.remove(location);
    }

    /**
     * Updates an existing location in the registry.
     * 
     * @param oldLocation The location to be updated.
     * @param newLocation The new location details.
     * @return true if the location was successfully updated, false otherwise.
     */
    public boolean updateLocation(Location oldLocation, Location newLocation) {
        int index = locations.indexOf(oldLocation);
        if (index != -1) {
            locations.set(index, newLocation);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the list of locations.
     * 
     * @return The list of locations.
     */
    public List<Location> getLocations() {
        return locations;
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
        for (Location location : locations) {
            sb.append(location.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
