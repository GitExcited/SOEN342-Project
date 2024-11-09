package main;

import java.time.LocalDateTime;

// TODO: Waiting for implementation of system and organization 
public class Admin {
    private String username;
    private String password;
    public Organization organization;
    private System system;
    //Constructor
    public Admin() {
        this.username = "testAdmin";
        this.password = "testAdminPassword";
        this.organization = new Organization();
        organization.initializeLocations();
    }

    /**
     * Creates a new offering for a given lesson and schedule.
     * 
     * @param lesson The lesson for which the offering is being created.
     * @param schedule The schedule to which the offering will be added.
     * @param start The start time of the event.
     * @param end The end time of the event.
     * @throws Exception If there is a conflict in the schedule for the given event.
     */
    // public void createOffering(Lesson lesson, Location location, LocalDateTime start, LocalDateTime end) throws Exception {
    //     TimeSlot event = new TimeSlot(start, end);
    //     //system.createOffering(lesson, schedule, event);
    // }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}