package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AppSystem appSystem = new AppSystem();
        Scanner in = new Scanner(System.in);
        
        String userInput = "";
        boolean running = true;
        while(running){
            if (appSystem.isUserAuthenticated()){
                System.out.println("""
                        Welcome to the lesson booking system please select an action:
                        1) Login client
                        2) Login instructor
                        3) Login admin 
                        4) Register client
                        5) Register Instructor
                        6) View public offerings
                        """);
                userInput = in.nextLine();
                processUserInput(userInput, UserAuthLevel.NotAuthorized, in, appSystem);
                userInput = "";
            }else{
                switch (appSystem.getUserAuthLevel()) {
                    case Client:
                    System.out.println("""
                        Logged in as Client, please choose an action:
                        1) View public offerings
                        2) Process new Bookings
                        3) View your Bookings
                        4) Cancel Booking
                        5) Logout
                        """);
                    userInput = in.nextLine();
                    processUserInput(userInput, UserAuthLevel.Client, in, appSystem);
                    userInput = "";
                        break;
                    case Instructor:
                    System.out.println("""
                        Logged in as Instructor, please choose an action:
                        1) Take Offerings
                        2) View taken Offerings
                        3) Cancel Offering Selection
                        4) Logout
                        """);
                        userInput = in.nextLine();
                        processUserInput(userInput, UserAuthLevel.Instructor, in, appSystem);
                        userInput = "";
                        break;
                    case Admin:
                    System.out.println("""
                        Logged in as Admin, please choose an action:
                        1) Create Offering
                        2) Delete Offering
                        3) Delete Instructor Account
                        4) Delete Client Account
                        5) Logout
                        """);
                        userInput = in.nextLine();
                        processUserInput(userInput, UserAuthLevel.Admin, in, appSystem);
                        userInput = "";
                        break;
                    default:
                        break;
                }
            }
        }
        in.close();
    }

    private static void processUserInput(String userInput, UserAuthLevel authLevel, Scanner in, AppSystem appSystem) {
        switch (authLevel) {
            case NotAuthorized:
                String username;
                String password;
                boolean loginSuccessful;
                boolean registerSuccessful;
                String name; 
                String age;
                String phoneNumber;
                String LessonId;
                String LocationId;
                String time;
                switch (userInput) {
                    case "1": //Login Client
                        System.out.println("Client Login: ");
                        System.out.println("Enter your username. >> ");
                        username = in.nextLine();
                        System.out.println("Enter your password. >> ");
                        password = in.nextLine();
                        loginSuccessful = appSystem.loginClient(username, password);
                        if (loginSuccessful){
                            System.out.println("Login successful.");
                        }else{
                            System.out.println("Login failed. Please try again.");
                        }
                        break;
                    case "2": //Login Instructor
                        System.out.println("Instructor Login: ");
                        System.out.println("Enter your username. >> ");
                        username = in.nextLine();
                        System.out.println("Enter your password. >> ");
                        password = in.nextLine();
                        loginSuccessful = appSystem.loginInstructor(username, password);
                        if (loginSuccessful){
                            System.out.println("Login Successful.");
                        }else{
                            System.out.println("Login failed. Please try again.");
                        }
                        break;
                    case "3": //Login Admin
                        System.out.println("Admin Login: ");
                        System.out.println("Enter your username. >> ");
                        username = in.nextLine();
                        System.out.println("Enter your password. >> ");
                        password = in.nextLine();
                        loginSuccessful = appSystem.loginAdmin(username, password);
                        if (loginSuccessful){
                            System.out.println("Login Successful.");
                        }else{
                            System.out.println("Login failed. Please try again.");
                        }
                        break;
                    case "4": //Register Client
                    try {
                        System.out.println("Register Client: ");
                        System.out.println("Enter your name. >>");
                        name = in.nextLine();
                        System.out.println("Enter your phone number. >>");
                        phoneNumber = in.nextLine();
                        System.out.println("Enter your age. >>");
                        age = in.nextLine();
                        System.out.println("Enter your password. >> ");
                        password = in.nextLine();
                        registerSuccessful = appSystem.registerUser(name, phoneNumber, Integer.parseInt(age), password);
                        if (registerSuccessful){
                            System.out.println("Registering Successful. Please login now.");
                        }else{
                            System.out.println("Registering failed. Username or Phone Number is already in use.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                        break; 
                    case "5": //Register Instructor
                        try {
                            System.out.println("Register Instructor: ");
                            System.out.println("Enter your name. >>");
                            name = in.nextLine();
                            System.out.println("Enter your phone number. >>");
                            phoneNumber = in.nextLine();
                            System.out.println("Enter your name. >>");
                            age = in.nextLine();
                            System.out.println("Enter your password. >> ");
                            password = in.nextLine();
                            registerSuccessful = appSystem.registerInstructor(name, phoneNumber, Integer.parseInt(age), password);
                            if (registerSuccessful){
                                System.out.println("Registering Successful. Please login now.");
                            }else{
                                System.out.println("Registering failed. Please try again.");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "6": //View public Offerings
                        System.out.println("Available public offerings: ");
                        System.out.println(appSystem.browsePublicOfferings());
                        break;
                    default:
                        System.out.println("Invalid choice, try again.");
                        break;
                }
                break;
            case Client:
            switch (userInput) {
                case "1": //View public offerings 
                    System.out.println("Available public offerings: ");
                    System.out.println(appSystem.browsePublicOfferings());
                    break;
                case "2": //Process Booking
                    appSystem.processNewBookings();
                    break;
                case "3": //View Bookings
                    System.out.println("Your active Bookings: ");
                    System.out.println(appSystem.getCurrentUserBookings());
                    break;
                case "4": //Cancel Booking
                    appSystem.deleteBooking();
                    break;
                case "5": //Logout
                    appSystem.Logout();
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    break;
            }
            case Instructor:
            switch (userInput) {
                case "1": //Take Offering RENAMED to viewOfferings
                    appSystem.viewOfferings();
                    break;
                case "2": //View taken offering DONE
                    System.out.println("Your active selected Offerings: ");
                    System.out.println(appSystem.getCurrentUserSelectedOfferings());
                    break;
                case "3": //Cancel offering selection
                    break;
                case "4": //Logout
                    appSystem.Logout();
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    break;
            }
                break;
            case Admin:
            String Id;
            boolean result;
            switch (userInput) {
                case "1": //Create Offering DONE
                    System.out.println(appSystem.getAllOfferingsToString());
                    System.out.println(" Enter a lesson ID. >>");
                    LessonId = in.nextLine();
                    System.out.println(appSystem.getAllOfferingsToString());
                    System.out.println(" Enter a location ID. >>");
                    LocationId = in.nextLine();
                    System.out.println(" Enter a day of the week, a start time and a finish time of format \"HH:mm\" >>");
                    time = in.nextLine();
                    result = appSystem.createOffering(LessonId, LocationId, time);
                    if (result){
                        System.out.println("Offering created successfully.");
                    }else{
                        System.out.println("Creating Offering Failed.");
                    }
                    break; 
                case "2": //Delete Offering DONE
                    System.out.println(appSystem.getAllOfferingsToString());
                    System.out.println(" Enter ID of Offering to delete. >>");
                    Id = in.nextLine();
                    result = appSystem.deleteOffering(Id);//DeleteOffering takes int 
                    if (result){
                        System.out.println("Delete Successful.");
                    }else{
                        System.out.println("Delete Failed.");
                    }
                    break;
                case "3": //Delete Instructor DONE
                    System.out.println(appSystem.getAllInstructorsToString());
                    System.out.println(" Enter ID of Instructor account to delete. >>");
                    Id = in.nextLine();
                    result = appSystem.deleteInstructor(Id);
                    if (result){
                        System.out.println("Delete Successful.");
                    }else{
                        System.out.println("Delete Failed.");
                    }
                    break;
                case "4": //Delete Client DONE
                    System.out.println(appSystem.getAllClientsToString());
                    System.out.println(" Enter ID of Client account to delete. >>");
                    Id = in.nextLine();
                    result = appSystem.deleteClient(Id);
                    if (result){
                        System.out.println("Delete Successful.");
                    }else{
                        System.out.println("Delete Failed.");
                    }
                    break;
                case "5": //Logout
                    appSystem.Logout();
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    break;
            }
                break;
            default:
                break;
        }
    }
}