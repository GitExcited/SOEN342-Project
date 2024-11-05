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
    private Client currentClient;
    private Instructor currentInstructor;
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
        this.currentClient = null;
        this.currentInstructor = null;

        this.userAuthenticated = false;
        this.authLevel = UserAuthLevel.NotAuthorized;
    }

    //? PART OF USE CASE 1: Process Offerings
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

    public void viewOfferings(){
        offerings.getAvailableOfferings();
    }

    public boolean isUserAuthenticated(){
        return this.userAuthenticated;
    }

    public UserAuthLevel getUserAuthLevel(){
        return this.authLevel;
    }

    public boolean loginClient(String username, String password){
        //find client with that username then do a password comparison
        Client client = clients.getClientbyUsername(username);
        if (client == null) {
            return false;
        }
        if( client.getPassword().equals(password)){
            userAuthenticated = true;
            authLevel = UserAuthLevel.Client;
            currentClient = client;
            return true;
        }else{
            return false;
        }
    }

    public boolean loginInstructor(String username, String password){
        Instructor instructor = instructors.getInstructorbyUsername(username);
        if (instructor == null){
            return false;
        }
        if(instructor.getPassword().equals(password)){
            userAuthenticated = true;
            authLevel = UserAuthLevel.Instructor;
            currentInstructor = instructor;
            return true;
        }else{
            return false;
        }
    }

    public boolean loginAdmin(String username, String password){
        if(admin.getPassword().equals(password)){
            userAuthenticated = true;
            authLevel = UserAuthLevel.Admin;
            return true;
        }else{
            return false;
        }
    }

    public boolean registerUser(String name, String phoneNumber, Integer age , String password){
        //verify user does not already exist 
        Client client = clients.getClientbyUsername(name);
        if (client != null) {
            return false;
        }
        client = clients.getClientbyPhoneNumber(phoneNumber);
        if (client != null) {
            return false;
        }
        //create and add user to user collection
        clients.addClient(new Client(name, phoneNumber, age, password));
        return true;
    }

    public boolean registerInstructor(String name, String phoneNumber, Integer age, String password){
                //verify user does not already exist 
        Instructor instructor = instructors.getInstructorbyUsername(name);
        if (instructor != null) {
            return false;
        }
        instructor = instructors.getInstructorbyPhoneNumber(phoneNumber);
        if (instructor != null) {
            return false;
        }
        //create and add user to user collection
        instructors.addInstructor(new Instructor(name, phoneNumber, age, password));
        return true;
    }

    public String publicOfferingsToString(){
        return publicOfferings.getAllPublicOfferingDescriptions();
    }

    public String getCurrentUserBookings(){
        return "";
    }

    public String getCurrentUserSelectedOfferings(){
        return "";
    }

    public void processNewBookings(){

    }

    public void deleteBooking(){
        
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
