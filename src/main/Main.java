package main;

import java.util.Scanner;

public class Main {
    static boolean running = true;
    public static void main(String[] args) {
        AppSystem appSystem = new AppSystem();
        Scanner in = new Scanner(System.in);
        
        String userInput = "";
        while(running){
            //System.out.println("Debug: running main loop");
            if (!appSystem.isUserAuthenticated()){
                System.out.println("""
                        Welcome to the lesson booking system please select an action:
                        1) Login client
                        2) Login instructor
                        3) Login admin 
                        4) Register client
                        5) Register Instructor
                        6) View Offerings
                        7) Exit
                        """);
                userInput = in.nextLine();
                processUserInput(userInput, UserAuthLevel.NotAuthorized, in, appSystem);
                userInput = "";
            }else{
                switch (appSystem.getUserAuthLevel()) {
                    case Client:
                    System.out.println("""
                        Logged in as Client, please choose an action:
                        1) View Offerings
                        2) Process new Bookings
                        3) View your Bookings
                        4) Cancel Booking
                        5) Process new Bookings for child
                        6) Cancel Booking for child
                        7) Logout
                        """);
                    userInput = in.nextLine();
                    processUserInput(userInput, UserAuthLevel.Client, in, appSystem);
                    userInput = "";
                        break;
                    case ClientMinor:
                    System.out.println("""
                        Logged in as Client, please choose an action:
                        1) View Offerings
                        2) View your Bookings
                        3) Logout
                        """);
                    userInput = in.nextLine();
                    processUserInput(userInput, UserAuthLevel.ClientMinor, in, appSystem);
                    userInput = "";
                            break;
                    case Instructor:
                    System.out.println("""
                        Logged in as Instructor, please choose an action:
                        1) Select Lesson
                        2) View Selected Lessons
                        3) Cancel Lesson Selection
                        4) Logout
                        """);
                        userInput = in.nextLine();
                        processUserInput(userInput, UserAuthLevel.Instructor, in, appSystem);
                        userInput = "";
                        break;
                    case Admin:
                    System.out.println("""
                        Logged in as Admin, please choose an action:
                        1) Create Lesson
                        2) Delete Lesson
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
                String password;
                boolean loginSuccessful;
                boolean registerSuccessful;
                String name; 
                String age;
                String phoneNumber;
                String LessonId;
                String LocationId;
                String BookingId;
                String time;
                String Id;
                boolean result;
                switch (userInput) {
                    case "1": //Login Client
                        System.out.println("Client Login: ");
                        System.out.println("Enter your name. >> ");
                        name = in.nextLine();
                        System.out.println("Enter your password. >> ");
                        password = in.nextLine();
                        loginSuccessful = appSystem.loginClient(name, password);
                        if (loginSuccessful){
                            System.out.println("Login successful.");
                        }else{
                            System.out.println("Login failed. Please try again.");
                        }
                        System.out.println("\n");
                        break;
                    case "2": //Login Instructor
                        System.out.println("Instructor Login: ");
                        System.out.println("Enter your name. >> ");
                        name = in.nextLine();
                        System.out.println("Enter your password. >> ");
                        password = in.nextLine();
                        loginSuccessful = appSystem.loginInstructor(name, password);
                        if (loginSuccessful){
                            System.out.println("Login Successful.");
                        }else{
                            System.out.println("Login failed. Please try again.");
                        }
                        System.out.println("\n");
                        break;
                    case "3": //Login Admin
                        System.out.println("Admin Login: ");
                        System.out.println("Enter your password. >> ");
                        password = in.nextLine();
                        loginSuccessful = appSystem.loginAdmin(password);
                        if (loginSuccessful){
                            System.out.println("Login Successful.");
                        }else{
                            System.out.println("Login failed. Please try again.");
                        }
                        System.out.println("\n");
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
                        String guardian = "";
                        if(Integer.parseInt(age) < 18){
                            System.out.println(appSystem.getAllClientsToString());
                            System.out.println("Enter the Id of your guardian>>");
                            guardian = in.nextLine();
                        }
                        System.out.println("Enter your password. >> ");
                        password = in.nextLine();
                        System.out.println(appSystem.registerUser(name, phoneNumber, Integer.parseInt(age), password, guardian));
                        System.out.println("\n");
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
                            System.out.println("Enter your age. >>");
                            age = in.nextLine();
                            System.out.println("Enter your password. >> ");
                            password = in.nextLine();
                            System.out.println(appSystem.registerInstructor(name, phoneNumber, Integer.parseInt(age), password));
                            System.out.println("\n");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "6": //View Offerings
                        System.out.println("Available public offerings: ");
                        System.out.println("\n");
                        break;
                    case "7": //View Offerings
                        System.out.println("Thank you for using our System!.");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice, try again.");
                        System.out.println("\n");
                        break;
                }
                break;
            case Client:
            switch (userInput) {
                case "1": //View Offerings Done
                    System.out.println("Available Offerings: ");
                    System.out.println(appSystem.getAllnonBookedOfferingsToString());
                    System.out.println("\n");
                    break;
                case "2": //Process Booking
                    System.out.println("Available Offerings: ");
                    System.out.println(appSystem.getAllnonBookedOfferingsToString());
                    System.out.println("Enter ID of the Offering to select. >>");
                    Id = in.nextLine();
                    System.out.println(appSystem.selectOffering(Id));
                    System.out.println("\n");
                    break;
                case "3": //View Bookings
                    System.out.println("Your active Bookings: ");
                    System.out.println(appSystem.getCurrentUserBookings());
                    System.out.println("\n");
                    break;
                case "4": //Cancel Booking
                    System.out.println("Your active Bookings: ");
                    System.out.println(appSystem.getCurrentUserBookings());
                    System.out.println("Enter ID of the Booking to delete. >>");
                    Id = in.nextLine();
                    System.out.println(appSystem.deleteBooking(Id));
                    System.out.println("\n");
                    break;
                case "5": //book for child DONE
                    System.out.println("Children you are responsible of: ");
                    System.out.println(appSystem.getCurrentResponsibleChildren());
                    System.out.println("Enter the ID of the child to create a booking for: ");
                    Id = in.nextLine();
                    System.out.println("Available Offerings: ");
                    System.out.println(appSystem.getAllnonBookedOfferingsToString());
                    System.out.println("Enter ID of the Offering to select. >>");
                    BookingId = in.nextLine();
                    System.out.println(appSystem.selectOffering(BookingId, Id));
                    System.out.println("\n");
                    break;
                case "6": //Cancel for child DONE
                    System.out.println("Children you are responsible of: ");
                    System.out.println(appSystem.getCurrentResponsibleChildren());
                    System.out.println("Enter the ID of the child to delete a booking for: ");
                    Id = in.nextLine();
                    System.out.println("Active Bookings of the child: ");
                    System.out.println(appSystem.getCurrentUserBookings(Id));
                    System.out.println("Enter ID of the Booking to select. >>");
                    BookingId = in.nextLine();
                    System.out.println(appSystem.deleteBooking(Id));
                    System.out.println("\n");
                    break;
                case "7": //Logout
                    appSystem.Logout();
                    System.out.println("\n");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    System.out.println("\n");
                    break;
            }
                break;
            case ClientMinor:
            switch (userInput) {
                case "1": //View Offerings Done
                    System.out.println("Available Offerings: ");
                    System.out.println(appSystem.getAllnonBookedOfferingsToString());
                    System.out.println("\n");
                    break;
                case "2": //View Bookings
                    System.out.println("Your active Bookings: ");
                    System.out.println(appSystem.getCurrentUserBookings());
                    System.out.println("\n");
                    break;
                case "3": //Logout
                    appSystem.Logout();
                    System.out.println("\n");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    System.out.println("\n");
                    break;
            }
                break;
            case Instructor:
            switch (userInput) {
                case "1": //Select Lesson
                    System.out.println(appSystem.getAllLessonsToString());
                    System.out.println("Enter ID of the Lesson to select. >>");
                    Id = in.nextLine();
                    result = appSystem.selectLesson(Id);//DeleteOffering takes int 
                    if (result){
                        System.out.println("Successfully Selected Lesson.");
                    }else{
                        System.out.println("Failed to Select Lesson.");
                    }
                    System.out.println("\n");
                    break;
                case "2": //View Selected Lesson
                    System.out.println("Your active selected Lessons: ");
                    System.out.println(appSystem.getCurrentUserSelectedLessons());
                    System.out.println("\n");
                    break;
                case "3": //Cancel Lesson selection
                    System.out.println("Your active selected Lessons: ");
                    System.out.println(appSystem.getCurrentUserSelectedLessons());
                    System.out.println("Enter ID of Lesson to cancel. >>");
                    Id = in.nextLine();
                    result = appSystem.cancelLessonSelection(Id);//DeleteOffering takes int 
                    if (result){
                        System.out.println("Successfully Deleted Selected Lesson.");
                    }else{
                        System.out.println("Failed to Delete Selected Lesson.");
                    }
                    System.out.println("\n");
                    break;
                case "4": //Logout
                    appSystem.Logout();
                    System.out.println("\n");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    System.out.println("\n");
                    break;
            }
                break;
            case Admin:
            String title;
            String description;
            switch (userInput) {
                case "1": //Create Lesson DONE
                    System.out.println("Enter the Lesson's title. >>");
                    title = in.nextLine();
                    System.out.println("Enter the Lesson's description. >>");
                    description = in.nextLine();
                    System.out.println(appSystem.getAllLocationsToString());
                    System.out.println("Enter a location ID. >>");
                    LocationId = in.nextLine();
                    System.out.println("Enter a day of the week, a start time and a finish time of format \"HH:mm\" >>");
                    time = in.nextLine();
                    result = appSystem.createLesson(title, description, LocationId, time);
                    if (result){
                        System.out.println("Lesson created successfully.");
                    }else{
                        System.out.println("Creating Lesson Failed.");
                    }
                    System.out.println("\n");
                    break; 
                case "2": //Delete Lesson DONE
                    System.out.println(appSystem.getAllLessonsToString());
                    System.out.println("Enter ID of Lesson to delete. >>");
                    Id = in.nextLine();
                    result = appSystem.deleteLesson(Id);//DeleteOffering takes int 
                    if (result){
                        System.out.println("Delete Successful.");
                    }else{
                        System.out.println("Delete Failed.");
                    }
                    System.out.println("\n");
                    break;
                case "3": //Delete Instructor DONE
                    System.out.println(appSystem.getAllInstructorsToString());
                    System.out.println("Enter ID of Instructor account to delete. >>");
                    Id = in.nextLine();
                    result = appSystem.deleteInstructor(Id);
                    if (result){
                        System.out.println("Delete Successful.");
                    }else{
                        System.out.println("Delete Failed.");
                    }
                    System.out.println("\n");
                    break;
                case "4": //Delete Client DONE
                    System.out.println(appSystem.getAllClientsToString());
                    System.out.println("Enter ID of Client account to delete. >>");
                    Id = in.nextLine();
                    result = appSystem.deleteClient(Id);
                    if (result){
                        System.out.println("Delete Successful.");
                    }else{
                        System.out.println("Delete Failed.");
                    }
                    System.out.println("\n");
                    break;
                case "5": //Logout
                    appSystem.Logout();
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
                    System.out.println("\n");
                    break;
            }
                break;
            default:
                break;
        }
    }
}