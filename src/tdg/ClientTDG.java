package tdg;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class ClientTDG {

    private Connection connection;

    /**
     * Constructor to initialize the database connection.
     *
     * @param connection the database connection
     */
    public ClientTDG() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:test.db");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the CLIENT table if it does not already exist.
     *
     * @throws SQLException if a database access error occurs
     */
    public void createTable() throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS CLIENT " +
                         "(ID TEXT PRIMARY KEY NOT NULL," +
                         " NAME TEXT NOT NULL, " + 
                         " PHONE_NUMBER TEXT NOT NULL, " + 
                         " AGE INT NOT NULL, " + 
                         " PASSWORD TEXT NOT NULL, " +
                         " SALT TEXT NOT NULL)"+
                         " GUARDIAN_ID TEXT"+
                         " FOREIGN KEY (GUARDIAN_ID) REFERENCES CLIENT(ID) ON DELETE CASCADE";
            stmt.executeUpdate(sql);
        } finally {
            closeResources(stmt);
        }
    }

    /**
     * Inserts a new client into the CLIENT table.
     *
     * @param params the parameters for the new client
     * @throws SQLException if a database access error occurs
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    public void insert(Object... params) throws SQLException, NoSuchAlgorithmException {
        String sql = "INSERT INTO CLIENT (ID, NAME, PHONE_NUMBER, AGE, PASSWORD, SALT, GUARDIAN_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
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

            // Hash the password with the salt
            String hashedPassword = hashPassword((String) params[4], salt);

            pstmt.setString(5, hashedPassword);
            pstmt.setString(6, saltStr);
            pstmt.setString(7, (String) params[5]); // Adding guardianId attribute
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    /**
     * Updates an existing client in the CLIENT table.
     *
     * @param params the parameters for the client to update
     * @throws SQLException if a database access error occurs
     * @throws NoSuchAlgorithmException if the specified algorithm is not available
     */
    public void update(Object... params) throws SQLException, NoSuchAlgorithmException {
        String sql = "UPDATE CLIENT SET NAME = ?, PHONE_NUMBER = ?, AGE = ?, PASSWORD = ?, SALT = ?, GUARDIAN_ID = ? WHERE ID = ?";
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
            pstmt.setString(6, (String) params[5]); // Adding guardianId attribute
            pstmt.setString(7, (String) params[0]);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    /**
     * Deletes a client from the CLIENT table.
     *
     * @param id the ID of the client to delete
     * @throws SQLException if a database access error occurs
     */
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM CLIENT WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No client found with ID: " + id);
            } else {
                System.out.println("Client with ID: " + id + " deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(pstmt);
        }
    }

    /**
     * Finds a client by ID in the CLIENT table.
     *
     * @param id the ID of the client to find
     * @return the result set containing the client data
     * @throws SQLException if a database access error occurs
     */
    public ResultSet find(String id) throws SQLException {
        String sql = "SELECT * FROM CLIENT WHERE ID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeQuery();
    }

    /**
     * Prints all clients from the CLIENT table.
     *
     * @throws SQLException if a database access error occurs
     */
    public void printAllClients() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM CLIENT");
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
    public byte[] getSaltByClientId(String clientId) throws SQLException {
        String query = "SELECT salt FROM Client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Base64.getDecoder().decode(rs.getString("salt"));
            } else {
                return null;
            }
        }
    }

    public String getHashedPasswordByClientId(String clientId) throws SQLException {
        String query = "SELECT password FROM Client WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            } else {
                return null;
            }
        }
    }
}
