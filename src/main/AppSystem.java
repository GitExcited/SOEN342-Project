package main;

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

    public void takeNewOfferings(){

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
