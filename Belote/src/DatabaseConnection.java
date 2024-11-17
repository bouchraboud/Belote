
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    // Static instance of DatabaseConnection (Singleton)
    private static DatabaseConnection instance;
    private Connection connection;

    // HSQLDB connection URL, username, and password
    private static final String URL = "jdbc:hsqldb:file:C:\\Users\\dell\\Downloads\\hsqldb-2.7.4;shutdown=true";  // Path to the HSQLDB database file
    private static final String USER = "SA"; // Default username for HSQLDB
    private static final String PASSWORD = ""; // Default password for HSQLDB
    private static final String DRIVER = "org.hsqldb.jdbc.JDBCDriver";  // HSQLDB JDBC Driver

    // Private constructor to prevent instantiation from other classes
    private DatabaseConnection() {
        try {
            // Register HSQLDB JDBC driver
            Class.forName(DRIVER);
            // Establish connection to HSQLDB database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (connection != null) {
                System.out.println("HSQLDB Connection successful!");
                createTournoisTable();
                createEquipesTable();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed.", e);
        }
    }

    // Public method to get the single instance of the DatabaseConnection
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    private void createTournoisTable() {
        try (Statement stmt = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS tours (" +
                    "id INT PRIMARY KEY, " +
                    "nom_tournoi VARCHAR(255), " +
                    "statut VARCHAR(255))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("TOURS table created (or already exists).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createEquipesTable() {
        try (Statement stmt = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS EQUIPES (" +
                    "ID_EQUIPE INT PRIMARY KEY, " +
                    "nom_equipe VARCHAR(255), " +
                    "statut VARCHAR(255))";
            stmt.executeUpdate(createTableSQL);
            System.out.println("EQUIPES table created (or already exists).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Method to get the connection object
    public Connection getConnection() {
        try {
            // Check if the connection is valid before returning it
            if (connection != null && !connection.isClosed()) {
                return connection;
            } else {
                // Re-establish connection if it's closed
                instance = new DatabaseConnection(); // Reinitialize the instance
                return instance.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get the connection.", e);
        }
    }

    // Close the connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("HSQLDB Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
