package tdg;
import java.sql.*;

public class LessonTDG {

    private Connection connection;

    public LessonTDG(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS LESSON " +
                         "(ID TEXT PRIMARY KEY NOT NULL," +
                         " TITLE TEXT NOT NULL, " + 
                         " DESCRIPTION TEXT NOT NULL, " + 
                         " LOCATION_ID TEXT NOT NULL, " + 
                         " TIMESLOT TEXT NOT NULL)"; // Assuming timeslot is stored as a JSON string
            stmt.executeUpdate(sql);
        } finally {
            closeResources(stmt);
        }
    }

    public void insert(Object... params) throws SQLException {
        String sql = "INSERT INTO LESSON (ID, TITLE, DESCRIPTION, LOCATION_ID, TIMESLOT) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[0]);
            pstmt.setString(2, (String) params[1]);
            pstmt.setString(3, (String) params[2]);
            pstmt.setString(4, (String) params[3]);
            pstmt.setString(5, (String) params[4]); // Assuming timeslot is passed as a JSON string
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    public void update(Object... params) throws SQLException {
        String sql = "UPDATE LESSON SET TITLE = ?, DESCRIPTION = ?, LOCATION_ID = ?, TIMESLOT = ? WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[1]);
            pstmt.setString(2, (String) params[2]);
            pstmt.setString(3, (String) params[3]);
            pstmt.setString(4, (String) params[4]); // Assuming timeslot is passed as a JSON string
            pstmt.setString(5, (String) params[0]);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM LESSON WHERE ID = ?";
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
        String sql = "SELECT * FROM LESSON WHERE ID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeQuery();
    }

    public void printAllLessons() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM LESSON");
            while (rs.next()) {
                String id = rs.getString("ID");
                String title = rs.getString("TITLE");
                String description = rs.getString("DESCRIPTION");
                String locationId = rs.getString("LOCATION_ID");
                String timeslot = rs.getString("TIMESLOT");
                System.out.println("ID = " + id);
                System.out.println("TITLE = " + title);
                System.out.println("DESCRIPTION = " + description);
                System.out.println("LOCATION_ID = " + locationId);
                System.out.println("TIMESLOT = " + timeslot);
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
