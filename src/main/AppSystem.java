package main;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    private ClientsRegistry clients;
    private InstructorsRegistry instructors;
    private BookingsRegistry bookings;
    private LessonsRegistry lessons;
    private OfferingsRegistry offerings;
    private LocationsRegistry locations;
    //private PublicOfferings publicOfferings;

    private boolean userAuthenticated;
    private UserAuthLevel authLevel;



    public AppSystem() throws ClassNotFoundException, SQLException{
        //System.out.println("Debug: AppSystem constructor");
        this.admin = new Admin();
        this.clients = new ClientsRegistry();
        this.instructors = new InstructorsRegistry();
        this.bookings = new BookingsRegistry();
        this.lessons = new LessonsRegistry();
        this.offerings = new OfferingsRegistry();
        this.locations= new LocationsRegistry();
        //this.publicOfferings = new PublicOfferings();
        this.currentClient = null;
        this.currentInstructor = null;
        this.userAuthenticated = false;
        this.authLevel = UserAuthLevel.NotAuthorized;
 
        //Initializes all objects in their registries from the database
        System.out.println("Initializing Database ...");
        initialize();
        System.out.println("Database Loaded successfully");

    }

    public static void main(String[] args) {
        try {
            AppSystem app =new AppSystem();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

            //* INITIALIZE Method */
    public void initialize() {

        ReentrantReadWriteLock.WriteLock writeLock = DatabaseLock.lock.writeLock();
        writeLock.lock();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            
            
            // INTIALIZE CLIENTS 
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENT");

            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String phone = rs.getString("PHONE_NUMBER");
                int age = rs.getInt("AGE");
                String hashedPassword = rs.getString("PASSWORD");

                
                Client client = new Client(name, phone, age, hashedPassword);
                client.setID(id);
                clients.initializeClient(client);
            }

            // INTIALIZE INSTRUCTORS 
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM INSTRUCTOR");

            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String phone = rs.getString("PHONE_NUMBER");
                int age = rs.getInt("AGE");
                String hashedPassword = rs.getString("PASSWORD");
                String citiesStr = rs.getString("CITIES");

                // Convert the comma-separated string back into an array of strings
                String[] cities = citiesStr.split(",");
            
                Instructor instructor = new Instructor(name, phone, age, hashedPassword,cities);
                instructor.setID(id);
                 
                instructors.initializeInstructor(instructor);
            }
            // INTIALIZE LOCATION 
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM LOCATION");

            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String adress = rs.getString("ADDRESS");
                String city= rs.getString("CITY");
                String room= rs.getString("ROOM");
                String scheduleJSON = rs.getString("SCHEDULE");

                Schedule schedule = new Schedule();
                schedule.fromJson(scheduleJSON);
                Location location = new Location(name, adress,city,room,schedule);

                location.setID(id);
                locations.initializeLocation(location);
            }

            // // Print all locations to verify they are properly added
            // locations.printAllLocations();

            // INITIALIZE LESSON (must run after initializing location)
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM LESSON");
            while (rs.next()) {
                String id = rs.getString("ID");
                String title = rs.getString("TITLE");
                String description = rs.getString("DESCRIPTION");
                String locationID = rs.getString("LOCATION_ID");
                String timeslotJSON = rs.getString("TIMESLOT");

                // Parse the TIMESLOT JSON string
                TimeSlot timeSlot = new TimeSlot(timeslotJSON);

                // Find the location by ID
                Location location = locations.getLocationById(locationID);

                // Create and initialize the lesson
                Lesson lesson = new Lesson(title, description, location, timeSlot);

                lesson.setID(id);
                lessons.initializeLesson(lesson);
            }

            // // Print all lessons to verify they are properly added
            // lessons.printAllLessons();
           
             // INITIALIZE OFFERINGS (must run after initializing lessons and instructors)
             stmt = c.createStatement();
             rs = stmt.executeQuery("SELECT * FROM OFFERING");
             while (rs.next()) {
                 String id = rs.getString("ID");
                 String lessonID = rs.getString("LESSON_ID");
                 String instructorID = rs.getString("INSTRUCTOR_ID");
                 boolean booked = rs.getBoolean("BOOKED");
 
                 // Find the lesson and instructor by ID
                 Lesson lesson = lessons.getLessonById(lessonID);
                 Instructor instructor = instructors.getInstructorById(instructorID);
 
                 // Create and initialize the offering
                 Offering offering = new Offering(lesson, instructor);
                 offering.setID(id);
                 offering.setBooked(booked);
                 offerings.initializeOffering(offering);
             }
 
            //  // Print all offerings to verify they are properly added
            //  offerings.printAllOfferings();

            // INITIALIZE BOOKINGS (must run after initializing offerings and clients)
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM BOOKING");
            while (rs.next()) {
                String id = rs.getString("ID");
                String clientID = rs.getString("CLIENT_ID");
                String offeringID = rs.getString("OFFERING_ID");

                // Find the client and offering by ID
                Client client = clients.getClientbyId(clientID);
                Offering offering = offerings.getOfferingById(offeringID);

                // Create and initialize the booking
                Booking booking = new Booking(offering, client);
                booking.setID(id);
                bookings.initializeBooking(booking);
            }

            // // Print all bookings to verify they are properly added
            // bookings.printAllBookings();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Prints to screen all available offerings with their Ids
     */
    public void viewOfferings(){
        offerings.getAvailableOfferings();
    }

    //yan refactor
    public boolean selectLesson(String lessonId){
        Lesson lesson = lessons.getLessonById(lessonId);
        if(lesson == null){
            return false;
        }

        if(!currentInstructor.cities.contains(lesson.getLocation().getCity())){
            return false;
        }

        boolean collision = offerings.checkTimeCollision(currentInstructor, lesson);
        if (collision){ 
            System.out.println("AppSystem says: SCHEDULE CONFLICT. Lesson couldnt be added to Offerings.");
            return false;
        } else {
            lessons.removeLesson(lesson);
            Offering offering = new Offering(lesson, currentInstructor);
            offerings.createOffering(offering);
            return true;
        } 
    }

    //? ^^^ USE CASE 1 ABOVE




    //? USE CASE 2

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
        clients.createClient(newClient);
        return "Registering new user was a success, please login now.";
    }

    public String registerInstructor(String name, String phoneNumber, Integer age, String password, String cities){
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
        //parse list cities
        String[] citiesParsed = cities.split(" ");
        //create and add user to user collection
        instructors.createInstructor(new Instructor(name, phoneNumber, age, password, citiesParsed));
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

    public boolean deleteOffering(String Id){
        return offerings.deleteOffering(Id);
    }

    public boolean deleteInstructor(String Id){
        return instructors.deleteInstructor(instructors.getInstructorById(Id));
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
        lessons.createLesson(offering.getLesson());
        offerings.deleteOffering(offering);
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
        bookings.createBooking(new Booking(offering, currentClient));
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
        bookings.createBooking(new Booking(offering, client));
        offering.setBooked(true);
        //offerings.removeOffering(offering);
        return "Offering selection was a success.";
    }

    public String getCurrentResponsibleChildren() {
        return clients.getAllResponsibleChildrenByGuardianId(currentClient.getID());
    }

    public String listAllCities() {
        return admin.organization.getAllCities();
    }
}
