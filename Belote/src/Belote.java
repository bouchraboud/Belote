import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Belote {

    public static void main(String[] args) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();
        try {
            // Initialize the GUI (Fenetre) window

            Fenetre fenetre = new Fenetre(connection);
            fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fenetre.setVisible(true);  // Show the window

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'initialisation du logiciel.");
            e.printStackTrace();
        }
    }
}
