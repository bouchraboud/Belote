
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // Remplacez les valeurs ci-dessous par celles de votre base de données
            String url = "jdbc:mysql://localhost:3306/votre_base_de_donnees";
            String user = "votre_utilisateur";
            String password = "votre_mot_de_passe";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
