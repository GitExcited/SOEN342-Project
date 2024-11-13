package main;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Organization {
    private ArrayList<Location> locationCollection = new ArrayList<Location>();

    public Organization(){

    }

    public void initializeLocations(){
        locationCollection.add(new Location("1", "City Sports Complex", "123 Main St", "Springfield", "Room 101"));
        locationCollection.add(new Location("2", "Downtown Gym", "456 Maple Ave", "Riverside", "Studio B"));
        locationCollection.add(new Location("3", "Westside Pool", "789 Oak Rd", "Lakeside", "Pool 1"));
        locationCollection.add(new Location("4", "East End Fitness Center", "321 Pine Ln", "Hilltown", "Room 305"));
        locationCollection.add(new Location("5", "Central Park Courts", "654 Elm St", "Greenfield", "Court 2"));
        locationCollection.add(new Location("6", "Northside Yoga Studio", "987 Birch Dr", "Meadowville", "Studio A"));
        locationCollection.add(new Location("7", "Southtown Boxing Club", "111 Cedar Pl", "Brookfield", "Room 12"));
        locationCollection.add(new Location("8", "Uptown Dance Hall", "222 Willow Rd", "Ridgeview", "Main Hall"));
        locationCollection.add(new Location("9", "Valley Martial Arts", "333 Aspen Ct", "Riverbend", "Dojo Room"));
        locationCollection.add(new Location("10", "Lakeside Track & Field", "444 Poplar Ave", "Clearwater", "Field A"));
    }

    public String getAllLocationsDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (Location location : locationCollection) {
            description.append(location.toString()+ " \n");
        }
        return description.toString();
    }

    public Location getLocationById(String id){
        Location location = null;
        for (Location location2 : locationCollection) {
            if(location2.getID().trim().equals(id.trim())){
                location = location2;
                break;
            }
        }
        return location;
    }

    public String getAllCities() {
        Set<String> cities = new HashSet<>();
        StringBuilder description = new StringBuilder("");
        for (Location location : locationCollection) {
            if (!cities.contains(location.getCity())){
                description.append(location.getCity()+ " \n");
                cities.add(location.getCity());
            }
        }
        return description.toString();
    }

}
