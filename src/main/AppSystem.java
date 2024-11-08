package main;
import java.time.LocalDateTime; //Used for start and end time of CreateOffering
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private ClientsRegistry clients;
    private InstructorsRegistry instructors;
    private BookingsRegistry bookings;
    private Offerings offerings;
    private PublicOfferings publicOfferings;

    private boolean userAuthenticated;
    private UserAuthLevel authLevel;



    public AppSystem(){
        this.admin = new Admin();
        this.organization = new Organization();
        this.clients = new ClientsRegistry();
        this.instructors = new InstructorsRegistry();
        this.bookings = new BookingsRegistry();
        this.offerings = new Offerings();
        this.publicOfferings = new PublicOfferings();
        this.currentClient = null;
        this.currentInstructor = null;

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
    // public void createOffering(Lesson lesson, Location location,LocalDateTime startTime, LocalDateTime endTime ) {
    //     TimeSlot temptativeTime = new TimeSlot(startTime, endTime);
    //     Offering off = null;
    //     try{
    //      off =offerings.createOffering(lesson, location, temptativeTime);
    //     } catch (ScheduleConflictException e) {
    //         // TODO: handle exception. For now simply printit
    //         System.out.println("AppSystem says: SCHEDULE CONFLICT. Lesson Couldn't be added");
    //     }
    //     System.out.println("AppSystem says: Offering Created"+ off.toString());  
    // }

    public boolean createOffering(String LessonId, String LocationId, String time ) {
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String[] params = time.split(" ");
            String dayOfWeek = params[0];
            LocalTime starTime = LocalTime.parse(params[1], timeFormatter);
            LocalTime endTime = LocalTime.parse(params[2], timeFormatter);

            TimeSlot timeslot = new TimeSlot(dayOfWeek, starTime, endTime);
            //find lesson
            Lesson lesson = admin.organization.getLessonById(LessonId);
            //find Location
            Location location = admin.organization.getLocationById(LocationId);
            if (location == null || lesson == null){
                return false;
            }
            //create offering
            offerings.createOffering(lesson,location, timeslot);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
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
            PublicOffering newPO = new PublicOffering(offerings.deleteOffering(offeringId), instructor);
            publicOfferings.addOffering(newPO);
        } 

    }

    //yan refactor
    public boolean selectOffering(String offeringId){
        Offering selectedOffering = offerings.getOfferingById(offeringId);
        boolean collision = publicOfferings.checkTimeCollision(selectedOffering);
        if ( collision){ 
            System.out.println("AppSystem says: SCHEDULE CONFLICT. Offering Couldn't be added to Public Offerings");
            return false;
        }
        else {
            //Removes and adds offering from Offerings to PublicOfferings
            PublicOffering newPO = new PublicOffering(selectedOffering, currentInstructor);
            offerings.removeOffering(selectedOffering);
            publicOfferings.addOffering(newPO);
            return true;
        } 
    }

    //? ^^^ USE CASE 1 ABOVE




    //? USE CASE 2

    public  void createBooking(Client client ,int publicOfferingId){
        PublicOffering selectPublicOffering= publicOfferings.getPublicOfferingById(publicOfferingId);
        boolean collision = bookings.checkTimeCollision(selectPublicOffering);
        if ( collision){ 
            System.out.println("AppSystem says: SCHEDULE CONFLICT. PublicOffering Couldn't be transformed into Booking");
        }
        else {
            //Removes and adds publicOffering from PublicOfferings to Bookings
            Booking newBooking = new Booking(publicOfferings.deleteOffering(publicOfferingId), client);
            bookings.addBooking(newBooking);
        } 
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

    public String browsePublicOfferings(){
        return publicOfferings.getAvailablePublicOfferings();
    }

    public String getCurrentUserBookings(){
        return "";
    }

    public String getCurrentUserSelectedOfferings(){
        return publicOfferings.getPublicOfferingsforInstructor(currentInstructor.getID());
    }

    public void processNewBookings(){

    }

    public void deleteBooking(){

    }

    public String getAllOfferingsToString(){
        return offerings.getAllOfferingDescriptions() +" \n" + publicOfferings.getAvailablePublicOfferings();
    }



    public boolean deleteOffering(String Id){
        return offerings.deleteOffering(Id);
    }

    public boolean deleteInstructor(String Id){
        return instructors.deleteInstructor(Id);
    }

    public boolean deleteClient(String Id){
        return clients.deleteClient(Id);
    }

    public String getAllInstructorsToString() {
        return instructors.getAllInstructorsDescriptions();
    }

    public String getAllClientsToString() {
        return clients.getAllClientsDescriptions();
    }

    public String getAllLessonsToString() {
        return admin.organization.getAllLessonsDescriptions();
    }

    public String getAlllocationsToString() {
        return admin.organization.getAllLocationsDescriptions();
    }

    public void Logout() {
        userAuthenticated = false;
        authLevel = UserAuthLevel.NotAuthorized;
        currentInstructor = null;
        currentClient = null;
    }

    public boolean cancelOfferingSelection(String id) {
        PublicOffering selectedOffering = publicOfferings.getPublicOfferingById(id);

        offerings.addOffering(selectedOffering);
        publicOfferings.deleteOffering(selectedOffering);
        return true;
    }
}
