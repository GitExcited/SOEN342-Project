package tdg;
import java.sql.*;

public class LocationTDG {

    private Connection connection;

    public LocationTDG( ) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:test.db");
    }

    public void createTable() throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS LOCATION " +
                         "(ID TEXT PRIMARY KEY NOT NULL," +
                         " NAME TEXT NOT NULL, " + 
                         " ADDRESS TEXT NOT NULL, " + 
                         " CITY TEXT NOT NULL, " + 
                         " ROOM TEXT NOT NULL, " + 
                         " SCHEDULE TEXT NOT NULL)"; // Assuming schedule is stored as a JSON string
            stmt.executeUpdate(sql);
        } finally {
            closeResources(stmt);
        }
    }

    public void insert(Object... params) throws SQLException {
        String sql = "INSERT INTO LOCATION (ID, NAME, ADDRESS, CITY, ROOM, SCHEDULE) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[0]);
            pstmt.setString(2, (String) params[1]);
            pstmt.setString(3, (String) params[2]);
            pstmt.setString(4, (String) params[3]);
            pstmt.setString(5, (String) params[4]);
            pstmt.setString(6, (String) params[5]); // Assuming schedule is passed as a JSON string
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    public void update(Object... params) throws SQLException {
        String sql = "UPDATE LOCATION SET NAME = ?, ADDRESS = ?, CITY = ?, ROOM = ?, SCHEDULE = ? WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[1]);
            pstmt.setString(2, (String) params[2]);
            pstmt.setString(3, (String) params[3]);
            pstmt.setString(4, (String) params[4]);
            pstmt.setString(5, (String) params[5]); // Assuming schedule is passed as a JSON string
            pstmt.setString(6, (String) params[0]);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM LOCATION WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    public ResultSet find(String id) throws SQLException {
        String sql = "SELECT * FROM LOCATION WHERE ID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeQuery();
    }

    public void printAllLocations() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM LOCATION");
            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String address = rs.getString("ADDRESS");
                String city = rs.getString("CITY");
                String room = rs.getString("ROOM");
                String schedule = rs.getString("SCHEDULE");
                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("ADDRESS = " + address);
                System.out.println("CITY = " + city);
                System.out.println("ROOM = " + room);
                System.out.println("SCHEDULE = " + schedule);
                System.out.println();
            }
        } finally {
            closeResources(rs, stmt);
        }
    }

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
