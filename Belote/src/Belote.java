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
        String dos = System.getenv("APPDATA") + "\\jBelote";
        TournoiDAOImpl tournoiDAO = null;
        EquipeDAOImpl equipeDAO = null;

        try {
            System.out.println("Chargement du pilote JDBC...");
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            System.out.println("Pilote JDBC chargé avec succès.");

            // Assurer que le dossier existe
            File storageDir = new File(dos);
            if (!storageDir.isDirectory()) {
                storageDir.mkdir();
            }

            // Utilisation de try-with-resources pour gérer la connexion
            try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:" + dos + "\\belote", "sa", "")) {
                System.out.println("Connexion à la base de données réussie.");
                
                // Initialisation des DAO
                tournoiDAO = new TournoiDAOImpl(connection);
                equipeDAO = new EquipeDAOImpl(connection);
                
                // Importation SQL
                tournoiDAO.importSQL(connection, new File("create.sql"));

                // Initialisation de la fenêtre
                Fenetre fenetre = new Fenetre(connection.createStatement());
                fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fenetre.setVisible(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de données. Vérifiez qu'une autre instance du logiciel n'est pas déjà ouverte.");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fichier SQL non trouvé : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Pilote JDBC non trouvé : " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'initialisation du logiciel. Vérifiez votre installation Java et vos droits d'accès sur le dossier AppData.");
            e.printStackTrace();
        }
    }
}



