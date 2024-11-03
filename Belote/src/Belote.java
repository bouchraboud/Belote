import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Belote {
    private static Scanner s;

    static class Match {
        public int eq1, eq2;

        public Match(int e1, int e2) {
            eq1 = e1;
            eq2 = e2;
        }

        @Override
        public String toString() {
            if (eq1 < eq2) {
                return "  " + eq1 + " contre " + eq2;
            } else {
                return "  " + eq2 + " contre " + eq1;
            }
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        TournoiDAOImpl tournoiDAO = null; // Tournoi Data Access Object
        EquipeDAOImpl equipeDAO = null;    // Equipe Data Access Object

        try {
            System.out.println("Chargement du pilote JDBC...");
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            System.out.println("Pilote JDBC chargé avec succès.");

            String dos = System.getenv("APPDATA") + "\\jBelote";
            System.out.println("Dossier de stockage: " + dos);
            if (!new File(dos).isDirectory()) {
                new File(dos).mkdir();
            }

            // Create the database connection
            connection = DriverManager.getConnection("jdbc:hsqldb:file:" + dos + "\\belote", "sa", "");

            // Initialize DAOs
            tournoiDAO = new TournoiDAOImpl(connection);
            equipeDAO = new EquipeDAOImpl(connection);

            // Connexion et importation SQL
            tournoiDAO.importSQL(connection, new File("create.sql"));

            // Initialisation de la fenêtre
            Fenetre f = new Fenetre(connection.createStatement());
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de données. Vérifiez qu'une autre instance du logiciel n'est pas déjà ouverte.");
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fichier SQL non trouvé.");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Pilote JDBC non trouvé.");
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'initialisation du logiciel. Vérifiez votre installation Java et vos droits d'accès sur le dossier AppData.");
            System.out.println(e.getMessage());
            System.exit(0);
        } finally {
            // Close resources in the finally block to ensure they are closed
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close(); // Close the connection directly here
                }
                if (s != null) {
                    s.close(); // Close the scanner if it was opened
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erreur lors de la fermeture du scanner : " + e.getMessage());
            }
        }
    }
}




