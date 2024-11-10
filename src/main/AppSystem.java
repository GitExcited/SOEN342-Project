package main;
import java.time.LocalDateTime; //Used for start and end time of CreateOffering
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import main.exceptions.ScheduleConflictException;

enum UserAuthLevel {
    Client,
    ClientMinor,
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
    private Lessons lessons;
    private Offerings offerings;
    //private PublicOfferings publicOfferings;

    private boolean userAuthenticated;
    private UserAuthLevel authLevel;



    public AppSystem(){
        //System.out.println("Debug: AppSystem constructor");
        this.admin = new Admin();
        this.organization = new Organization();
        this.clients = new ClientsRegistry();
        this.instructors = new InstructorsRegistry();
        this.bookings = new BookingsRegistry();
        
        this.lessons = new Lessons();
        this.offerings = new Offerings();
        //this.publicOfferings = new PublicOfferings();
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

    // public boolean createOffering(String LessonId, String LocationId, String time ) {
    //     try {
    //         DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    //         String[] params = time.split(" ");
    //         String dayOfWeek = params[0];
    //         LocalTime starTime = LocalTime.parse(params[1], timeFormatter);
    //         LocalTime endTime = LocalTime.parse(params[2], timeFormatter);

    //         TimeSlot timeslot = new TimeSlot(dayOfWeek, starTime, endTime);
    //         //find lesson
    //         Lesson lesson = admin.organization.getLessonById(LessonId);
    //         //find Location
    //         Location location = admin.organization.getLocationById(LocationId);
    //         if (location == null || lesson == null){
    //             return false;
    //         }
    //         //create offering
    //         offerings.createOffering(lesson,location, timeslot);
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //         return false;
    //     }
    //     return true;
    // }

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
    // public void selectOffering(Instructor instructor, int offeringId){
    //     Offering selectedOffering = offerings.getOfferingById(offeringId);
    //     boolean collision = publicOfferings.checkTimeCollision(selectedOffering);
    //     if ( collision){ 
    //         System.out.println("AppSystem says: SCHEDULE CONFLICT. Offering Couldn't be added to Public Offerings");
    //     }
    //     else {
    //         //Removes and adds offering from Offerings to PublicOfferings
    //         PublicOffering newPO = new PublicOffering(offerings.deleteOffering(offeringId), instructor);
    //         publicOfferings.addOffering(newPO);
    //     } 

    // }

    //yan refactor
    public boolean selectLesson(String lessonId){
        Lesson lesson = lessons.getLessonById(lessonId);
        if(lesson == null){
            return false;
        }
        boolean collision = offerings.checkTimeCollision(currentInstructor, lesson);
        if (collision){ 
            System.out.println("AppSystem says: SCHEDULE CONFLICT. Lesson couldnt be added to Offerings.");
            return false;
        }
        else {
            lessons.removeLesson(lesson);
            Offering offering = new Offering(lesson, currentInstructor);
            offerings.addOffering(offering);
            return true;
        } 
    }

    //? ^^^ USE CASE 1 ABOVE




    //? USE CASE 2

    // public  void createBooking(Client client ,int publicOfferingId){
    //     PublicOffering selectPublicOffering= publicOfferings.getPublicOfferingById(publicOfferingId);
    //     boolean collision = bookings.checkTimeCollision(selectPublicOffering);
    //     if ( collision){ 
    //         System.out.println("AppSystem says: SCHEDULE CONFLICT. PublicOffering Couldn't be transformed into Booking");
    //     }
    //     else {
    //         //Removes and adds publicOffering from PublicOfferings to Bookings
    //         Booking newBooking = new Booking(publicOfferings.deleteOffering(publicOfferingId), client);
    //         bookings.addBooking(newBooking);
    //     } 
    // }


    public boolean isUserAuthenticated(){
        return this.userAuthenticated;
    }

    public UserAuthLevel getUserAuthLevel(){
        return this.authLevel;
    }

    public boolean loginClient(String name, String password){
        //find client with that username then do a password comparison
        Client client = clients.getClientbyName(name);
        if (client == null) {
            System.out.println("Debug: client not found");
            return false;
        }
        if( client.getPassword().equals(password)){
            userAuthenticated = true;
            if(client instanceof UnderAgeClient){
                authLevel = UserAuthLevel.ClientMinor;
            }else{
                authLevel = UserAuthLevel.Client;
            }
            currentClient = client;
            return true;
        }else{
            return false;
        }
    }

    public boolean loginInstructor(String name, String password){
        Instructor instructor = instructors.getInstructorbyName(name);
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

    public boolean loginAdmin(String password){
        if(admin.getPassword().trim().equals(password.trim())){
            userAuthenticated = true;
            authLevel = UserAuthLevel.Admin;
            return true;
        }else{
            return false;
        }
    }

    public String registerUser(String name, String phoneNumber, Integer age , String password, String guardianId){
        //verify user does not already exist 
        Client client = clients.getClientbyName(name);
        if (client != null) {
            return "Client already registered";
        }
        client = clients.getClientbyPhoneNumber(phoneNumber);
        if (client != null) {
            return "phone number already in use";
        }
        //create and add user to user collection
        Client newClient;
        if(age >= 18) {
            newClient = new Client(name, phoneNumber, age, password);
        }else{
            client = clients.getClientbyId(guardianId);
            if(client == null){
                return "Error: guardian specified by id does not exist";
            }
            newClient = new UnderAgeClient(name, phoneNumber, 0, password, guardianId);
        }
        clients.addClient(newClient);
        return "Registering new user was a success, please login now.";
    }

    public String registerInstructor(String name, String phoneNumber, Integer age, String password){
        if(age < 18){ //instructor cant be a minor
            return "Instructor must be an adult";
        }
        //verify user does not already exist 
        Instructor instructor = instructors.getInstructorbyName(name);
        if (instructor != null) {
            return "Instructor already registered";
        }
        instructor = instructors.getInstructorbyPhoneNumber(phoneNumber);
        if (instructor != null) {
            return "Phone number already in use.";
        }
        //create and add user to user collection
        instructors.addInstructor(new Instructor(name, phoneNumber, age, password));
        return "Registering instructor was a success, please login now.";
    }

    // public String browsePublicOfferings(){
    //     return publicOfferings.getAvailablePublicOfferings();
    // }

    public String getCurrentUserBookings(){
        return bookings.getClientBookings(currentClient.getID());
    }

    public String getCurrentUserBookings(String Id){
        return bookings.getClientBookings(Id);
    }

    public String getCurrentUserSelectedLessons(){
        return offerings.getOfferingsforInstructor(currentInstructor);
    }

    public void processNewBookings(){

    }

    public String deleteBooking(String id){
        Booking booking = bookings.getBookingById(id);
        if(booking == null){
            return "Failed to find booking specified by id";
        }
        booking.getOffering().setBooked(false);
        bookings.deleteBooking(booking);
        return "Deleting booking was a success";
    }

    // public String getAllOfferingsToString(){
    //     return offerings.getAllOfferingDescriptions() +" \n" + publicOfferings.getAvailablePublicOfferings();
    // }



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
        return lessons.getAllLessonsDescriptions();
    }

    public String getAllLocationsToString() {
        return admin.organization.getAllLocationsDescriptions();
    }

    public void Logout() {
        userAuthenticated = false;
        authLevel = UserAuthLevel.NotAuthorized;
        currentInstructor = null;
        currentClient = null;
    }

    // public boolean cancelOfferingSelection(String id) {
    //     PublicOffering selectedOffering = publicOfferings.getPublicOfferingById(id);

    //     offerings.addOffering(selectedOffering);
    //     publicOfferings.deleteOffering(selectedOffering);
    //     return true;
    // }

    public boolean createLesson(String title, String description, String locationId, String time) {
        try {
            Set<String> daysOfWeek = new HashSet<>();
            daysOfWeek.add("monday");
            daysOfWeek.add("tuesday");
            daysOfWeek.add("wednesday");
            daysOfWeek.add("thursday");
            daysOfWeek.add("friday");
            daysOfWeek.add("saturday");
            daysOfWeek.add("sunday");

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String[] params = time.split(" ");
            String dayOfWeek = params[0].toLowerCase();
            if(!daysOfWeek.contains(dayOfWeek)){
                return false;
            }
            LocalTime starTime = LocalTime.parse(params[1], timeFormatter);
            LocalTime endTime = LocalTime.parse(params[2], timeFormatter);

            TimeSlot timeslot = new TimeSlot(dayOfWeek, starTime, endTime);

            //find Location
            Location location = admin.organization.getLocationById(locationId);
            if (location == null){
                System.out.println("Debug: Couldnt find location");
                return false;
            }
            //create Lesson
            //offerings.createOffering(lesson,location, timeslot);
            Lesson newLesson = new Lesson(title, description, location, timeslot);
            if(!lessons.checkTimeCollision(newLesson)){
                lessons.createLesson(newLesson);
            }else{
                System.out.println("AppSystem says: SCHEDULE CONFLICT. Lesson couldnt be created.");
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteLesson(String id) {
        //get lesson by id 
        Lesson lessonToRemove = lessons.getLessonById(id);
        if(lessonToRemove == null){
            return false;
        }
        //tell lessons to remove it
        lessons.removeLesson(lessonToRemove);
        return true;
    }

    public boolean cancelLessonSelection(String id) {
        Offering offering = offerings.getOfferingById(id);
        if(offering == null){
            return false;
        }
        lessons.addLesson(offering.getLesson());
        offerings.removeOffering(offering);
        return true;
    }

    public String getAllOfferingsToString() {
       return offerings.getAllOfferingDescriptions();
    }

    public String getAllnonBookedOfferingsToString() {
        return offerings.getAllnonBookedOfferingDescriptions();
     }

    public String selectOffering(String id) {
        Offering offering = offerings.getOfferingById(id);
        if(offering == null){
            return "Failed to find offering specified by id.";
        }
        if(bookings.checkTimeCollision(currentClient, offering)){
            return "Unable to add booking, collision detected with other bookings the client already has.";
        }
        //needs to be refactored
        bookings.addBooking(new Booking(offering, currentClient));
        offering.setBooked(true);
        //offerings.removeOffering(offering);
        return "Offering selection was a success.";
    }

    public String selectOffering(String id, String minorId) {
        Client client = clients.getClientbyId(minorId);
        if (client == null) {
            return "Could not find client specified by Id.";
        }
        Offering offering = offerings.getOfferingById(id);
        if(offering == null){
            return "Failed to find offering specified by id.";
        }
        if(bookings.checkTimeCollision(client, offering)){
            return "Unable to add booking, collision detected with other bookings the client already has.";
        }

        //needs to be refactored
        bookings.addBooking(new Booking(offering, client));
        offering.setBooked(true);
        //offerings.removeOffering(offering);
        return "Offering selection was a success.";
    }

    public String getCurrentResponsibleChildren() {
        return clients.getAllResponsibleChildrenByGuardianId(currentClient.getID());
    }
}
