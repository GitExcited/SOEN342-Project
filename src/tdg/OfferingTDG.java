package tdg;

import java.sql.*;

public class OfferingTDG {

    private Connection connection;

    public OfferingTDG() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:test.db");
    }

    public void createTable() throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS OFFERING " +
                         "(ID TEXT PRIMARY KEY NOT NULL," +
                         " LESSON_ID TEXT NOT NULL, " + 
                         " INSTRUCTOR_ID TEXT NOT NULL, " + 
                         " BOOKED BOOLEAN NOT NULL)";
            stmt.executeUpdate(sql);
        } finally {
            closeResources(stmt);
        }
    }

    public void insert(Object... params) throws SQLException {
        String sql = "INSERT INTO OFFERING (ID, LESSON_ID, INSTRUCTOR_ID, BOOKED) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[0]);
            pstmt.setString(2, (String) params[1]);
            pstmt.setString(3, (String) params[2]);
            pstmt.setBoolean(4, (Boolean) params[3]);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    public void update(Object... params) throws SQLException {
        String sql = "UPDATE OFFERING SET LESSON_ID = ?, INSTRUCTOR_ID = ?, BOOKED = ? WHERE ID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, (String) params[1]);
            pstmt.setString(2, (String) params[2]);
            pstmt.setBoolean(3, (Boolean) params[3]);
            pstmt.setString(4, (String) params[0]);
            pstmt.executeUpdate();
        } finally {
            closeResources(pstmt);
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM OFFERING WHERE ID = ?";
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
        String sql = "SELECT * FROM OFFERING WHERE ID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeQuery();
    }

    public void printAllOfferings() throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM OFFERING");
            while (rs.next()) {
                String id = rs.getString("ID");
                String lessonId = rs.getString("LESSON_ID");
                String instructorId = rs.getString("INSTRUCTOR_ID");
                boolean booked = rs.getBoolean("BOOKED");
                System.out.println("ID = " + id);
                System.out.println("LESSON_ID = " + lessonId);
                System.out.println("INSTRUCTOR_ID = " + instructorId);
                System.out.println("BOOKED = " + booked);
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
