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
                processUserInput(userInput, appSystem.getUserAuthLevel());
                userInput = "";
            }else{
                switch (appSystem.getUserAuthLevel()) {
                    case Client:
                    System.out.println("""
                        Logged in as Client, please choose an action:
                        1) View public offerings
                        2) Process Bookings
                        3) View Bookings
                        """);
                    userInput = in.nextLine();
                    processUserInput(userInput, UserAuthLevel.Client);
                    userInput = "";
                        break;
                    case Instructor:
                    System.out.println("""
                        Logged in as Instructor, please choose an action:
                        1) Take Offerings
                        2) View taken Offerings
                        """);
                        userInput = in.nextLine();
                        processUserInput(userInput, UserAuthLevel.Instructor);
                        userInput = "";
                        break;
                    case Admin:
                    System.out.println("""
                        Logged in as Admin, please choose an action:
                        1) Create Offering
                        2) Delete Offering
                        3) Delete Instructor Account
                        4) Delete Client Account
                        """);
                        userInput = in.nextLine();
                        processUserInput(userInput, UserAuthLevel.Admin);
                        userInput = "";
                        break;
                    default:
                        break;
                }
            }
        }
        in.close();
    }

    private static void processUserInput(String userInput, UserAuthLevel authLevel) {
        switch (authLevel) {
            case Client:
                
                break;
            case Instructor:
                
                break;
            case Admin:
                
                break;
            default:
                break;
        }
    }
}