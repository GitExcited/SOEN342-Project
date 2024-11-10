package main;

public class Admin {
    private String username;
    private String password;
    public Organization organization;

    //Constructor
    public Admin() {
        this.username = "testAdmin";
        this.password = "testAdminPassword";
        this.organization = new Organization();
        organization.initializeLocations();
    }

    public String getUsername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}