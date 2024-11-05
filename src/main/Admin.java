package main;

import java.time.LocalDateTime;

// TODO: Waiting for implementation of system and organization 
public class Admin {
    private String username;
    private String password;
    private Organization organization;
    private System system;
    //Constructor
    public Admin() {
        this.username = "testAdmin";
        this.password = "testAdminPassword";
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
    public void createOffering(Lesson lesson, Schedule schedule, LocalDateTime start, LocalDateTime end) throws Exception {
        Event event = new Event(start, end);
        //system.createOffering(lesson, schedule, event);
    }
}