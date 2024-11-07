import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Belote {
    private static Scanner scanner;

    // Classe interne pour la gestion d'un match entre deux équipes
    static class Match {
        public int equipe1, equipe2;

        public Match(int equipe1, int equipe2) {
            this.equipe1 = equipe1;
            this.equipe2 = equipe2;
        }

        @Override
        public String toString() {
            return (equipe1 < equipe2) ? "  " + equipe1 + " contre " + equipe2 : "  " + equipe2 + " contre " + equipe1;
        }
    }

    public static void main(String[] args) {
        // Dossier de stockage des données
        String dos = System.getenv("APPDATA") + "\\jBelote";
        TournoiDAOImpl tournoiDAO = null;
        EquipeDAOImpl equipeDAO = null;

        try {
            System.out.println("Chargement du pilote JDBC...");
            Class.forName("org.hsqldb.jdbcDriver").newInstance();  // Chargement du pilote HSQLDB
            System.out.println("Pilote JDBC chargé avec succès.");

            // Assurer que le dossier existe
            File storageDir = new File(dos);
            if (!storageDir.isDirectory()) {
                storageDir.mkdir();  // Créer le dossier si nécessaire
            }

            // Connexion à la base de données HSQLDB
            try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:" + dos + "\\belote", "sa", "")) {
                System.out.println("Connexion à la base de données réussie.");

                // Initialisation des DAO (Data Access Objects)
                tournoiDAO = new TournoiDAOImpl(connection);
                equipeDAO = new EquipeDAOImpl(connection);

                // Importation du fichier SQL pour créer la structure de la base
                tournoiDAO.importSQL(connection, new File("create.sql"));

                // Initialisation de la fenêtre graphique
                Fenetre fenetre = new Fenetre(connection.createStatement());
                fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fenetre.setVisible(true);  // Affichage de la fenêtre
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de données. Vérifiez qu'une autre instance du logiciel n'est pas déjà ouverte.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Pilote JDBC non trouvé : " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'initialisation du logiciel. Vérifiez votre installation Java et vos droits d'accès sur le dossier AppData.");
            e.printStackTrace();
        }
    }
}
