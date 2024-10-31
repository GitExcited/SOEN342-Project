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
}
