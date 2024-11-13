package main;
import java.sql.*;
import java.nio.file.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
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
            // Drop Database ( uncomment this and comment the rest)
            // closeDatabaseConnection("jdbc:sqlite:test.db");
            // deleteDatabaseFile("test.db");

            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
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

            // Test LessonTDG
            System.out.println(location1.getID());
            Lesson lesson1 = new Lesson("Math", "Math Lesson", location1, timeSlot1);
            lessonTDG.insert(lesson1.toParams());
            lessonTDG.printAllLessons();
            lesson1.setTitle("Advanced Math");
            lessonTDG.update(lesson1.toParams());
            lessonTDG.printAllLessons();
            lessonTDG.delete(lesson1.getID());
            lessonTDG.printAllLessons();

            // Test InstructorTDG and InstructorsRegistry
            Instructor instructor1 = new Instructor("John Doe", "1234567890", 30, "password123");
            instructor1.addCity("New York");
            instructor1.addCity("Los Angeles");
            instructorsRegistry.createInstructor(instructor1);
            System.out.println(instructorsRegistry.getAllInstructorsDescriptions());
            instructor1.setName("John Smith");
            instructorsRegistry.updateInstructor(0, instructor1);
            System.out.println(instructorsRegistry.getAllInstructorsDescriptions());
            instructorsRegistry.deleteInstructor(instructor1);
            System.out.println(instructorsRegistry.getAllInstructorsDescriptions());

            // Test BookingTDG and BookingRegistry
            Booking booking1 = new Booking( new Offering(lesson1, instructor1), new Client("Jane Doe", "9876543210", 25, "password456"));
            bookingsRegistry.createBooking(booking1);
            System.out.println(bookingsRegistry.getAllBookingsDescriptions());
            booking1.setOffering(new Offering( lesson1, instructor1));
            bookingsRegistry.updateBooking(0, booking1);
            System.out.println(bookingsRegistry.getAllBookingsDescriptions());
            System.out.println(bookingsRegistry.getAllBookingsDescriptions());

            // Test ClientTDG and ClientRegistry
            Client client1 = new Client("Jane Doe", "9876543210", 25, "password456");
            clientsRegistry.createClient(client1);
            System.out.println(clientsRegistry.getAllClientsDescriptions());
            client1.setName("Jane Jack");
            clientsRegistry.updateClient(0, client1);
            System.out.println(clientsRegistry.getAllClientsDescriptions());
            clientsRegistry.deleteClient(client1);
            System.out.println(clientsRegistry.getAllClientsDescriptions());

            // Test OfferingTDG and OfferingRegistry
            Offering offering1 = new Offering( lesson1, instructor1);
            offeringsRegistry.createOffering(offering1);
            System.out.println(offeringsRegistry.getAllOfferingDescriptions());
            offering1.setBooked(true);
            offeringsRegistry.updateOffering(0, offering1);
            System.out.println(offeringsRegistry.getAllOfferingDescriptions());
            offeringsRegistry.deleteOffering(offering1);
            System.out.println(offeringsRegistry.getAllOfferingDescriptions());

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        // Create threads
        Thread insertThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    System.out.println("Im thread 1 yipee");
                    instructorsRegistry.createInstructor(new Instructor("John Doe", "1234567890", 30, "password123"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread deleteThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    System.out.println("Im thread 2 yipee");
                    instructorsRegistry.deleteInstructor("1");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread readThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    System.out.println("Im thread 3 yipee");
                    Instructor instructor = instructorsRegistry.getInstructorById("1");
                    if (instructor != null) {
                        System.out.println("Read instructor: " + instructor.getName());
                    } else {
                        System.out.println("Instructor not found");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start threads
        insertThread.start();
        deleteThread.start();
        readThread.start();

        // Wait for threads to finish
        try {
            insertThread.join();
            deleteThread.join();
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
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

 
    private static void closeDatabaseConnection(String dbUrl) {
        try (Connection c = DriverManager.getConnection(dbUrl)) {
            // Closing the connection
            if (c != null && !c.isClosed()) {
                c.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void deleteDatabaseFile(String dbName) {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            if (dbFile.delete()) {
                System.out.println("Database file deleted successfully.");
            } else {
                System.out.println("Failed to delete the database file.");
            }
        } else {
            System.out.println("Database file does not exist.");
        }
    }

    

}