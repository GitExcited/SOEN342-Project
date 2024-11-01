package main;
import java.time.LocalDateTime; //Used for start and end time of CreateOffering

import main.exceptions.ScheduleConflictException;

enum UserAuthLevel {
    Client,
    Instructor,
    Admin,
    NotAuthorized
}

public class AppSystem {


    private Admin admin;
    private Organization organization;
    private Clients clients;
    private Instructors instructors;
    private Bookings bookings;
    private Offerings offerings;
    private PublicOfferings publicOfferings;

    private boolean userAuthenticated;
    private UserAuthLevel authLevel;


    public AppSystem(){
        this.admin = new Admin();
        this.organization = new Organization();
        this.clients = new Clients();
        this.instructors = new Instructors();
        this.bookings = new Bookings();
        this.offerings = new Offerings();
        this.publicOfferings = new PublicOfferings();

        this.userAuthenticated = false;
        this.authLevel = UserAuthLevel.NotAuthorized;
    }

    //? PART OF USE CASE 1: Process Offerings

    /**
     * Creates Offering and adds it to the collection Offerings
     * @param lesson The type of lesson ( swiming, boxing, etc)
     * @param location The location you want to make the lesson
     * @param startTime Start time of lesson
     * @param endTime end time of lesson
     */
    public void creatOffering(Lesson lesson, Location location,LocalDateTime startTime, LocalDateTime endTime ) {
        Event temptativeTime = new Event(startTime, endTime);
        Offering off = null;
        try{
         off =offerings.createOffering(lesson, location, temptativeTime);
        } catch (ScheduleConflictException e) {
            // TODO: handle exception. For now simply printit
            System.out.println("AppSystem says: SCHEDULE CONFLICT. Lesson Couldn't be added");
        }
        System.out.println("AppSystem says: Offering Created"+ off.toString());  
    }

    /**
     * Prints to screen all available offerings with their Ids
     */
    public void viewOfferings(){
        offerings.getAvailableOfferings();
    }
    /**
     * Gets offering by Id, checks if there is a time collision 
     * @param instructor
     * @param offeringId
     */
    public void selectOffering(Instructor instructor, int offeringId){
        Offering selectedOffering = offerings.getOfferingById(offeringId);
        boolean collision = publicOfferings.checkTimeCollision(selectedOffering);
        if ( collision){ 
            System.out.println("AppSystem says: SCHEDULE CONFLICT. Offering Couldn't be added to Public Offerings");
        }
        else {
            //Removes and adds offering from Offerings to PublicOfferings
            PublicOffering newPO = new PublicOffering(offerings.removeOffering(offeringId), instructor);
            publicOfferings.addOffering(newPO);
        } 

    }
    //? ^^^ USE CASE 1 ABOVE


    public boolean isUserAuthenticated(){
        return this.userAuthenticated;
    }

    public UserAuthLevel getUserAuthLevel(){
        return this.authLevel;
    }

    public boolean loginClient(String username, String password){
        return false;
    }

    public boolean loginInstructor(String username, String password){
        return false;
    }

    public boolean loginAdmin(String username, String password){
        return false;
    }

    public boolean registerUser(String name, String phoneNumber, String age){
        return false;
    }

    public boolean registerInstructor(String name, String phoneNumber, String age){
        return false;
    }

    public String publicOfferingsToString(){
        return "";
    }

    public String getCurrentUserBookings(){
        return "";
    }

    public String getCurrentUserSelectedOfferings(){
        return "";
    }

    public void processNewBookings(){

    }



    public boolean deleteOffering(String Id){
        return false;
    }

    public boolean deleteInstructor(String Id){
        return false;
    }

    public boolean deleteClient(String Id){
        return false;
    }
}
