package managment;

import java.sql.*;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ams";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "AMSpassw333";
    
    private static Connection connection;  // Static connection variable
    // Static block to initialize the connection
    static {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    // Public static method to get the connection
    public static Connection getConnection() {
        return connection;
    }

    // Close connection method (if needed)
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
