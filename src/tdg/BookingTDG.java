package tdg;
import java.sql.*;

public class BookingTDG {

    private Connection connection;

    /**
     * Constructor to initialize the database connection.
     *
     * @param connection the database connection
     */
    public BookingTDG() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:test.db");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates the BOOKING table if it does not already exist.
     *
     * @throws SQLException if a database access error occurs
     */
    public void createTable() throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS BOOKING " +
                         "(ID TEXT PRIMARY KEY NOT NULL," +
                         " CLIENT_ID TEXT NOT NULL, " + 
                         " OFFERING_ID TEXT NOT NULL, " +
                         " FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT(ID), " +
                         " FOREIGN KEY (OFFERING_ID) REFERENCES OFFERING(ID))";
            stmt.executeUpdate(sql);
        } finally {
            closeResources(stmt);
        }
    }

    /**
     * Inserts a new booking into the BOOKING table.
     *
     * @param params the parameters for the new booking
     * @throws SQLException if a database access error occurs
     */
    public void insert(Object... params) throws SQLException {
        String sql = "INSERT INTO BOOKING (ID, CLIENT_ID, OFFERING_ID) VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[0]);
            pstmt.setString(2, (String) params[1]);
            pstmt.setString(3, (String) params[2]);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    /**
     * Updates an existing booking in the BOOKING table.
     *
     * @param params the parameters for the booking to update
     * @throws SQLException if a database access error occurs
     */
    public void update(Object... params) throws SQLException {
        String sql = "UPDATE BOOKING SET CLIENT_ID = ?, OFFERING_ID = ? WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[1]);
            pstmt.setString(2, (String) params[2]);
            pstmt.setString(3, (String) params[0]);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    /**
     * Deletes a booking from the BOOKING table.
     *
     * @param id the ID of the booking to delete
     * @throws SQLException if a database access error occurs
     */
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM BOOKING WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    /**
     * Finds a booking by ID in the BOOKING table.
     *
     * @param id the ID of the booking to find
     * @return the result set containing the booking data
     * @throws SQLException if a database access error occurs
     */
    public ResultSet find(String id) throws SQLException {
        String sql = "SELECT * FROM BOOKING WHERE ID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeQuery();
    }

    /**
     * Prints all bookings from the BOOKING table.
     *
     * @throws SQLException if a database access error occurs
     */
    public void printAllBookings() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM BOOKING");
            while (rs.next()) {
                String id = rs.getString("ID");
                String clientId = rs.getString("CLIENT_ID");
                String offeringId = rs.getString("OFFERING_ID");
                System.out.println("ID = " + id);
                System.out.println("CLIENT_ID = " + clientId);
                System.out.println("OFFERING_ID = " + offeringId);
                System.out.println();
            }
        } finally {
            closeResources(rs, stmt);
        }
    }

    /**
     * Closes the given resources.
     *
     * @param resources the resources to close
     */
    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
