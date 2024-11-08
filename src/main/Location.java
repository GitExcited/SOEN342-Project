package main;

public class Location {
    private String id;
    private String name;
    private String address;
    private String city;
    private String room;
    private Schedule schedule; //Each location has an availability schedule

    // Constructor
    public Location(String name, String address, String city, String room, Schedule schedule) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.room = room;
        this.schedule = schedule;
    }
    //Constructor for an empty schedule
    public Location(String id, String name, String address, String city, String room){
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.room = room;
        this.schedule = new Schedule();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getRoom() {
        return room;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", room='" + room + '\'' +
                ", schedule=" + (schedule != null ? schedule.toString() : "No Schedule") +
                '}';
    }
}
