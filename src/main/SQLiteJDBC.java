package main;
import java.sql.*;
import java.nio.file.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import tdg.*;

public class SQLiteJDBC {

    public static void main(String args[]) {
        // Initialize Registries
        BookingsRegistry bookingsRegistry = new BookingsRegistry();
        LocationsRegistry locationsRegistry = new LocationsRegistry();
        OfferingsRegistry offeringsRegistry = new OfferingsRegistry();
        ClientsRegistry clientsRegistry = new ClientsRegistry();
        LessonsRegistry lessonsRegistry = new LessonsRegistry();
        InstructorsRegistry instructorsRegistry = new InstructorsRegistry();

        // Create TimeSlot objects for the schedules
        TimeSlot timeSlot1 = new TimeSlot("Monday", LocalTime.of(9, 0), LocalTime.of(10, 0));
        TimeSlot timeSlot2 = new TimeSlot("Tuesday", LocalTime.of(11, 0), LocalTime.of(12, 0));
        TimeSlot timeSlot3 = new TimeSlot("Wednesday", LocalTime.of(13, 0), LocalTime.of(14, 0));
        TimeSlot timeSlot4 = new TimeSlot("Thursday", LocalTime.of(15, 0), LocalTime.of(16, 0));

        // Create Schedule objects
        Schedule schedule1 = new Schedule();
        schedule1.addTimeSlot(timeSlot1);
        schedule1.addTimeSlot(timeSlot2);

        Schedule schedule2 = new Schedule();
        schedule2.addTimeSlot(timeSlot3);
        schedule2.addTimeSlot(timeSlot4);
        try {

            // Class.forName("org.sqlite.JDBC");
            // Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            // Initialize TDGs and create tables
            LocationTDG locationTDG = new LocationTDG();
            locationTDG.createTable();

            OfferingTDG offeringTDG = new OfferingTDG();
            offeringTDG.createTable();

            LessonTDG lessonTDG = new LessonTDG();
            lessonTDG.createTable();

            InstructorTDG instructorTDG = new InstructorTDG();
            instructorTDG.createTable();

            BookingTDG bookingTDG = new BookingTDG();
            bookingTDG.createTable();

            ClientTDG clientTDG = new ClientTDG();
            clientTDG.createTable();

            // Create Location objects
            Location location1 = new Location("Loc1", "Main Building 123 Main St", "New York", "Room 101", schedule1);
            Location location2 = new Location("Loc2", "Annex Building 456 Side St", "Los Angeles", "Room 202", schedule2);

            // Add them to table location
            locationsRegistry.createLocation(location1);
            locationsRegistry.createLocation(location2);

            // // Test LessonTDG
            // System.out.println(location1.getID());
            // Lesson lesson1 = new Lesson("Math", "Math Lesson", location1, timeSlot1);
            // lessonTDG.insert(lesson1.toParams());
            // lessonTDG.printAllLessons();
            // lesson1.setTitle("Advanced Math");
            // lessonTDG.update(lesson1.toParams());
            // lessonTDG.printAllLessons();
            // lessonTDG.delete(lesson1.getID());
            // lessonTDG.printAllLessons();

            // Test InstructorTDG and InstructorsRegistry
            Instructor instructor1 = new Instructor("John Doe", "1234567890", 30, "password123");
            instructor1.addCity("New York");
            instructor1.addCity("Los Angeles");
            instructorsRegistry.createInstructor(instructor1);
            instructor1.setName("John Smith");
            // Test InstructorTDG and InstructorsRegistry
            Instructor instructor2= new Instructor("Francois Leclerc", "1234567890", 30, "password123");
            instructor2.addCity("Montreal");
            instructor2.addCity("Toronto");
            instructorsRegistry.createInstructor(instructor2);
            
            instructorsRegistry.updateInstructor(0, instructor1);
            

            // // Test BookingTDG and BookingRegistry
            // Booking booking1 = new Booking( new Offering(lesson1, instructor1), new Client("Jane Doe", "9876543210", 25, "password456"));
            // bookingsRegistry.createBooking(booking1);
            // System.out.println(bookingsRegistry.getAllBookingsDescriptions());
            // booking1.setOffering(new Offering( lesson1, instructor1));
            // bookingsRegistry.updateBooking(0, booking1);
            // System.out.println(bookingsRegistry.getAllBookingsDescriptions());
            // System.out.println(bookingsRegistry.getAllBookingsDescriptions());

            // // Test ClientTDG and ClientRegistry
            // Client client1 = new Client("Jane Doe", "9876543210", 25, "password456");
            // clientsRegistry.createClient(client1);
            // System.out.println(clientsRegistry.getAllClientsDescriptions());
            // client1.setName("Jane Jack");
            // clientsRegistry.updateClient(0, client1);
            // System.out.println(clientsRegistry.getAllClientsDescriptions());
            // // clientsRegistry.deleteClient(client1);
            // System.out.println(clientsRegistry.getAllClientsDescriptions());

            // // Test OfferingTDG and OfferingRegistry
            // Offering offering1 = new Offering( lesson1, instructor1);
            // offeringsRegistry.createOffering(offering1);
            // System.out.println(offeringsRegistry.getAllOfferingDescriptions());
            // offering1.setBooked(true);
            // offeringsRegistry.updateOffering(0, offering1);
            // System.out.println(offeringsRegistry.getAllOfferingDescriptions());
            // offeringsRegistry.deleteOffering(offering1);
            // System.out.println(offeringsRegistry.getAllOfferingDescriptions());

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        // Create Instructors for testing purposes with ids from 0 to 100
        // for (int i = 0; i < 100; i++) {
        //     try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
        //         System.out.println("Im thread 1 yipee");
        //         Instructor ins = new Instructor("John Doe", "1234567890", 30, "password123");
        //         ins.setID(String.valueOf(i));
        //         instructorsRegistry.createInstructor(ins);
        //     } catch (SQLException e) {
        //         e.printStackTrace();
        //     }
        // }
        

        // List<Instructor> Thread2Instructors = new ArrayList<>();
        // Thread snatchThread2 = new Thread(() -> {
        //     while(!instructorsRegistry.isEmpty()){
        //         try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
        //             System.out.println("Im thread 2 yipee");
        //             Instructor instructor= instructorsRegistry.snatch();
        //             Thread2Instructors.add(instructor);
        //         } catch (SQLException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });

        // List<Instructor> Thread3Instructors = new ArrayList<>();
        // Thread snatchThread3 = new Thread(() -> {
        //     while(!instructorsRegistry.isEmpty()){
        //         try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
        //             System.out.println("Im thread 3 yipee");
        //             Instructor instructor= instructorsRegistry.snatch();
        //             Thread3Instructors.add(instructor);
        //         } catch (SQLException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });

        // // Start threads
        // snatchThread2.start();
        // snatchThread3.start();

        // // Wait for threads to finish
        // try {
        //     snatchThread2.join();
        //     snatchThread3.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // System.out.println("*********THESE ARE THE IDS OF THREAD 2");
        // for (Instructor instructor : Thread2Instructors) {
        //     System.out.print(instructor.getID()+", ");
        // }
        // System.out.println("*******THESE ARE THE IDS OF THREAD 3");
        // for (Instructor instructor : Thread3Instructors) {
        //     System.out.print(instructor.getID()+", ");
        // }


        // Thread readThread = new Thread(() -> {
        //     for (int i = 0; i < 100; i++) {
        //         try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
        //             String query = "SELECT * FROM Instructor LIMIT 1";
        //             try (PreparedStatement pstmt = c.prepareStatement(query);
        //                  ResultSet rs = pstmt.executeQuery()) {
        //                 if (rs.next()) {
        //                     // Read values from the result set
        //                     String id = rs.getString("ID");
        //                     String name = rs.getString("NAME");
        //                     String phoneNumber = rs.getString("PHONE_NUMBER");
        //                     int age = rs.getInt("AGE");
        //                     String password = rs.getString("PASSWORD");
        //                     String salt = rs.getString("SALT");
        //                     String cities = rs.getString("CITIES");
        //                     System.out.println("IM THREAD 1 AND I JUST READ "+ id+" whose name is"+name +" NOW I HOLD LOCK");

        //                     Thread.sleep(10000);
        //                     System.out.println("Im thread 1 and I release lock");

        //                     // Values are read but not printed
        //                 }
        //             } 
        //         } catch (SQLException |InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });
        // Thread readThread2 = new Thread(() -> {
        //     for (int i = 0; i < 100; i++) {
        //         try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
        //             String query = "SELECT * FROM Instructor LIMIT 1";
        //             try (PreparedStatement pstmt = c.prepareStatement(query);
        //                  ResultSet rs = pstmt.executeQuery()) {
        //                 if (rs.next()) {
        //                     // Read values from the result set
        //                     String id = rs.getString("ID");
        //                     String name = rs.getString("NAME");
        //                     String phoneNumber = rs.getString("PHONE_NUMBER");
        //                     int age = rs.getInt("AGE");
        //                     String password = rs.getString("PASSWORD");
        //                     String salt = rs.getString("SALT");
        //                     String cities = rs.getString("CITIES");

        //                     System.out.println("IM THREAD 2 AND I JUST READ "+ id+" whose name is"+name +" NOW I HOLD LOCK");
        //                     Thread.sleep(10000);
        //                     System.out.println("Im thread 2 and I release lock");


        //                     // Values are read but not printed
        //                 }
        //             }
        //         } catch (SQLException |InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });
        // Thread readThread3= new Thread(() -> {
        //     for (int i = 0; i < 100; i++) {
        //         try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
        //             String query = "SELECT * FROM Instructor LIMIT 1";
        //             try (PreparedStatement pstmt = c.prepareStatement(query);
        //                  ResultSet rs = pstmt.executeQuery()) {
        //                 if (rs.next()) {
        //                     // Read values from the result set
        //                     String id = rs.getString("ID");
        //                     String name = rs.getString("NAME");
        //                     String phoneNumber = rs.getString("PHONE_NUMBER");
        //                     int age = rs.getInt("AGE");
        //                     String password = rs.getString("PASSWORD");
        //                     String salt = rs.getString("SALT");
        //                     String cities = rs.getString("CITIES");
        //                     System.out.println("IM THREAD 3 AND I JUST READ "+ id+" whose name is"+name+" NOW I HOLD LOCK");
        //                     Thread.sleep(10000);
        //                     System.out.println("Im thread 3 and I release lock");

        //                     // Values are read but not printed
        //                 }
        //             }
        //         } catch (SQLException | InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });
        // Thread readThread4 = new Thread(() -> {
        //     for (int i = 0; i < 100; i++) {
        //         try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
        //             String query = "SELECT * FROM Instructor LIMIT 1";
        //             try (PreparedStatement pstmt = c.prepareStatement(query);
        //                  ResultSet rs = pstmt.executeQuery()) {
        //                 if (rs.next()) {
        //                     // Read values from the result set
        //                     String id = rs.getString("ID");
        //                     String name = rs.getString("NAME");
        //                     String phoneNumber = rs.getString("PHONE_NUMBER");
        //                     int age = rs.getInt("AGE");
        //                     String password = rs.getString("PASSWORD");
        //                     String salt = rs.getString("SALT");
        //                     String cities = rs.getString("CITIES");
        //                     System.out.println("IM THREAD 4 AND I JUST READ "+ id+" whose name is"+name+" NOW I HOLD LOCK");
        //                     Thread.sleep(10000);
        //                     System.out.println("Im thread 4 and I release lock");
        //                     // Values are read but not printed
        //                 }
        //             }
        //         } catch (SQLException | InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });

        // readThread.start();
        // readThread2.start();
        // readThread3.start();
        // readThread4.start();

        // try {
        //     readThread.join();
        //     readThread2.join();
        //     readThread3.join();
        //     readThread4.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        
    }

    public static void executeSQLFile(Connection c, String filePath) throws IOException, SQLException {
        Statement stmt = null;
        try {
            // Implementation for executing SQL file
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    
    

}