package tdg;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class InstructorTDG {

    private Connection connection;

    /**
     * Constructor to initialize the database connection.
     *
     * @param connection the database connection
     */
    public InstructorTDG(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates the INSTRUCTOR table if it does not already exist.
     *
     * @throws SQLException if a database access error occurs
     */
    public void createTable() throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS INSTRUCTOR " +
                         "(ID TEXT PRIMARY KEY NOT NULL," +
                         " NAME TEXT NOT NULL, " + 
                         " PHONE_NUMBER TEXT NOT NULL, " + 
                         " AGE INT NOT NULL, " + 
                         " PASSWORD TEXT NOT NULL, " +
                         " SALT TEXT NOT NULL)";
            stmt.executeUpdate(sql);
        } finally {
            closeResources(stmt);
        }
    }

    /**
     * Inserts a new instructor into the INSTRUCTOR table.
     *
     * @param params the parameters for the new instructor
     * @throws SQLException if a database access error occurs
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    public void insert(Object... params) throws SQLException, NoSuchAlgorithmException {
        String sql = "INSERT INTO INSTRUCTOR (ID, NAME, PHONE_NUMBER, AGE, PASSWORD, SALT) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[0]);
            pstmt.setString(2, (String) params[1]);
            pstmt.setString(3, (String) params[2]);
            pstmt.setInt(4, (Integer) params[3]);

            // Generate salt
            byte[] salt = generateSalt();
            String saltStr = Base64.getEncoder().encodeToString(salt);

            // Hash the password with the salt. This stores all passwords as some hash so its secure in case of data leaks
            String hashedPassword = hashPassword((String) params[4], salt);

            pstmt.setString(5, hashedPassword);
            pstmt.setString(6, saltStr);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    /**
     * Updates an existing instructor in the INSTRUCTOR table.
     *
     * @param params the parameters for the instructor to update
     * @throws SQLException if a database access error occurs
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    public void update(Object... params) throws SQLException, NoSuchAlgorithmException {
        String sql = "UPDATE INSTRUCTOR SET NAME = ?, PHONE_NUMBER = ?, AGE = ?, PASSWORD = ?, SALT = ? WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[1]);
            pstmt.setString(2, (String) params[2]);
            pstmt.setInt(3, (Integer) params[3]);

            // Generate salt
            byte[] salt = generateSalt();
            String saltStr = Base64.getEncoder().encodeToString(salt);

            // Hash the password with the salt
            String hashedPassword = hashPassword((String) params[4], salt);

            pstmt.setString(4, hashedPassword);
            pstmt.setString(5, saltStr);
            pstmt.setString(6, (String) params[0]);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    /**
     * Deletes an instructor from the INSTRUCTOR table.
     *
     * @param id the ID of the instructor to delete
     * @throws SQLException if a database access error occurs
     */
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM INSTRUCTOR WHERE ID = ?";
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
     * Finds an instructor by ID in the INSTRUCTOR table.
     *
     * @param id the ID of the instructor to find
     * @return the result set containing the instructor data
     * @throws SQLException if a database access error occurs
     */
    public ResultSet find(String id) throws SQLException {
        String sql = "SELECT * FROM INSTRUCTOR WHERE ID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeQuery();
    }

    /**
     * Prints all instructors from the INSTRUCTOR table.
     *
     * @throws SQLException if a database access error occurs
     */
    public void printAllInstructors() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM INSTRUCTOR");
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String phoneNumber = rs.getString("PHONE_NUMBER");
                int age = rs.getInt("AGE");
                String password = rs.getString("PASSWORD");
                String salt = rs.getString("SALT");
                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("PHONE_NUMBER = " + phoneNumber);
                System.out.println("AGE = " + age);
                System.out.println("PASSWORD = " + password);
                System.out.println("SALT = " + salt);
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

    /**
     * Generates a random salt.
     *
     * @return the generated salt
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Hashes the password with the given salt using SHA-256.
     *
     * @param password the password to hash
     * @param salt the salt to use for hashing
     * @return the hashed password
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
