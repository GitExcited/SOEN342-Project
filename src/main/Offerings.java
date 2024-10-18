package main;
import java.util.ArrayList;

class Offerings{
    ArrayList<Offering> offs = new ArrayList<Offering>();//Collection of offerings
    /**
     * Creates a new offering for a given lesson and schedule.
     * 
     * This method checks if the provided event conflicts with the existing schedule.
     * If there is no conflict, it creates a new offering, adds it to the list of offerings,
     * and returns the newly created offering.
     * 
     * @param lesson The lesson for which the offering is being created.
     * @param schedule The schedule to which the offering will be added.
     * @param event The event representing the time slot for the offering.
     * @return The newly created offering.
     * @throws Exception If there is a conflict in the schedule for the given event.
     */
    public Offering createOffering(Lesson lesson, Schedule schedule,Event event){
        if (schedule.hasConflict(event)) {
            try {
                throw new Exception("Schedule conflict detected for the given event.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Offering newOffering = new Offering(lesson, event);
        offs.add(newOffering);
        return newOffering;

    }
}