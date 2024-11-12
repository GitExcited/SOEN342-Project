package main;
import java.sql.*;

import tdg.OfferingTDG;

import java.nio.file.*;
import java.io.IOException;

public class SQLiteJDBC {

   //TODO: As we implement more tdgs, each has to create its own table.
   // public InitializeDatabase(Offerings o, LocationRegistry lo, Lessons le, InstructorsRegistry i, ClientsRegistry c, BookingsRegistry b){
   //    o.getOfferingTDG().createTable();
   //    lo
   // }

   public static void main(String args[]) {
      try {
         Class.forName("org.sqlite.JDBC");
         Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");


         OfferingsRegistry offs = new OfferingsRegistry();
         offs.getTDG().createTable();

         // dropTable(c, "OFFERING");
         // Create Offering objects
         Lesson lesson1 = new Lesson(null, null, null, null);
         Instructor instructor1 = new Instructor("I1", "John Doe", 0, null);
         Offering offering1 = new Offering( lesson1, instructor1);

         offs.addOffering(offering1);

         // Print all offerings
         offs.getTDG().printAllOfferings();

      } catch (ClassNotFoundException | SQLException e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }
      System.out.println("Operation done successfully");
   }

   public static void executeSQLFile(Connection c, String filePath) throws IOException, SQLException {
      Statement stmt = null;
      try {
         stmt = c.createStatement();
         String sql = new String(Files.readAllBytes(Paths.get(filePath)));
         for (String query : sql.split(";")) {
            if (!query.trim().isEmpty()) {
               stmt.executeUpdate(query.trim() + ";");
            }
         }
         stmt.close();
      } catch (SQLException | IOException e) {
         if (stmt != null) {
            stmt.close();
         }
         throw e;
      }
   }

   public static void dropTable(Connection c, String tableName) throws SQLException {
      Statement stmt = null;
      try {
         stmt = c.createStatement();
         String sql = "DROP TABLE IF EXISTS " + tableName;
         stmt.executeUpdate(sql);
      } finally {
         if (stmt != null) {
            stmt.close();
         }
      }
   }
}